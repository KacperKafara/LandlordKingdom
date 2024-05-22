package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@PreAuthorize("permitAll()")
@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);
    Optional<User> findByGoogleId(String googleId);
    @NonNull
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    List<User> findAll();

    List<User> getUsersByCreatedAtBeforeAndVerifiedIsFalse(LocalDateTime createdAt);
    List<User> getUsersByCreatedAtBeforeAndCreatedAtAfterAndVerifiedIsFalse(LocalDateTime createdAt, LocalDateTime createdAt2);
}
