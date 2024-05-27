package pl.lodz.p.it.ssbd2024.mok.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.ssbd2024.exceptions.AdministratorOwnRoleRemovalException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Administrator;

import java.util.UUID;

public interface AdministratorService {

    Page<Administrator> getAllFiltered(Specification<Administrator> specification, Pageable pageable);

    Administrator addAdministratorAccessLevel(UUID id) throws NotFoundException;

    Administrator removeAdministratorAccessLevel(UUID id, UUID administratorId) throws NotFoundException, AdministratorOwnRoleRemovalException;
}
