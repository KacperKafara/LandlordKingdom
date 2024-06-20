package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.IdenticalFieldValueException;
import pl.lodz.p.it.ssbd2024.exceptions.ApplicationOptimisticLockException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.LocalExceptionMessages;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.messages.LocalMessages;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.messages.OptimisticLockExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.model.LocalState;
import pl.lodz.p.it.ssbd2024.mok.dto.GetActiveLocalsFilterRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.EditLocalRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.EditLocalRequestAdmin;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReportResponse;
import pl.lodz.p.it.ssbd2024.mol.repositories.AddressRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.OwnerMolRepository;
import pl.lodz.p.it.ssbd2024.mol.services.LocalService;
import pl.lodz.p.it.ssbd2024.util.SignVerifier;
import pl.lodz.p.it.ssbd2024.util.UserFromContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Slf4j
public class LocalServiceImpl implements LocalService {
    private final LocalRepository localRepository;
    private final AddressRepository addressRepository;
    private final SignVerifier signVerifier;
    private final OwnerMolRepository ownerRepository;

    @Override
    @PreAuthorize("hasRole('OWNER')")
    @Transactional(rollbackFor = {IdenticalFieldValueException.class}, propagation = Propagation.REQUIRES_NEW)
    public Local addLocal(Local local, UUID ownerId) throws GivenAddressAssignedToOtherLocalException, NotFoundException, CreationException {
        Owner owner = ownerRepository.findByUserIdAndActiveIsTrue(ownerId).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
        Optional<Address> existingAddress = addressRepository.findByAddress(
                local.getAddress().getCountry(),
                local.getAddress().getCity(),
                local.getAddress().getStreet(),
                local.getAddress().getNumber(),
                local.getAddress().getZip()
        );
        if (existingAddress.isPresent()) {
            Local existingLocal = localRepository.findByAddressAndStateNotContaining(existingAddress.get(), LocalState.ARCHIVED).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
            if (existingLocal.getState() != LocalState.WITHOUT_OWNER) {
                throw new GivenAddressAssignedToOtherLocalException(LocalMessages.ADDRESS_ASSIGNED,
                        ErrorCodes.ADDRESS_ALREADY_ASSIGNED);
            }
            existingLocal.setOwner(owner);
            existingLocal.setState(LocalState.UNAPPROVED);
            return localRepository.saveAndFlush(existingLocal);
        } else {
            try {
                local.setOwner(owner);
                return localRepository.saveAndFlush(local);
            } catch (ConstraintViolationException e) {
                throw new CreationException(LocalExceptionMessages.CREATION_FAILED, ErrorCodes.LOCAL_CREATION_ERROR);
            }
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Page<Local> getActiveLocals(Pageable pageable) {
        return localRepository.findAllByState(pageable, LocalState.ACTIVE);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Page<Local> getActiveLocalsFilter(String city, Double minSize, Double maxSize, Pageable pageable){
        return localRepository.findAllByStateCityAndSize(
                pageable,
                LocalState.ACTIVE,
                city,
                minSize,
                maxSize
        );
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Page<Local> getUnapprovedLocals(Pageable pageable) {
        return localRepository.findAllByState(pageable, LocalState.UNAPPROVED);
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Page<Local> getOwnLocals(UUID id, Pageable pageable, String state) {
        if(Objects.equals(state, "ALL")) {
            return localRepository.findAllByOwnerId(id, pageable);
        }

        LocalState localState = LocalState.valueOf(state);
        return localRepository.findAllByOwnerIdAndState(id, pageable, localState);
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public LocalReportResponse getLocalReport(UUID id) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Local editLocal(UUID userId, UUID localId, EditLocalRequest editLocalRequest, String tagValue) throws NotFoundException, ApplicationOptimisticLockException {
        Local local = localRepository.findByOwner_User_IdAndId(userId, localId).orElseThrow(() ->
                new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
        if (!signVerifier.verifySignature(local.getId(), local.getVersion(), tagValue)) {
            throw new ApplicationOptimisticLockException(OptimisticLockExceptionMessages.LOCAL_ALREADY_MODIFIED_DATA, ErrorCodes.OPTIMISTIC_LOCK);
        }
        local.setName(editLocalRequest.name());
        local.setDescription(editLocalRequest.description());
        LocalState newState = LocalState.valueOf(editLocalRequest.state());
        if (local.getState() == LocalState.ACTIVE || local.getState() == LocalState.INACTIVE)
            local.setState(newState);
        return localRepository.saveAndFlush(local);
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Local leaveLocal(UUID userId, UUID localId) throws InvalidLocalState, NotFoundException {
        Local local = localRepository.findByOwner_User_IdAndId(userId, localId).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
        if (local.getState() != LocalState.INACTIVE) {
            throw new InvalidLocalState(LocalExceptionMessages.LOCAL_NOT_INACTIVE, ErrorCodes.LOCAL_NOT_INACTIVE);
        }
        local.setOwner(null);
        local.setState(LocalState.WITHOUT_OWNER);
        return localRepository.saveAndFlush(local);

    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Local setFixedFee(UUID localId, UUID ownerId,  BigDecimal marginFee, BigDecimal rentalFee, String tagValue) throws NotFoundException, ApplicationOptimisticLockException {
        Local local = localRepository.findByOwner_User_IdAndId(ownerId, localId).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));

        if (!signVerifier.verifySignature(local.getId(), local.getVersion(), tagValue)) {
            throw new ApplicationOptimisticLockException(OptimisticLockExceptionMessages.LOCAL_ALREADY_MODIFIED_DATA, ErrorCodes.OPTIMISTIC_LOCK);
        }

        if (local.getState() == LocalState.RENTED) {
            local.setNextMarginFee(marginFee);
            local.setNextRentalFee(rentalFee);
        } else {
            local.setMarginFee(marginFee);
            local.setRentalFee(rentalFee);
        }

        return localRepository.saveAndFlush(local);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Page<Local> getAllLocals(Pageable pageable, String state, String ownerLogin) {
        if(state.equals(LocalState.ARCHIVED.name()) || state.equals(LocalState.WITHOUT_OWNER.name()))
            return localRepository.findAllByState(pageable, LocalState.valueOf(state));

        if(state.equals("ALL") && ownerLogin.isEmpty())
            return localRepository.findAll(pageable);

        if(state.equals("ALL"))
            return localRepository.findAll(pageable, ownerLogin);

        return localRepository.findAllByStateAndOwnerLogin(pageable, LocalState.valueOf(state), ownerLogin);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {GivenAddressAssignedToOtherLocalException.class, NotFoundException.class})
    public Local changeLocalAddress(UUID id, Address address, String tagValue) throws GivenAddressAssignedToOtherLocalException, NotFoundException, ApplicationOptimisticLockException, InvalidLocalState {
        Local local = localRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
        if(local.getState() == LocalState.ARCHIVED) {
            throw new InvalidLocalState(LocalExceptionMessages.LOCAL_ARCHIVED, ErrorCodes.UPDATE_LOCAL_ARCHIVED);
        }
        if (!signVerifier.verifySignature(local.getId(), local.getVersion(), tagValue)) {
            throw new ApplicationOptimisticLockException(OptimisticLockExceptionMessages.LOCAL_ALREADY_MODIFIED_DATA, ErrorCodes.OPTIMISTIC_LOCK);
        }
        Optional<Address> addressOptional = addressRepository.findByAddress(address.getCountry(), address.getCity(), address.getStreet(), address.getNumber(), address.getZip());
        if (addressOptional.isPresent()) {
            Address foundAddress = addressOptional.get();
            for (Local l : foundAddress.getLocals()) {
                if (!l.getState().equals(LocalState.ARCHIVED)) {
                    throw new GivenAddressAssignedToOtherLocalException(LocalExceptionMessages.ADDRESS_ALREADY_ASSIGNED, ErrorCodes.ADDRESS_ALREADY_ASSIGNED);
                }
            }
            local.setAddress(foundAddress);
            return localRepository.saveAndFlush(local);
        }

        Address oldAddress = local.getAddress();
        oldAddress.setAddress(address);
        try {
            addressRepository.saveAndFlush(oldAddress);
        } catch (ConstraintViolationException e) {
            throw new GivenAddressAssignedToOtherLocalException(LocalExceptionMessages.ADDRESS_ALREADY_ASSIGNED, ErrorCodes.ADDRESS_ALREADY_ASSIGNED);
        }

        local.setModifiedAt(LocalDateTime.now());
        local.setModifiedBy(UserFromContext.getCurrentUserId());
        return localRepository.saveAndFlush(local);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Local editLocalByAdmin(UUID localId, EditLocalRequestAdmin editLocalRequest, String tagValue) throws NotFoundException, ApplicationOptimisticLockException {
        Local local = localRepository.findById(localId).orElseThrow(() ->
                new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
        if (!signVerifier.verifySignature(local.getId(), local.getVersion(), tagValue)) {
            throw new ApplicationOptimisticLockException(OptimisticLockExceptionMessages.LOCAL_ALREADY_MODIFIED_DATA, ErrorCodes.OPTIMISTIC_LOCK);
        }
        System.out.println(editLocalRequest);
        local.setName(editLocalRequest.name());
        local.setDescription(editLocalRequest.description());
        local.setSize(editLocalRequest.size());
        LocalState newState = LocalState.valueOf(editLocalRequest.state());
        if (local.getState() == LocalState.ACTIVE || local.getState() == LocalState.INACTIVE)
            local.setState(newState);
        return localRepository.saveAndFlush(local);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Local approveLocal(UUID id) throws NotFoundException, InvalidLocalState {
        Local local = localRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));

        if (local.getState() != LocalState.UNAPPROVED) {
            throw new InvalidLocalState(LocalExceptionMessages.LOCAL_NOT_UNAPPROVED, ErrorCodes.LOCAL_NOT_UNAPPROVED, LocalState.UNAPPROVED, local.getState());
        }

        local.setState(LocalState.INACTIVE);
        return localRepository.saveAndFlush(local);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Local rejectLocal(UUID id) throws NotFoundException, InvalidLocalState {
        Local local = localRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));

        if (local.getState() != LocalState.UNAPPROVED) {
            throw new InvalidLocalState(LocalExceptionMessages.LOCAL_NOT_UNAPPROVED, ErrorCodes.LOCAL_NOT_UNAPPROVED, LocalState.UNAPPROVED, local.getState());
        }

        local.setState(LocalState.WITHOUT_OWNER);
        local.setOwner(null);
        return localRepository.saveAndFlush(local);
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public List<LocalReportResponse> getAllReports(UUID ownerId) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Local archiveLocal(UUID id) throws NotFoundException, InvalidLocalState {
        Local local = localRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
        if (local.getState() != LocalState.WITHOUT_OWNER) {
            throw new InvalidLocalState(LocalExceptionMessages.INVALID_LOCAL_STATE_ARCHIVE, ErrorCodes.INVALID_LOCAL_STATE_ARCHIVE, LocalState.WITHOUT_OWNER, local.getState());
        }
        local.setState(LocalState.ARCHIVED);
        return localRepository.saveAndFlush(local);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Local getLocal(UUID id) throws NotFoundException {
        return localRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Local getOwnLocal(UUID id, UUID ownerId) throws NotFoundException {
        return localRepository.findByOwner_User_IdAndId(ownerId, id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
    }

    @Override
    public Local getActiveLocal(UUID id) throws NotFoundException {
        return localRepository.findByIdAndState(id, LocalState.ACTIVE).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
    }
}
