package pl.lodz.p.it.ssbd2024.mok.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssbd2024.model.PasswordVerificationToken;


import java.util.Optional;
import java.util.UUID;

public interface PasswordVerificationTokenRepository extends JpaRepository<PasswordVerificationToken, UUID> {

    Optional<PasswordVerificationToken> findByToken(String token);

    Optional<PasswordVerificationToken> findByUserId(UUID id);

    void deleteByUserId(UUID id);

}
