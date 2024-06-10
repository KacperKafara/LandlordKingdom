package pl.lodz.p.it.ssbd2024.mol.mappers;

import org.springframework.data.domain.Page;
import pl.lodz.p.it.ssbd2024.model.VariableFee;
import pl.lodz.p.it.ssbd2024.mol.dto.RentVariableFeesResponse;
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

    public static RentVariableFeesResponse toRentVariableFeesResponse(Page<VariableFee> variableFees) {
        return new RentVariableFeesResponse(
                variableFees.map(VariableFeeMapper::variableFeeResponse).toList(),
                variableFees.getTotalPages()
        );
    }
}
