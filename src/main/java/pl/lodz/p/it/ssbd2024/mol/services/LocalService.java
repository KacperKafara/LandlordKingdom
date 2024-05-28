package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.model.Application;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReportResponse;
import pl.lodz.p.it.ssbd2024.mol.exceptions.GivenAddressAssignedToOtherLocalException;
import pl.lodz.p.it.ssbd2024.mol.exceptions.InvalidLocalState;
import pl.lodz.p.it.ssbd2024.mol.exceptions.InvalidRelationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LocalService {

    Local addLocal(Local local, UUID ownerId) throws GivenAddressAssignedToOtherLocalException, NotFoundException;

    List<Local> getActiveLocals();

    List<Local> getUnapprovedLocals();

    List<Local> getOwnLocals(UUID id);

    LocalReportResponse getLocalReport(UUID id) throws NotFoundException;

    Local editLocal(UUID id, Local local) throws NotFoundException;

    Local leaveLocal(UUID ownerId, UUID localId) throws InvalidLocalState;

    Local setFixedFee(UUID localId, BigDecimal marginFee, BigDecimal rentalFee) throws NotFoundException;

    List<Local> getAllLocals();

    Local changeLocalAddress(UUID id, Address address) throws GivenAddressAssignedToOtherLocalException, NotFoundException;

    Local editLocalByAdmin(UUID id, Local newLocal) throws NotFoundException;

    Application createApplication(UUID localId, UUID userId) throws NotFoundException, InvalidLocalState;

    void deleteApplication(UUID applicationId, UUID userId) throws NotFoundException, InvalidRelationException;

    Local approveLocal(UUID id);

    List<LocalReportResponse> getAllReports(UUID ownerId);
}
