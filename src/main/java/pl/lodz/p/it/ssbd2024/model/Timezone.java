package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity(name = "timezones")
@Getter
public class Timezone {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)", name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, updatable = false, length = 50)
    private String name;
}
