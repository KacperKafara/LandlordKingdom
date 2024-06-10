package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "applications",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tenant_id", "local_id"})
        },
        indexes = {
                @Index(name = "idx_application_local_id", columnList = "local_id"),
                @Index(name = "idx_application_tenant_id", columnList = "tenant_id")
        })
@NoArgsConstructor
@Getter
public class Application extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false, updatable = false)
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false, updatable = false)
    private Local local;

    public Application(Tenant tenant, Local local) {
        this.tenant = tenant;
        this.local = local;
    }
}
