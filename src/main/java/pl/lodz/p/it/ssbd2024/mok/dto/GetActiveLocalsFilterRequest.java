package pl.lodz.p.it.ssbd2024.mok.dto;

public record GetActiveLocalsFilterRequest(
        String city,
        Double minSize,
        Double maxSize
) {
}
