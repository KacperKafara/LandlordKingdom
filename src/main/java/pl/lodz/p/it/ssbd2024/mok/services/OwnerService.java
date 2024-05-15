package pl.lodz.p.it.ssbd2024.mok.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Owner;

import java.util.UUID;

public interface OwnerService {

    Page<Owner> getAllFiltered(Specification<Owner> specification, Pageable pageable);

    Owner addOwnerAccessLevel(UUID id) throws NotFoundException;

    Owner removeOwnerAccessLevel(UUID id) throws NotFoundException;
}
