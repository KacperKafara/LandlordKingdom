package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "variable_fees",
        indexes = {
                @Index(name = "idx_variable_fee_rent_id", columnList = "rent_id")
        })

public class VariableFee extends AbstractEntity {
    @Column(name = "amount", nullable = false, updatable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "rent_id", nullable = false, updatable = false)
    private Rent rent;

    public VariableFee(BigDecimal amount, LocalDate date, Rent rent) {
        this.amount = amount;
        this.date = date;
        this.rent = rent;
    }
}
