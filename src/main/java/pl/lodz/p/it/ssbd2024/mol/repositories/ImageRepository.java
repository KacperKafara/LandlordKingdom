package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Image;
import pl.lodz.p.it.ssbd2024.model.Local;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ImageRepository extends JpaRepository<Image, UUID> {


    @NonNull
    @PreAuthorize("hasRole('OWNER')")
    Image saveAndFlush(@NonNull Local local);

    @PreAuthorize("hasRole('OWNER')")
    @Query("SELECT image.id FROM Image image WHERE image.local.id = :localId")
    List<UUID> findImageIdsByLocalId(UUID localId);


}
