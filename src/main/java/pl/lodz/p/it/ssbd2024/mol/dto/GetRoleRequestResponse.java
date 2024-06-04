package pl.lodz.p.it.ssbd2024.mol.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetRoleRequestResponse(UUID id, LocalDateTime createdAt) {
}
