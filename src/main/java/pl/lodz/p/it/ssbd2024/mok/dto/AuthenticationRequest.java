package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AuthenticationRequest (
        @NotBlank
        @NotNull
        String login,

        @NotBlank
        @NotNull
        @Size(min = 8)
        String password){
}
