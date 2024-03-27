package pl.lodz.p.it.ssb2024.Model.DomainModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(name = "order_number", nullable = false, updatable = false)
    private Long order;

    @ManyToOne
    @JoinColumn(name = "interested_tenant_id", nullable = false, updatable = false)
    private Tenant interested;

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false, updatable = false)
    private Local local;

    public Application(Long order, Tenant interested, Local local) {
        this.order = order;
        this.interested = interested;
        this.local = local;
    }
}
