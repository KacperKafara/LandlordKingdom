package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.VariableFee;
import pl.lodz.p.it.ssbd2024.mol.dto.VariableFeeResponse;

import java.util.List;

public class VariableFeeMapper {
    private VariableFeeMapper() {
    }

    public static VariableFeeResponse variableFeeResponse(VariableFee variableFee) {
        return new VariableFeeResponse(
                variableFee.getDate().toString(), variableFee.getAmount()
        );
    }

    public static List<VariableFeeResponse> variableFeeResponseList(List<VariableFee> variableFeeList) {
        return variableFeeList.stream().map(VariableFeeMapper::variableFeeResponse).toList();
    }
}
