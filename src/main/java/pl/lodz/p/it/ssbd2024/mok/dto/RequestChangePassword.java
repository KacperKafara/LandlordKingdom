package pl.lodz.p.it.ssbd2024.mok.dto;

import java.util.UUID;

public record RequestChangePassword(String password, String token) {
}
