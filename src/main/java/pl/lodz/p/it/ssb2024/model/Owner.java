package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("OWNER")
@Table(name = "owners")
public class Owner extends AccessLevel {
}
