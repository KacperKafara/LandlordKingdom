package pl.lodz.p.it.ssb2024.Model;

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

    @ManyToOne
    Tenant tenant;
}
