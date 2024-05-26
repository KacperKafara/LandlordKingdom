package pl.lodz.p.it.ssbd2024.model.tokens;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2024.model.User;

import java.time.Instant;

@DiscriminatorValue("ACTIVATE_ACCOUNT")
@Entity
@NoArgsConstructor
public class AccountActivateToken extends VerificationToken {

    public static int EXPIRATION_TIME = 24 * 60;

    public AccountActivateToken(String token, Instant expirationDate, User user) {
        super(token, expirationDate, user);
    }
}
