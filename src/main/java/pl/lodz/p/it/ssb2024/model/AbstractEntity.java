package pl.lodz.p.it.ssb2024.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@MappedSuperclass
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", name = "id")
    private UUID id;

    @Column(name = "version", nullable = false)
    @Version
    private Long version;
}
