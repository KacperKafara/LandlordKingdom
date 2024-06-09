package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.FixedFee;
import pl.lodz.p.it.ssbd2024.mol.dto.FixedFeeResponse;

import java.util.List;

public class FixedFeeMapper {
    private FixedFeeMapper() {
    }

    public static FixedFeeResponse fixedFeeResponse(FixedFee fixedFee) {
        return new FixedFeeResponse(fixedFee.getDate().toString(), fixedFee.getMarginFee(), fixedFee.getRentalFee());
    }

    public static List<FixedFeeResponse> fixedFeeResponseList(List<FixedFee> fixedFeeList) {
        return fixedFeeList.stream().map(FixedFeeMapper::fixedFeeResponse).toList();
    }
}
