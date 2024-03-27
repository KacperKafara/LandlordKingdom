package pl.lodz.p.it.ssb2024.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "variable_fees")
public class VariableFee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", name = "id")
    private UUID id;

    @Column(nullable = false, updatable = false)
    private BigDecimal amount;

    @Column(nullable = false, updatable = false)
    private LocalDate date;

    @ManyToOne
    private Rent rent;

    public VariableFee(BigDecimal amount, LocalDate date, Rent rent) {
        this.amount = amount;
        this.date = date;
        this.rent = rent;
    }
}
