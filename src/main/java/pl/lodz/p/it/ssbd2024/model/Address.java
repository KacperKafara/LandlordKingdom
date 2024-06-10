package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses", uniqueConstraints = {@UniqueConstraint(columnNames = {"number", "street", "city", "zip", "country"})})
public class Address extends AbstractEntity {
    @Column(name = "country", nullable = false, length = 100)
    private String country;
    @Column(name = "number", nullable = false, length = 10)
    private String number;
    @Column(name = "street", nullable = false, length = 100)
    private String street;
    @Column(name = "city", nullable = false, length = 100)
    private String city;
    @Column(name = "zip", nullable = false, length = 10)
    private String zip;

    @OneToMany(mappedBy = "address")
    private List<Local> locals = new ArrayList<>();

    public void setAddress(Address address) {
        this.number = address.getNumber();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.zip = address.getZip();
        this.country = address.getCountry();
    }

    public Address(String country, String city, String street, String number, String zip) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zip = zip;
    }
}
