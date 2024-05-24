package pl.lodz.p.it.ssbd2024.mok.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token,
        String refreshToken,
        String theme
) {
}
