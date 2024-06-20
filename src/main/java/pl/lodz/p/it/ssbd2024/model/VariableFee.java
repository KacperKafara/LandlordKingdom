package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "variable_fees",
        indexes = {
                @Index(name = "idx_variable_fee_rent_id", columnList = "rent_id")
        })

public class VariableFee extends AbstractEntity {
    @NotNull(message = "Payment cannot be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Payment must be greater than 0.")
    @DecimalMax(value = "1000000", inclusive = true, message = "Payment must be less than or equal to 1 000 000.00")
    @Digits(integer = 10, fraction = 2, message = "Payment must be a valid monetary amount with up to 10 integer digits and 2 fractional digits.")
    @Column(name = "amount", nullable = false, updatable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date;

    @ManyToOne
    @NotNull(message = "Payment rent cannot be null.")
    @JoinColumn(name = "rent_id", nullable = false, updatable = false)
    private Rent rent;

    public VariableFee(BigDecimal amount, LocalDate date, Rent rent) {
        this.amount = amount;
        this.date = date;
        this.rent = rent;
    }
}
