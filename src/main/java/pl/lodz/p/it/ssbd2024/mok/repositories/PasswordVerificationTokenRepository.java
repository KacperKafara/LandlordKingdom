package pl.lodz.p.it.ssbd2024.mok.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.tokens.PasswordVerificationToken;


import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
@PreAuthorize("permitAll()")
public interface PasswordVerificationTokenRepository extends JpaRepository<PasswordVerificationToken, UUID> {

    Optional<PasswordVerificationToken> findByToken(String token);

    Optional<PasswordVerificationToken> findByUserId(UUID id);

    void deleteByUserId(UUID id);

}
