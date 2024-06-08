package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.mol.dto.TenantForOwnerResponse;

public class TenantMapper {
    public static TenantForOwnerResponse tenantForOwnerResponse(Tenant tenant){
        return new TenantForOwnerResponse(
                tenant.getUser().getLogin(),
                tenant.getUser().getFirstName(),
                tenant.getUser().getLastName(),
                tenant.getUser().getEmail()
        );
    }
}
