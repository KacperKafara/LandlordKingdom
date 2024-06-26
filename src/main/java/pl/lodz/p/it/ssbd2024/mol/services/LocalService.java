package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.exceptions.ApplicationOptimisticLockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.mol.dto.EditLocalRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.EditLocalRequestAdmin;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReportResponse;
import pl.lodz.p.it.ssbd2024.exceptions.GivenAddressAssignedToOtherLocalException;
import pl.lodz.p.it.ssbd2024.exceptions.InvalidLocalState;
import pl.lodz.p.it.ssbd2024.util.translate.LocalsLanguage;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LocalService {

    Local addLocal(Local local, UUID ownerId) throws GivenAddressAssignedToOtherLocalException, NotFoundException, CreationException;

    LocalsLanguage getActiveLocals();

    List<Local> getUnapprovedLocals();

    Page<Local> getOwnLocals(UUID id, Pageable pageable, String state);

    LocalReportResponse getLocalReport(UUID id) throws NotFoundException;

    Local editLocal(UUID userId, UUID localId, EditLocalRequest editLocalRequest, String tagValue) throws NotFoundException, ApplicationOptimisticLockException;

    Local leaveLocal(UUID userId, UUID localId) throws InvalidLocalState, NotFoundException;

    Local setFixedFee(UUID localId, UUID ownerId, BigDecimal marginFee, BigDecimal rentalFee) throws NotFoundException;

    Page<Local> getAllLocals(Pageable pageable, String state, String ownerLogin);

    Local changeLocalAddress(UUID id, Address address) throws GivenAddressAssignedToOtherLocalException, NotFoundException;

    Local editLocalByAdmin(UUID localId, EditLocalRequestAdmin editLocalRequest, String tagValue) throws NotFoundException, ApplicationOptimisticLockException;

    Local approveLocal(UUID id) throws NotFoundException, InvalidLocalState;

    Local rejectLocal(UUID id) throws NotFoundException, InvalidLocalState;

    List<LocalReportResponse> getAllReports(UUID ownerId) throws NotFoundException;

    Local archiveLocal(UUID id) throws NotFoundException, InvalidLocalState;

    Local getLocal(UUID id) throws NotFoundException;

    Local getOwnLocal(UUID id, UUID ownerId) throws NotFoundException;

    LocalsLanguage getActiveLocal(UUID id) throws NotFoundException;
}
