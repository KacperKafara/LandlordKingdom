package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@DiscriminatorValue("ADMINISTRATOR")
@Table(name = "administrators")
public class Administrator extends AccessLevel {

}
