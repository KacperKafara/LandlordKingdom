package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@DiscriminatorValue("ADMINISTRATOR")
@Table(name = "administrators")
public class Administrator extends AccessLevel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
