package pl.lodz.p.it.ssb2024.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssb2024.Model.Users.Tenant;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "rents")
@NoArgsConstructor
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", name = "id")
    @Getter
    private UUID id;

    @OneToOne
    @Column(name = "local_id", nullable = false, updatable = false)
    @Getter
    private Local local;

    @OneToOne
    @Column(name = "tenant_id", nullable = false, updatable = false)
    @Getter
    private Tenant tenant;

    @Column(name = "start_date", nullable = false, updatable = false)
    @Getter
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @Getter @Setter
    private LocalDate endDate;

    @Column(nullable = false)
    @Getter @Setter
    private BigDecimal balance;

    public Rent(Local local, Tenant tenant, LocalDate startDate, LocalDate endDate, BigDecimal balance) {
        this.local = local;
        this.tenant = tenant;
        this.startDate = startDate;
        this.endDate = endDate;
        this.balance = balance;
    }
}
