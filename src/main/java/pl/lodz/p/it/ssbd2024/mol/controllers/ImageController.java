package pl.lodz.p.it.ssbd2024.mol.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Image;
import pl.lodz.p.it.ssbd2024.mol.dto.ImageResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalImagesResponse;
import pl.lodz.p.it.ssbd2024.mol.mappers.ImagesMapper;
import pl.lodz.p.it.ssbd2024.mol.services.ImageService;

import java.util.Base64;
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
        imageService.store(file, localId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<byte[]> getImage(@PathVariable UUID id) throws NotFoundException {
        Image image = imageService.getImage(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, " inline" ).header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(Base64.getEncoder().encode(image.getImage()));
    }


    @GetMapping(path = "/local/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LocalImagesResponse> getLocalImagesIds(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(ImagesMapper.toLocalImagesResponse(imageService.getImagesByLocalId(id)));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID id) throws NotFoundException {
        imageService.deleteImage(id);
        return ResponseEntity.ok().build();
    }


}
