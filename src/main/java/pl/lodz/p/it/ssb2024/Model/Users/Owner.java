package pl.lodz.p.it.ssb2024.Model.Users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("OWNER")
@Table(name = "owners")
@NoArgsConstructor
public class Owner extends User{
    public Owner(String firstName,
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
