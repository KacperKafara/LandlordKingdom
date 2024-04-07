package pl.lodz.p.it.ssb2024.model.domainmodel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssb2024.model.users.Tenant;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "rents")
@NoArgsConstructor
@Getter
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Local local;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Tenant tenant;

    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @Setter
    private LocalDate endDate;

    @Column(nullable = false, precision = 10, scale = 2)
    @Setter
    private BigDecimal balance;

    public Rent(Local local,
                Tenant tenant,
                LocalDate startDate,
                LocalDate endDate,
                BigDecimal balance) {
        this.local = local;
        this.tenant = tenant;
        this.startDate = startDate;
        this.endDate = endDate;
        this.balance = balance;
    }
}
