package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.mol.dto.RentForTenantResponse;

public class RentMapper {

    public static RentForTenantResponse rentForTenantResponse(Rent rent){
        return new RentForTenantResponse(
                LocalMapper.localForTenantResponse(rent.getLocal()),
                OwnerMapper.ownerForTenantResponse(rent.getOwner()),
                rent.getStartDate().toString(),
                rent.getEndDate().toString(),
                rent.getBalance()
        );
    }
}
