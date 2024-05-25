package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "rents",
        indexes = {
                @Index(name = "idx_rent_local_id", columnList = "local_id"),
                @Index(name = "idx_rent_tenant_id", columnList = "tenant_id"),
                @Index(name = "idx_rent_owner_id", columnList = "owner_id"),
        })
@NoArgsConstructor
@Getter
public class Rent extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false, updatable = false)
    private Local local;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false, updatable = false)
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false, updatable = false)
    private Owner owner;

    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @Setter
    private LocalDate endDate;

    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    @Setter
    private BigDecimal balance;

    @OneToMany(mappedBy = "payment")
    private List<Payment> payments = new ArrayList<>();
    @OneToMany(mappedBy = "variable_fee")
    private List<VariableFee> variableFees = new ArrayList<>();
    @OneToMany(mappedBy = "fixed_fee")
    private List<FixedFee> fixedFees = new ArrayList<>();

    public Rent(Local local,
                Tenant tenant,
                Owner owner,
                LocalDate startDate,
                LocalDate endDate,
                BigDecimal balance) {
        this.local = local;
        this.tenant = tenant;
        this.owner = owner;
        this.startDate = startDate;
        this.endDate = endDate;
        this.balance = balance;
    }
}
