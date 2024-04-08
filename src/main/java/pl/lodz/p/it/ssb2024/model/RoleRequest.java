package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "role_requests")
@Getter
public class RoleRequest extends AbstractEntity {
    @Column(name = "request_date", nullable = false, updatable = false)
    LocalDate requestDate;

    @OneToOne
    @JoinColumn(name = "tenant_id", nullable = false, updatable = false, unique = true)
    Tenant tenant;

    public RoleRequest(LocalDate requestDate, Tenant tenant) {
        this.requestDate = requestDate;
        this.tenant = tenant;
    }
}
