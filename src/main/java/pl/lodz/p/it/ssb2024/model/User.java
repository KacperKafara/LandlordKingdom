package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@Getter
@SecondaryTable(name = "personal_data", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
@NoArgsConstructor
public class User extends AbstractEntity {
    @Setter
    @Column(name = "first_name", table = "personal_data", nullable = false, length = 50)
    private String firstName;
    @Setter
    @Column(name = "last_name", table = "personal_data", nullable = false, length = 50)
    private String lastName;
    @Setter
    @Column(name = "email", table = "personal_data", nullable = false, unique = true, length = 50)
    private String email;


    @Column(name = "login", nullable = false, updatable = false, unique = true, length = 50)
    private String login;

    @Setter
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Setter
    @Column(name = "login_attempts", nullable = false)
    private int loginAttempts = 0;

    @Setter
    @Column(name = "last_successful_login")
    private LocalDate lastSuccessfulLogin;

    @Setter
    @Column(name = "last_failed_login")
    private LocalDate lastFailedLogin;

    @Setter
    @Column(name = "blocked", nullable = false)
    private boolean blocked = false;

    @Setter
    @Column(name = "verified", nullable = false)
    private boolean verified = false;

    public User(String firstName,
                String lastName,
                String email,
                String login,
                String password,
                int loginAttempts,
                LocalDate lastSuccessfulLogin,
                LocalDate lastFailedLogin,
                boolean blocked,
                boolean verified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.password = password;
        this.loginAttempts = loginAttempts;
        this.lastSuccessfulLogin = lastSuccessfulLogin;
        this.lastFailedLogin = lastFailedLogin;
        this.blocked = blocked;
        this.verified = verified;
    }

    public User(String firstName,
                String lastName,
                String email,
                String login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.password = "";
        this.loginAttempts = 0;
        this.lastSuccessfulLogin = null;
        this.lastFailedLogin = null;
        this.blocked = false;
        this.verified = false;
    }
}
