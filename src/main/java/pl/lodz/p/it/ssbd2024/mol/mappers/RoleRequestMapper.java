package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.RoleRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.GetRoleRequestResponse;
import pl.lodz.p.it.ssbd2024.util.TimezoneMapper;

import java.time.LocalDateTime;

public class RoleRequestMapper {
    private RoleRequestMapper() {
    }

    public static GetRoleRequestResponse toRoleResponse(RoleRequest roleRequest) {
        return new GetRoleRequestResponse(roleRequest.getId(), roleRequest.getCreatedAt().toString());
    }
}
