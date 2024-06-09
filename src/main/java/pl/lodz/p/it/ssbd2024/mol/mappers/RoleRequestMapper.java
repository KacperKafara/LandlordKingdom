package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.RoleRequest;
import pl.lodz.p.it.ssbd2024.model.Timezone;
import pl.lodz.p.it.ssbd2024.mol.dto.GetRoleRequestResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.RoleRequestResponse;
import pl.lodz.p.it.ssbd2024.util.TimezoneMapper;

public class RoleRequestMapper {
    private RoleRequestMapper() {
    }

    public static GetRoleRequestResponse toRoleResponse(RoleRequest roleRequest, Timezone timezone, String lang) {
        String timezoneStr = timezone == null ? "UTC" : timezone.getName();
        String createdAt = TimezoneMapper.convertUTCToAnotherTimezoneSimple(roleRequest.getCreatedAt(), timezoneStr, lang);

        return new GetRoleRequestResponse(roleRequest.getId(), createdAt);
    }

    public static RoleRequestResponse toRoleRequestResponse(RoleRequest roleRequest) {
        return new RoleRequestResponse(roleRequest.getId(),
                roleRequest.getTenant().getUser().getLogin(),
                roleRequest.getTenant().getUser().getEmail(),
                roleRequest.getTenant().getUser().getFirstName(),
                roleRequest.getTenant().getUser().getLastName());
    }
}
