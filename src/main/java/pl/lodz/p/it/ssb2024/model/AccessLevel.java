package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "access_levels", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "level"})})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "level")
public class AccessLevel extends AbstractEntity {
    @Column(name = "level", nullable = false, updatable = false)
    private String level;

    @Setter
    @Column(name="active", nullable = false)
    private boolean active;

    @JoinColumn(name = "user_id",referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private User user;
}
