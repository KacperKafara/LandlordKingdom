package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.mol.dto.AllLocalsReport;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReport;

import java.time.LocalDate;
import java.util.UUID;

public interface ReportService {
    LocalReport getLocalReport(UUID localId, UUID userId, LocalDate startDate, LocalDate endDate)
            throws NotFoundException;

    AllLocalsReport getReport(UUID userId);
}
