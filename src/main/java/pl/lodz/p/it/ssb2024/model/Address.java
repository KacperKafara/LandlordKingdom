package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses", uniqueConstraints = {@UniqueConstraint(columnNames = {"number", "street", "city", "zip", "country"})})
public class Address extends AbstractEntity {
    @Column(name = "number", nullable = false, length = 10)
    private String number;
    @Column(name = "street", nullable = false, length = 100)
    private String street;
    @Column(name = "city", nullable = false, length = 100)
    private String city;
    @Column(name = "zip", nullable = false, length = 10)
    private String zip;
    @Column(name = "country", nullable = false, length = 100)
    private String country;
}
