package pl.lodz.p.it.ssb2024.Model.Users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("ADMINISTRATOR")
@Table(name = "administrators")
public class Administrator extends User {
}
