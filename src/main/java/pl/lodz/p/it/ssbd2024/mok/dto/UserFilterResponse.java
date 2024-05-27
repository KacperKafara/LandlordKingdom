package pl.lodz.p.it.ssbd2024.mok.dto;

public record UserFilterResponse(String login,
                                 String email,
                                 String firstName,
                                 String lastName,
                                 boolean blocked,
                                 boolean verified,
                                 String role) {
}
