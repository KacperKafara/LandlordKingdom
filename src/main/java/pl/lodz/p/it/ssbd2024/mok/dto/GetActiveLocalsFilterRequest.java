package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record GetActiveLocalsFilterRequest(
        @Size(min = 1, max = 50, message = "City length has to be between 1 and 50")
        String city,
        @Min(value = 0, message = "Minimal size has to be greater or equal to 0")
        @Max(value = 1000000, message = "Max size cannot be greater than 1000000")
        Double minSize,
        @Min(value = 1, message = "Minimal size has to be greater or equal to 1")
        @Max(value = 1000000, message = "Max size cannot be greater than 1000000")
        Double maxSize
) {
}
