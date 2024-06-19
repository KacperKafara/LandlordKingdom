package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Blob;
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
    @Size(min = 1, max = 200, message = "Name length has to be between 1 and 200")
    private String name;

    @Setter
    @Column(name = "description", nullable = false, length = 5000)
    @Size(min = 1, max = 1500, message = "Description length has to be between 1 and 1500")
    private String description;

    @Setter
    @Column(name = "size", nullable = false)
    @Min(value = 1, message = "Size has to be greater than 0")
    private int size;

    @Enumerated(EnumType.ORDINAL)
    @Setter
    @Column(name = "state", nullable = false)
    private LocalState state = LocalState.UNAPPROVED;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @Setter
    @JoinColumn(name = "address_id", nullable = false, updatable = true)
    private Address address;

    @Setter
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Setter
    @Column(name = "margin_fee", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Margin fee cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Margin fee must be greater than 0")
    @DecimalMax(value = "1000000", inclusive = true, message = "Margin fee must be less than or equal to 1 000 000.00")
    @Digits(integer = 10, fraction = 2, message = "Margin fee must be a valid monetary amount with up to 10 integer digits and 2 fractional digits")
    private BigDecimal marginFee;

    @Setter
    @Column(name = "next_margin_fee", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Next margin fee must be greater than 0")
    @DecimalMax(value = "1000000", inclusive = true, message = "Next margin fee must be less than or equal to 1 000 000.00")
    @Digits(integer = 10, fraction = 2, message = "Next margin fee must be a valid monetary amount with up to 10 integer digits and 2 fractional digits")
    private BigDecimal nextMarginFee;

    @Setter
    @Column(name = "rental_fee", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Rental fee cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Rental fee must be greater than 0")
    @DecimalMax(value = "1000000", inclusive = true, message = "Rental fee must be less than or equal to 1 000 000.00")
    @Digits(integer = 10, fraction = 2, message = "Rental fee must be a valid monetary amount with up to 10 integer digits and 2 fractional digits")
    private BigDecimal rentalFee;

    @Setter
    @Column(name = "next_rental_fee", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Next rental fee must be greater than 0")
    @DecimalMax(value = "1000000", inclusive = true, message = "Next rental fee must be less than or equal to 1 000 000.00")
    @Digits(integer = 10, fraction = 2, message = "Next rental fee must be a valid monetary amount with up to 10 integer digits and 2 fractional digits")
    private BigDecimal nextRentalFee;

    @Setter
    @Column(name = "images")
    @OneToMany(mappedBy = "local")
    private List<Image> images = new ArrayList<>();

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
