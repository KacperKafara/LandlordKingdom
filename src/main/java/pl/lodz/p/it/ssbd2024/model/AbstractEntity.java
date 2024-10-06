package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

import static pl.lodz.p.it.ssbd2024.util.UserFromContext.getCurrentUserId;

@MappedSuperclass
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "char(36)")
    private UUID id;

    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    @ToString.Exclude
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    @Setter
    @ToString.Exclude
    private LocalDateTime modifiedAt;

    @Column(name = "created_by", updatable = false, columnDefinition = "char(36)")
    @JdbcTypeCode(Types.VARCHAR)
    @ToString.Exclude
    private UUID createdBy;

    @Column(name = "modified_by", columnDefinition = "char(36)")
    @JdbcTypeCode(Types.VARCHAR)
    @Setter
    @ToString.Exclude
    private UUID modifiedBy;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
        this.createdBy = getCurrentUserId();
        this.modifiedBy = getCurrentUserId();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = getCurrentUserId();
    }
}
