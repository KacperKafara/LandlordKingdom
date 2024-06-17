package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.mol.dto.LocalImagesResponse;

import java.util.List;
import java.util.UUID;

public class ImagesMapper {
    public static LocalImagesResponse toLocalImagesResponse(List<UUID> imageIds) {
        return new LocalImagesResponse(imageIds);
    }
}
