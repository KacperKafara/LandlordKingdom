package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "fixed_fees",
        indexes = {
                @Index(name = "idx_fixed_fee_rent_id", columnList = "rent_id")
        })
@Getter
@NoArgsConstructor
public class FixedFee extends AbstractEntity {
    @Column(name = "rental_fee", nullable = false, updatable = false, precision = 10, scale = 2)
    @NotNull(message = "fixed fee cannot be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fixed fee must be greater than 0.")
    @DecimalMax(value = "1000000", inclusive = true, message = "Fixed fee must be less than or equal to 1 000 000.00")
    @Digits(integer = 10, fraction = 2, message = "Fixed fee must be a valid monetary amount with up to 10 integer digits and 2 fractional digits.")
    private BigDecimal rentalFee;

    @Column(name = "margin_fee", nullable = false, updatable = false, precision = 10, scale = 2)
    @NotNull(message = "fixed fee cannot be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fixed fee must be greater than 0.")
    @DecimalMax(value = "1000000", inclusive = true, message = "Fixed fee must be less than or equal to 1 000 000.00")
    @Digits(integer = 10, fraction = 2, message = "Fixed fee must be a valid monetary amount with up to 10 integer digits and 2 fractional digits.")
    private BigDecimal marginFee;

    @Column(name = "date", nullable = false, updatable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "rent_id", nullable = false, updatable = false)
    @NotNull
    private Rent rent;

    public FixedFee(BigDecimal rentalFee, BigDecimal marginFee, LocalDate date, Rent rent) {
        this.rentalFee = rentalFee;
        this.marginFee = marginFee;
        this.date = date;
        this.rent = rent;
    }
}
