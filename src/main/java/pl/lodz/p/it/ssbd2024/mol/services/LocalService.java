package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReportResponse;

import java.util.List;
import java.util.UUID;

public interface LocalService {
    Local addLocal(Local local);
    List<Local> getOwnLocals(UUID id);
    LocalReportResponse getLocalReport(UUID id);
    Local editLocal(UUID id, Local local);
}
