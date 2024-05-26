package pl.lodz.p.it.ssbd2024.model.tokens;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2024.model.User;

import java.time.Instant;

@DiscriminatorValue("OTP")
@Entity
@NoArgsConstructor
public class OTPToken extends VerificationToken {

    public static int EXPIRATION_TIME = 5;

    public OTPToken(String token, Instant expirationDate, User user) {
        super(token, expirationDate, user);
    }
}
