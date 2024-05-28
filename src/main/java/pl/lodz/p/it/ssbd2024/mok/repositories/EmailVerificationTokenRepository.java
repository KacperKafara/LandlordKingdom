package pl.lodz.p.it.ssbd2024.mok.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.tokens.EmailVerificationToken;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
@PreAuthorize("permitAll()")
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, UUID> {

    Optional<EmailVerificationToken> findByToken(String token);

    Optional<EmailVerificationToken> findByUserId(UUID id);

    @NonNull
    @PreAuthorize("isAuthenticated()")
    EmailVerificationToken saveAndFlush(@NonNull EmailVerificationToken emailVerificationToken);

    @PreAuthorize("isAuthenticated()")
    void deleteEmailVerificationTokenByUserId(UUID id);
}
