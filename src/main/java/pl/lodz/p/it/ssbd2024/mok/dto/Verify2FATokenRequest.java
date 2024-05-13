package pl.lodz.p.it.ssbd2024.mok.dto;

public record Verify2FATokenRequest(
        String login,
        String token
) {
}
