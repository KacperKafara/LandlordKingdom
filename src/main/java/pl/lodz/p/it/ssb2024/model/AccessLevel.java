package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Entity
@Getter
@NoArgsConstructor
@Table(name = "access_levels", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "level"})})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "level")
public abstract class AccessLevel extends AbstractEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "active", nullable = false)
    private boolean active;

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private User user;
}
