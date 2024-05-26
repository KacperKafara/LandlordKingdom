package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReportResponse;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.services.LocalService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalServiceImpl implements LocalService {
    private final LocalRepository localRepository;

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Local addLocal(Local local) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public List<Local> getOwnLocals(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public LocalReportResponse getLocalReport(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Local editLocal(UUID id, Local local) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
