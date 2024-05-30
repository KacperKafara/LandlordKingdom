package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.lodz.p.it.ssbd2024.model.UserFilter;

import java.util.Optional;
import java.util.UUID;

public interface UserFilterRepository extends JpaRepository<UserFilter, UUID> {
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    Optional<UserFilter> findByUserId(UUID userId);

    @NonNull
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    UserFilter saveAndFlush(@NonNull UserFilter userFilter);
}
