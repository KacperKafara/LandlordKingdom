package pl.lodz.p.it.ssbd2024.mol.mappers;

import org.springframework.data.domain.Page;
import pl.lodz.p.it.ssbd2024.model.Payment;
import pl.lodz.p.it.ssbd2024.mol.dto.PaymentResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.RentPaymentsResponse;

public class PaymentMapper {

    public static PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getAmount(),
                payment.getDate().toString()
        );
    }

    public static RentPaymentsResponse toRentPaymentsResponse(Page<Payment> rentPayments) {
        return new RentPaymentsResponse(
                rentPayments.map(PaymentMapper::toPaymentResponse).getContent(),
                rentPayments.getTotalPages()
        );
    }
}
