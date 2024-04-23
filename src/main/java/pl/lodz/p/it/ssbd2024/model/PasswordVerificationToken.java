package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.Instant;

@DiscriminatorValue("PASSWORD")
@Entity
@NoArgsConstructor
public class PasswordVerificationToken extends VerificationToken{

    public static int EXPIRATION_TIME = 15;

    public PasswordVerificationToken(String token, Instant expirationDate, User user) {
        super(token, expirationDate, user);
    }
}
