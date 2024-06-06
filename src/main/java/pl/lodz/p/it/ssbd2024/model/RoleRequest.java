package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(
        name = "role_requests",
        indexes = {
                @Index(name = "idx_role_request_tenant_id", columnList = "tenant_id")
        })
@Getter
public class RoleRequest extends AbstractEntity {
    @Column(name = "request_date", nullable = false, updatable = false)
    LocalDateTime requestDate = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "tenant_id", nullable = false, updatable = false, unique = true)
    Tenant tenant;

    public RoleRequest(Tenant tenant) {
        this.tenant = tenant;
    }
}
