package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    @PreAuthorize("permitAll()")
    Optional<User> findByEmail(String email);
    @PreAuthorize("permitAll()")
    Optional<User> findByGoogleId(String googleId);

    List<User> getUsersByCreatedAtBeforeAndVerifiedIsFalse(LocalDateTime createdAt);
    List<User> getUsersByCreatedAtBeforeAndCreatedAtAfterAndVerifiedIsFalse(LocalDateTime createdAt, LocalDateTime createdAt2);
}
