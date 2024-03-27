package pl.lodz.p.it.ssb2024.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "fixed_fees")
@Getter
@NoArgsConstructor
public class FixedFee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", name = "id")
    private UUID id;

    @Column(name = "rental_fee", nullable = false, updatable = false)
    private BigDecimal rentalFee;

    @Column(name = "margin_fee", nullable = false, updatable = false)
    private BigDecimal marginFee;

    @Column(nullable = false, updatable = false)
    private LocalDate date;

    @ManyToOne
    private Rent rent;

    public FixedFee(BigDecimal rentalFee, BigDecimal marginFee, LocalDate date, Rent rent) {
        this.rentalFee = rentalFee;
        this.marginFee = marginFee;
        this.date = date;
        this.rent = rent;
    }
}
