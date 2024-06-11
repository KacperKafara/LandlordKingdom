package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.Owner;
import pl.lodz.p.it.ssbd2024.mol.dto.OwnerForAdminResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.OwnerForTenantResponse;

import java.util.UUID;

public class OwnerMapper {

    public static OwnerForTenantResponse ownerForTenantResponse(Owner owner){
        return new OwnerForTenantResponse(
                owner.getUser().getFirstName(),
                owner.getUser().getLastName(),
                owner.getUser().getLogin(),
                owner.getUser().getEmail()
        );
    }

    public static OwnerForAdminResponse toOwnerForAdminResponse(Owner owner) {
        if(owner == null){
            return new OwnerForAdminResponse(
                    null,"","","",""
            );
        }

        return new OwnerForAdminResponse(
                owner.getUser().getId(),
                owner.getUser().getFirstName(),
                owner.getUser().getLastName(),
                owner.getUser().getLogin(),
                owner.getUser().getEmail()
        );
    }
}
