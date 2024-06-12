package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(
        name = "locals",
        indexes = {
                @Index(name = "idx_local_address", columnList = "address_id"),
                @Index(name = "idx_local_owner", columnList = "owner_id")
        })
@Getter
public class Local extends AbstractEntity {
    @Setter
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Setter
    @Column(name = "description", nullable = false, length = 5000)
    private String description;

    @Setter
    @Column(name = "size", nullable = false)
    private int size;

    @Enumerated(EnumType.ORDINAL)
    @Setter
    @Column(name = "state", nullable = false)
    private LocalState state = LocalState.UNAPPROVED;

    @ManyToOne
    @Setter
    @JoinColumn(name = "address_id", nullable = false, updatable = true)
    private Address address;

    @Setter
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Setter
    @Column(name = "margin_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal marginFee;

    @Setter
    @Column(name = "next_margin_fee", precision = 10, scale = 2)
    private BigDecimal nextMarginFee;

    @Setter
    @Column(name = "rental_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal rentalFee;

    @Setter
    @Column(name = "next_rental_fee", precision = 10, scale = 2)
    private BigDecimal nextRentalFee;


    @OneToMany(mappedBy = "local")
    private List<Rent> rents = new ArrayList<>();

    public Local(String name,
                 String description,
                 int size,
                 Address address,
                 Owner owner,
                 BigDecimal marginFee,
                 BigDecimal rentalFee) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.address = address;
        this.owner = owner;
        this.marginFee = marginFee;
        this.rentalFee = rentalFee;
    }
}
