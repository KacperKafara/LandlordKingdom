package pl.lodz.p.it.ssb2024.Model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", name = "id")
    private UUID id;

}
