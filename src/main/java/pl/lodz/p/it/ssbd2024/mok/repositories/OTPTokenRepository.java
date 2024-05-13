package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.OTPToken;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OTPTokenRepository extends JpaRepository<OTPToken, UUID> {
    Optional<OTPToken> findByToken(String token);

    Optional<OTPToken> findByUserId(UUID id);

    void deleteByUserId(UUID id);

}
