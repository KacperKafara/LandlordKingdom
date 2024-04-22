package pl.lodz.p.it.ssbd2024.mok.dto;

public record UserResponse(
        String firstName,
        String lastName,
        String email,
        String login,
        String language
) {
}
