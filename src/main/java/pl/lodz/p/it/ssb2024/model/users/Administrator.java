package pl.lodz.p.it.ssb2024.model.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("ADMINISTRATOR")
@Table(name = "administrators")
@NoArgsConstructor
public class Administrator extends User {
    public Administrator(String firstName,
                 String lastName,
                 String email,
                 String login,
                 String password,
                 int loginAttempts,
                 LocalDate lastSuccessfulLogin,
                 LocalDate lastFailedLogin,
                 boolean blocked,
                 boolean verified) {
        super(firstName,
                lastName,
                email,
                login,
                password,
                loginAttempts,
                lastSuccessfulLogin,
                lastFailedLogin,
                blocked,
                verified);
    }
}
