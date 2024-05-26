package pl.lodz.p.it.ssbd2024.model.tokens;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2024.model.User;

import java.time.Instant;

@DiscriminatorValue("PASSWORD")
@Entity
@NoArgsConstructor
public class PasswordVerificationToken extends VerificationToken {

    public static int EXPIRATION_TIME = 15;

    public PasswordVerificationToken(String token, Instant expirationDate, User user) {
        super(token, expirationDate, user);
    }
}
