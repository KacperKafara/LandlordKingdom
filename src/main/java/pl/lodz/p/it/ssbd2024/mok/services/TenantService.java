package pl.lodz.p.it.ssbd2024.mok.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.ssbd2024.exceptions.AccessLevelAlreadyAssignedException;
import pl.lodz.p.it.ssbd2024.exceptions.AccessLevelAlreadyTakenException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Tenant;

import java.util.UUID;

public interface TenantService {

    Page<Tenant> getAllFiltered(Specification<Tenant> specification, Pageable pageable);

    Tenant addTenantAccessLevel(UUID id) throws NotFoundException, AccessLevelAlreadyAssignedException;

    Tenant removeTenantAccessLevel(UUID id) throws NotFoundException, AccessLevelAlreadyTakenException;
}
