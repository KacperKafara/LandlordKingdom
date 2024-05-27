package pl.lodz.p.it.ssbd2024.mol.services;

import org.springframework.security.access.prepost.PreAuthorize;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReportResponse;
import pl.lodz.p.it.ssbd2024.mol.exceptions.GivenAddressAssignedToOtherLocalException;
import pl.lodz.p.it.ssbd2024.mol.exceptions.LocalIsNotInactiveException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LocalService {
    Local addLocal(Local local) throws GivenAddressAssignedToOtherLocalException;
    List<Local> getOwnLocals(UUID id);
    LocalReportResponse getLocalReport(UUID id);
    Local editLocal(UUID id, Local local);
    Local leaveLocal(UUID ownerId, UUID localId) throws LocalIsNotInactiveException;
    Local setFixedFee(UUID localId, BigDecimal marginFee, BigDecimal rentalFee);


    List<Local> getAllLocals();

    Local changeLocalAddress(UUID id, Address address) throws GivenAddressAssignedToOtherLocalException, NotFoundException;


    Local editLocalByAdmin(UUID id, Local newLocal) throws NotFoundException;
}
