package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.LocalMessages;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.model.LocalState;
import pl.lodz.p.it.ssbd2024.model.Owner;
import pl.lodz.p.it.ssbd2024.mok.repositories.OwnerRepository;
import pl.lodz.p.it.ssbd2024.mol.dto.AddLocalRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReportResponse;
import pl.lodz.p.it.ssbd2024.mol.repositories.AddressRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.services.LocalService;

import java.math.BigDecimal;
import java.util.List;
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
    public Local addLocal(AddLocalRequest addLocalRequest, UUID ownerId) throws GivenAddressAssignedToOtherLocalException, NotFoundException {
        Optional<Address> existingAddress = addressRepository.findByNumberAndStreetAndCityAndZipAndCountry(
                addLocalRequest.address().getNumber(),
                addLocalRequest.address().getStreet(),
                addLocalRequest.address().getCity(),
                addLocalRequest.address().getZip(),
                addLocalRequest.address().getCountry()
        );
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new
                NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
        if (existingAddress.isPresent()) {
            List<Local> existingLocal = localRepository.findByAddressAndStateNotContaining(addLocalRequest.address(), LocalState.ARCHIVED);
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
            }
        } else {
            addressRepository.saveAndFlush(addLocalRequest.address());
        }
        Local local = new Local(
                addLocalRequest.name(),
                addLocalRequest.description(),
                addLocalRequest.size(),
                addLocalRequest.address(),
                owner,
                addLocalRequest.marginFee(),
                addLocalRequest.rentalFee()
        );
        return localRepository.saveAndFlush(local);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Local> getActiveLocals() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public List<Local> getUnapprovedLocals() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public List<Local> getOwnLocals(UUID id) {
        return localRepository.findAllByOwnerId(id);
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
    public Local leaveLocal(UUID ownerId, UUID localId) throws InvalidLocalState, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Local setFixedFee(UUID localId, BigDecimal marginFee, BigDecimal rentalFee) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public List<Local> getAllLocals() {
        return localRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Local changeLocalAddress(UUID id, Address address) throws GivenAddressAssignedToOtherLocalException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Local editLocalByAdmin(UUID id, Local newLocal) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Local approveLocal(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Local rejectLocal(UUID id) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public List<LocalReportResponse> getAllReports(UUID ownerId) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Local archiveLocal(UUID id) throws NotFoundException, InvalidLocalState {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
