package pl.lodz.p.it.ssbd2024.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Table(name = "tokens",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "purpose"})
},
        indexes = {
                @Index(name = "idx_token_user_id", columnList = "user_id")
        }

)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "purpose")
public abstract class VerificationToken extends AbstractEntity implements Serializable {

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expiration_date", nullable = false)
    private Instant expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;
}
