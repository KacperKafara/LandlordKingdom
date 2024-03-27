package pl.lodz.p.it.ssb2024.Model.DomainModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssb2024.Model.Users.Tenant;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "role_requests")
@Getter
public class RoleRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", name = "id")
    private UUID id;

    @Column(nullable = false, updatable = false)
    LocalDate requestDate;

    @OneToOne
    @JoinColumn(nullable = false, updatable = false)
    Tenant tenant;

    public RoleRequest(LocalDate requestDate, Tenant tenant) {
        this.requestDate = requestDate;
        this.tenant = tenant;
    }
}
