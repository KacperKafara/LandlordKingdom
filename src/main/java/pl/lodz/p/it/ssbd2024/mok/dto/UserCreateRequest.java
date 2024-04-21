package pl.lodz.p.it.ssbd2024.mok.dto;

public record UserCreateRequest (String login, String email, String firstName, String lastName, String password) {}
