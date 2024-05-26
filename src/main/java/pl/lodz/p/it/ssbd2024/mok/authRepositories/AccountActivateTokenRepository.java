package pl.lodz.p.it.ssbd2024.mok.authRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.tokens.AccountActivateToken;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
@PreAuthorize("permitAll()")
public interface AccountActivateTokenRepository extends JpaRepository<AccountActivateToken, UUID> {
    Optional<AccountActivateToken> findByToken(String token);

    Optional<AccountActivateToken> findByUserId(UUID id);

    void deleteByUserId(UUID id);
}
