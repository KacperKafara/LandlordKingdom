package pl.lodz.p.it.ssbd2024.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@DiscriminatorValue("ACCOUNT")
@Entity
@NoArgsConstructor
public class AccountVerificationToken extends VerificationToken{
    public static int EXPIRATION_TIME = 24 * 60;

    public AccountVerificationToken(String token, Instant expirationDate, User user) {
        super(token, expirationDate, user);
    }
}
