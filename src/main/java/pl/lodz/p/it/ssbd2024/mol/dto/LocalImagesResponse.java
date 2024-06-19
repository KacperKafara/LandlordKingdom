package pl.lodz.p.it.ssbd2024.mol.dto;

import java.util.List;
import java.util.UUID;

public record LocalImagesResponse(
    List<UUID> imageIds
) {
}
