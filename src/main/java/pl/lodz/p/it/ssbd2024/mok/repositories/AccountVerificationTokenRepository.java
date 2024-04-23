package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssbd2024.model.AccountVerificationToken;


import java.util.Optional;
import java.util.UUID;

public interface AccountVerificationTokenRepository extends JpaRepository<AccountVerificationToken, UUID> {

    Optional<AccountVerificationToken> findByToken(String token);

    Optional<AccountVerificationToken> findByUserId(UUID id);

    void deleteByUserId(UUID id);


}
