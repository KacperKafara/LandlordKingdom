package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.RoleRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.GetRoleRequestResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.RoleRequestResponse;

public class RoleRequestMapper {
    private RoleRequestMapper() {
    }

    public static GetRoleRequestResponse toGetRoleResponse(RoleRequest roleRequest) {
        return new GetRoleRequestResponse(roleRequest.getId(), roleRequest.getCreatedAt().toString());
    }

    public static RoleRequestResponse toRoleRequestResponse(RoleRequest roleRequest) {
        return new RoleRequestResponse(roleRequest.getId(),
                roleRequest.getTenant().getUser().getLogin(),
                roleRequest.getTenant().getUser().getEmail(),
                roleRequest.getTenant().getUser().getFirstName(),
                roleRequest.getTenant().getUser().getLastName());
    }
}
