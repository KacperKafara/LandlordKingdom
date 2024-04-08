package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@DiscriminatorValue("TENANT")
@Table(name = "tenants")
public class Tenant extends AccessLevel {
}
