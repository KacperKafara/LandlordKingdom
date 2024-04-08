package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@DiscriminatorValue("OWNER")
@Table(name = "owners")
public class Owner extends AccessLevel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
