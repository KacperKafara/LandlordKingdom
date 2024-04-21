package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Table(
        name = "payments",
        indexes = {
                @Index(name = "idx_payment_rent", columnList = "rent_id")
        })
@Getter
public class Payment extends AbstractEntity {
    @Column(name = "amount", nullable = false, updatable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "rent_id", nullable = false, updatable = false)
    private Rent rent;

    public Payment(BigDecimal amount, LocalDate date, Rent rent) {
        this.amount = amount;
        this.date = date;
        this.rent = rent;
    }
}
