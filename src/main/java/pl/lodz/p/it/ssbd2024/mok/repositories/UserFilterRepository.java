package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssbd2024.model.UserFilter;

import java.util.Optional;
import java.util.UUID;

public interface UserFilterRepository extends JpaRepository<UserFilter, UUID> {
    Optional<UserFilter> findByUserId(UUID userId);
}
