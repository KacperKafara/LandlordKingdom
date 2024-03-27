package pl.lodz.p.it.ssb2024.Model.Users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("TENANT")
@Table(name = "tenants")
public class Tenant extends User{
}
