package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.mol.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class LocalMapper {
    public static GetOwnLocalsResponse toGetOwnLocalsResponse(Local local) {
        return new GetOwnLocalsResponse(
                local.getId(),
                local.getName(),
                local.getDescription(),
                local.getState().toString(),
                local.getSize(),
                local.getMarginFee(),
                local.getRentalFee(),
                AddressMapper.toAddressResponse(local.getAddress())
        );
    }

    public static List<GetOwnLocalsResponse> toGetOwnLocalsResponseList(List<Local> locals) {
        return locals.stream().map(LocalMapper::toGetOwnLocalsResponse).collect(Collectors.toList());
    }

    public static GetAllLocalsResponse toGetAllLocalsResponse(Local local) {
        return new GetAllLocalsResponse(
                local.getId(),
                local.getOwner().getUser().getLogin(),
                local.getName(),
                local.getDescription(),
                local.getState().toString(),
                local.getSize(),
                local.getMarginFee(),
                local.getRentalFee(),
                AddressMapper.toAddressResponse(local.getAddress())
        );
    }

    public static List<GetAllLocalsResponse> toGetAllLocalsResponseList(List<Local> locals) {
        return locals.stream().map(LocalMapper::toGetAllLocalsResponse).collect(Collectors.toList());
    }

    public static LocalForAdministratorResponse toLocalForAdministratorResponse(Local local) {
        return new LocalForAdministratorResponse(
                local.getId(),
                local.getOwner().getUser().getLogin(),
                local.getName(),
                local.getDescription(),
                local.getState().toString(),
                local.getSize(),
                local.getMarginFee(),
                local.getRentalFee(),
                AddressMapper.toAddressResponse(local.getAddress())
        );
    }

    public static LocalForTenantResponse localForTenantResponse(Local local) {
        return new LocalForTenantResponse(
                local.getName(),
                local.getSize(),
                AddressMapper.toAddressResponse(local.getAddress()),
                local.getMarginFee(),
                local.getRentalFee()
        );
    }

    public static LocalDetailsForAdminResponse toLocalDetailsForAdminResponse(Local local) {
        return new LocalDetailsForAdminResponse(
                local.getName(),
                local.getSize(),
                local.getDescription(),
                local.getState().toString(),
                OwnerMapper.toOwnerForAdminResponse(local.getOwner()),
                AddressMapper.toAddressResponse(local.getAddress()),
                local.getMarginFee(),
                local.getRentalFee()
        );
    }

    public static OwnLocalDetailsResponse toOwnLocalDetailsResponse(Local local) {
        return new OwnLocalDetailsResponse(
                local.getName(),
                local.getSize(),
                local.getDescription(),
                local.getState().toString(),
                AddressMapper.toAddressResponse(local.getAddress()),
                local.getMarginFee(),
                local.getRentalFee()
        );
    }

    public static LocalPublicResponse toLocalPublicResponse(Local local) {
        return new LocalPublicResponse(
                local.getName(),
                local.getDescription(),
                local.getSize(),
                local.getOwner().getUser().getFirstName(),
                local.getAddress().getCity(),
                local.getMarginFee(),
                local.getRentalFee()
        );
    }

    public static GetActiveLocalsResponse toGetAllActiveLocalsResponse(Local local) {
        return new GetActiveLocalsResponse(
                local.getId(),
                local.getName(),
                local.getDescription(),
                local.getSize(),
                local.getMarginFee(),
                local.getRentalFee(),
                AddressMapper.toAddressResponse(local.getAddress())
        );
    }

    public static List<GetActiveLocalsResponse> toGetAllActiveLocalsResponseList(List<Local> locals) {
        return locals.stream().map(LocalMapper::toGetAllActiveLocalsResponse).collect(Collectors.toList());
    }
}
