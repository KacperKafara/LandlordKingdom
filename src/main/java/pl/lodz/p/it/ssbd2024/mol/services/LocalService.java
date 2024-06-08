package pl.lodz.p.it.ssbd2024.mol.services;

import org.aspectj.weaver.ast.Not;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.model.Application;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReportResponse;
import pl.lodz.p.it.ssbd2024.exceptions.GivenAddressAssignedToOtherLocalException;
import pl.lodz.p.it.ssbd2024.exceptions.InvalidLocalState;

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

    Local leaveLocal(UUID userId, UUID localId) throws InvalidLocalState, NotFoundException;

    Local setFixedFee(UUID localId, BigDecimal marginFee, BigDecimal rentalFee) throws NotFoundException;

    List<Local> getAllLocals();

    Local changeLocalAddress(UUID id, Address address) throws GivenAddressAssignedToOtherLocalException, NotFoundException;

    Local editLocalByAdmin(UUID id, Local newLocal) throws NotFoundException;

    Local approveLocal(UUID id) throws NotFoundException;

    Local rejectLocal(UUID id) throws NotFoundException;

    List<LocalReportResponse> getAllReports(UUID ownerId) throws NotFoundException;

    Local archiveLocal(UUID id) throws NotFoundException, InvalidLocalState;

    Local getLocal(UUID id) throws NotFoundException;
}
