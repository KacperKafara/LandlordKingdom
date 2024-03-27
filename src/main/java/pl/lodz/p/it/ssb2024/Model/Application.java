package pl.lodz.p.it.ssb2024.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssb2024.Model.Users.Tenant;

import java.util.UUID;

@Entity
@Table(name = "applications")
@NoArgsConstructor
@Getter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", name = "id")
    private UUID id;

    @Setter
    @Column(nullable = false, updatable = false)
    private Long order;

    @Setter
    @ManyToOne
    private Tenant interested;

    public Application(Long order, Tenant interested) {
        this.order = order;
        this.interested = interested;
    }
}
