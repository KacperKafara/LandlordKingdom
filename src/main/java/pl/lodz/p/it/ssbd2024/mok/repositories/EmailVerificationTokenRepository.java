package pl.lodz.p.it.ssbd2024.mok.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssbd2024.model.EmailVerificationToken;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, UUID> {

    Optional<EmailVerificationToken> findByToken(String token);

    Optional<EmailVerificationToken> findByUserId(UUID id);


    void deleteByUserId(UUID id);

    void deleteEmailVerificationTokenByUserId(UUID id);
}
