package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.mol.dto.RentDetailedTenantResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.RentForOwnerResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.RentForTenantResponse;

import java.util.List;

public class RentMapper {
    private RentMapper() {
    }

    public static RentForTenantResponse rentForTenantResponse(Rent rent) {
        return new RentForTenantResponse(
                rent.getId(),
                LocalMapper.localForTenantResponse(rent.getLocal()),
                OwnerMapper.ownerForTenantResponse(rent.getOwner()),
                rent.getStartDate().toString(),
                rent.getEndDate().toString(),
                rent.getBalance()
        );
    }

    public static RentForOwnerResponse rentForOwnerResponse(Rent rent) {
        return new RentForOwnerResponse(
                rent.getId(),
                LocalMapper.toGetOwnLocalsResponse(rent.getLocal()),
                TenantMapper.tenantForOwnerResponse(rent.getTenant()),
                rent.getStartDate().toString(),
                rent.getEndDate().toString(),
                rent.getBalance()
        );
    }

    public static List<RentForOwnerResponse> rentForOwnerResponseList(List<Rent> rentList) {
        return rentList.stream().map(RentMapper::rentForOwnerResponse).toList();
    }

    public static RentDetailedTenantResponse rentDetailedTenantResponse(Rent rent) {
        return new RentDetailedTenantResponse(
                rent.getId(),
                LocalMapper.localForTenantResponse(rent.getLocal()),
                OwnerMapper.ownerForTenantResponse(rent.getOwner()),
                rent.getStartDate().toString(),
                rent.getEndDate().toString(),
                rent.getBalance(),
                VariableFeeMapper.variableFeeResponseList(rent.getVariableFees()),
                FixedFeeMapper.fixedFeeResponseList(rent.getFixedFees()),
                PaymentMapper.paymentResponseList(rent.getPayments())
        );
    }

    public static List<RentDetailedTenantResponse> rentDetailedTenantResponseList(List<Rent> rentList) {
        return rentList.stream().map(RentMapper::rentDetailedTenantResponse).toList();
    }
}
