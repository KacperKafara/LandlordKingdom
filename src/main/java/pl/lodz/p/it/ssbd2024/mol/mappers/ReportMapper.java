package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.mol.dto.LocalReport;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReportResponse;

public class ReportMapper {
    private ReportMapper() {
    }

    public static LocalReportResponse toLocalReportResponse(LocalReport localReport) {
        return new LocalReportResponse(
                localReport.local().getId(),
                localReport.local().getName(),
                PaymentMapper.paymentResponseList(localReport.payments()),
                VariableFeeMapper.variableFeeResponseList(localReport.variableFees()),
                FixedFeeMapper.fixedFeeResponseList(localReport.fixedFees()),
                localReport.rentCount(),
                localReport.longestRentDays(),
                localReport.shortestRentDays()
        );
    }
}
