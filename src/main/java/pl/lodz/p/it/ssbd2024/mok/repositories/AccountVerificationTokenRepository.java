package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.AccountVerificationToken;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountVerificationTokenRepository extends JpaRepository<AccountVerificationToken, UUID> {

    Optional<AccountVerificationToken> findByToken(String token);

    Optional<AccountVerificationToken> findByUserId(UUID id);

    void deleteByUserId(UUID id);


}
