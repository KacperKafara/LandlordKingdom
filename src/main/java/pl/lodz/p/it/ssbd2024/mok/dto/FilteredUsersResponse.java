package pl.lodz.p.it.ssbd2024.mok.dto;

import java.util.List;

public record FilteredUsersResponse(
        List<UserResponse> users,
        long totalPages
) {
}
