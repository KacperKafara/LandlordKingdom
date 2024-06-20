package pl.lodz.p.it.ssbd2024.mol.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.CreationException;
import pl.lodz.p.it.ssbd2024.exceptions.ImageFormatNotSupported;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Image;
import pl.lodz.p.it.ssbd2024.mol.services.ImageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Scope("prototype")
@Transactional(propagation = Propagation.NEVER)
public class ImageController {
    private final ImageService imageService;

    @PostMapping(path = "/upload/{localId}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable UUID localId) throws NotFoundException {
        try {
            imageService.store(file, localId);
            return ResponseEntity.ok().build();
        } catch (ImageFormatNotSupported e) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage(), e);
        } catch (CreationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<byte[]> getImage(@PathVariable UUID id) throws NotFoundException {
        Image image = imageService.getImage(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, image.getType())
                .body(image.getImage());
    }


    @GetMapping(path = "/local/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UUID>> getLocalImagesIds(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(imageService.getImagesByLocalId(id));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID id) throws NotFoundException {
        imageService.deleteImage(id);
        return ResponseEntity.ok().build();
    }
}
