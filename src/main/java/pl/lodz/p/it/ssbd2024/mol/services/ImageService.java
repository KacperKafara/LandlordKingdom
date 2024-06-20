package pl.lodz.p.it.ssbd2024.mol.services;

import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.it.ssbd2024.exceptions.CreationException;
import pl.lodz.p.it.ssbd2024.exceptions.ImageFormatNotSupported;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Image;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalImagesResponse;

import java.util.List;
import java.util.UUID;

public interface ImageService {
    void store(MultipartFile file, UUID localId) throws NotFoundException, ImageFormatNotSupported, CreationException;

    Image getImage(UUID id) throws NotFoundException;

    List<UUID> getImagesByLocalId(UUID id) throws NotFoundException;

    void deleteImage(UUID id) throws NotFoundException;
}
