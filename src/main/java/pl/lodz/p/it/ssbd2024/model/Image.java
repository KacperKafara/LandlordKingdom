package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Table(name = "images", indexes = {
        @Index(name = "idx_image_local_id", columnList = "local_id")
})
@Builder
@Getter
@AllArgsConstructor
public class Image extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false, updatable = false)
    private Local local;

    @Setter
    @Column(name = "image", nullable = false, length = (256*1024))
    private byte[]  image;


}
