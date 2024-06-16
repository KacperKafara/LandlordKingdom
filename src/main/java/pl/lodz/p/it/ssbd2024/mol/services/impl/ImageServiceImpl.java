package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final LocalRepository localRepository;

    @Override
    public void store(MultipartFile file, UUID localId) throws NotFoundException {
        Local local = localRepository.findById(localId).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
        try {
            imageRepository.saveAndFlush(new Image(local, file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Image getImage(UUID id) throws NotFoundException {
        return imageRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.IMAGE_NOT_FOUND, ErrorCodes.IMAGE_NOT_FOUND));
    }

    @Override
    public List<UUID> getImagesByLocalId(UUID id) {
        return imageRepository.findImageIdsByLocalId(id);
    }

    @Override
    public void deleteImage(UUID id) throws NotFoundException {
        imageRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.IMAGE_NOT_FOUND, ErrorCodes.IMAGE_NOT_FOUND));
        imageRepository.deleteById(id);
        imageRepository.flush();
    }
}
