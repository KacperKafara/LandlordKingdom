package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.IdenticalFieldValueException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.LocalExceptionMessages;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.messages.LocalMessages;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.model.LocalState;
import pl.lodz.p.it.ssbd2024.mok.repositories.OwnerRepository;
import pl.lodz.p.it.ssbd2024.mol.dto.AddLocalRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReportResponse;
import pl.lodz.p.it.ssbd2024.mol.repositories.AddressRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.services.LocalService;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class LocalServiceImpl implements LocalService {
    private final LocalRepository localRepository;
    private final AddressRepository addressRepository;
    private final OwnerRepository ownerRepository;

    @Override
    @PreAuthorize("hasRole('OWNER')")
    @Transactional(rollbackFor = {IdenticalFieldValueException.class}, propagation = Propagation.REQUIRES_NEW)
    public Local addLocal(AddLocalRequest addLocalRequest, UUID ownerId) throws GivenAddressAssignedToOtherLocalException, NotFoundException, CreationException {
        Owner owner = ownerRepository.findByUserId(ownerId).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
        Address newAddress = new Address(
                addLocalRequest.address().number(),
                addLocalRequest.address().street(),
                addLocalRequest.address().city(),
                addLocalRequest.address().zipCode(),
                addLocalRequest.address().country()
        );
        Optional<Address> existingAddress = addressRepository.findByNumberAndStreetAndCityAndZipAndCountry(
                newAddress.getNumber(),
                newAddress.getStreet(),
                newAddress.getCity(),
                newAddress.getZip(),
                newAddress.getCountry()
        );
        if (existingAddress.isPresent()) {
            List<Local> existingLocal = localRepository.findByAddressAndStateNotContaining(newAddress, LocalState.ARCHIVED);
            if (!existingLocal.isEmpty()) {
                boolean hasOtherState = existingLocal.stream()
                        .anyMatch(local -> local.getState() != LocalState.WITHOUT_OWNER);
                if (hasOtherState && existingLocal.size() > 1) {
                    throw new GivenAddressAssignedToOtherLocalException(LocalMessages.ADDRESS_ASSIGNED,
                            ErrorCodes.ADDRESS_ASSIGNED);
                } else {
                    existingLocal.getFirst().setOwner(owner);
                    existingLocal.getFirst().setState(LocalState.UNAPPROVED);
                    return localRepository.saveAndFlush(existingLocal.getFirst());
                }
            } else {
                throw new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
            }
        } else {
            try {
                Address savedAddress = addressRepository.saveAndFlush(newAddress);
                Local rewriteLocal = new Local(
                        addLocalRequest.name(),
                        addLocalRequest.description(),
                        addLocalRequest.size(),
                        savedAddress,
                        owner,
                        addLocalRequest.marginFee(),
                        addLocalRequest.rentalFee()
                );
                return localRepository.saveAndFlush(rewriteLocal);
            } catch (ConstraintViolationException e) {
                throw new CreationException(UserExceptionMessages.CREATION_FAILED, ErrorCodes.REGISTRATION_ERROR);
            }
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Local> getActiveLocals() {
        return localRepository.findAllByState(LocalState.ACTIVE);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public List<Local> getUnapprovedLocals() {
        return localRepository.findAllByState(LocalState.UNAPPROVED);
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
    public Local editLocal(UUID id, Local local) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public Local setFixedFee(UUID localId, UUID ownerId,  BigDecimal marginFee, BigDecimal rentalFee) throws NotFoundException {
        Local local = localRepository.findByOwner_User_IdAndId(ownerId, localId).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));

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
    public Page<Local> getAllLocals(Pageable pageable) {
        return localRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {GivenAddressAssignedToOtherLocalException.class, NotFoundException.class})
    public Local changeLocalAddress(UUID id, Address address) throws GivenAddressAssignedToOtherLocalException, NotFoundException {
        Optional<Address> addressOptional = addressRepository.findByAddress(address.getCountry(), address.getCity(), address.getStreet(), address.getNumber(), address.getZip());
        Local local = localRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
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

        return local;
    }


    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Local editLocalByAdmin(UUID id, Local newLocal) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
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
