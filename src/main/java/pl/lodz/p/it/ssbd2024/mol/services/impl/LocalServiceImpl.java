package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.LocalMessages;
import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.model.LocalState;
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

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Local addLocal(Local local) throws GivenAddressAssignedToOtherLocalException, NotFoundException {
        Optional<Address> existingAddress = addressRepository.findByNumberAndStreetAndCityAndZipAndCountry(
                local.getAddress().getNumber(),
                local.getAddress().getStreet(),
                local.getAddress().getCity(),
                local.getAddress().getZip(),
                local.getAddress().getCountry()
        );
        if (existingAddress.isPresent()) {
            Local existingLocal = localRepository.findByAddress(local.getAddress()).orElseThrow(() -> new
                    NotFoundException(LocalMessages.NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
            if (existingLocal.getState() == LocalState.WITHOUT_OWNER) {
                existingLocal.setOwner(local.getOwner());
                existingLocal.setName(local.getName());
                existingLocal.setDescription(local.getDescription());
                existingLocal.setState(LocalState.UNAPPROVED);
                return localRepository.saveAndFlush(existingLocal);
            } else throw new GivenAddressAssignedToOtherLocalException(LocalMessages.ADDRESS_ASSIGNED,
                                                                       ErrorCodes.ADDRESS_ASSIGNED);
        } else {
            addressRepository.saveAndFlush(local.getAddress());
        }
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
        throw new UnsupportedOperationException("Not supported yet.");
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
    public List<Local> getAllLocals(){
        throw new UnsupportedOperationException("Not supported yet.");
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
