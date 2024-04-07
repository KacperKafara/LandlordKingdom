package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "applications")
@NoArgsConstructor
@Getter
public class Application extends AbstractEntity {
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
