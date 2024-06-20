package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.it.ssbd2024.exceptions.ImageFormatNotSupported;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.LocalExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Image;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.mol.repositories.ImageRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.services.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final LocalRepository localRepository;

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public void store(MultipartFile file, UUID localId) throws NotFoundException, ImageFormatNotSupported {
        Local local = localRepository.findById(localId).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
        if(!Objects.equals(file.getContentType(), "image/jpeg") && !Objects.equals(file.getContentType(), "image/png")) {
            throw new ImageFormatNotSupported(LocalExceptionMessages.IMAGE_FORMAT_NOT_SUPPORTED, ErrorCodes.IMAGE_FORMAT_NOT_SUPPORTED);
        }

        try {
            imageRepository.saveAndFlush(new Image(local, file.getBytes(), file.getContentType()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @PreAuthorize("permitAll()")
    public Image getImage(UUID id) throws NotFoundException {
        return imageRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.IMAGE_NOT_FOUND, ErrorCodes.IMAGE_NOT_FOUND));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<UUID> getImagesByLocalId(UUID id) throws NotFoundException {
        localRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
        return imageRepository.findImageIdsByLocalId(id);
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public void deleteImage(UUID id) throws NotFoundException {
        imageRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.IMAGE_NOT_FOUND, ErrorCodes.IMAGE_NOT_FOUND));
        imageRepository.deleteById(id);
        imageRepository.flush();
    }
}
