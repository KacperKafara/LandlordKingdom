package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;

public record GetActiveLocalsFilterRequest(
        @Size(min = 1, max = 50, message = "City length has to be between 1 and 50")
        String city,

        Double minSize,

        Double maxSize
) {
}
