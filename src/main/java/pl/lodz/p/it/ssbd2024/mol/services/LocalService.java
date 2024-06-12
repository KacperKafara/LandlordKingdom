package pl.lodz.p.it.ssbd2024.mol.services;

import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.lodz.p.it.ssbd2024.exceptions.IdenticalFieldValueException;
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

    Page<Local> getOwnLocals(UUID id, Pageable pageable, String state);

    LocalReportResponse getLocalReport(UUID id) throws NotFoundException;

    Local editLocal(UUID id, Local local) throws NotFoundException;

    Local leaveLocal(UUID userId, UUID localId) throws InvalidLocalState, NotFoundException;

    Local setFixedFee(UUID localId, UUID ownerId, BigDecimal marginFee, BigDecimal rentalFee) throws NotFoundException;

    Page<Local> getAllLocals(Pageable pageable, String state, String ownerLogin);

    Local changeLocalAddress(UUID id, Address address) throws GivenAddressAssignedToOtherLocalException, NotFoundException;

    Local editLocalByAdmin(UUID id, Local newLocal) throws NotFoundException;

    Local approveLocal(UUID id) throws NotFoundException, InvalidLocalState;

    Local rejectLocal(UUID id) throws NotFoundException, InvalidLocalState;

    List<LocalReportResponse> getAllReports(UUID ownerId) throws NotFoundException;

    Local archiveLocal(UUID id) throws NotFoundException, InvalidLocalState;

    Local getLocal(UUID id) throws NotFoundException;

    Local getOwnLocal(UUID id, UUID ownerId) throws NotFoundException;

    Local getActiveLocal(UUID id) throws NotFoundException;
}
