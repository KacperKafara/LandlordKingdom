package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.Payment;
import pl.lodz.p.it.ssbd2024.mol.dto.PaymentResponse;

import java.util.List;

public class PaymentMapper {
    private PaymentMapper() {
    }

    public static PaymentResponse paymentResponse(Payment payment) {
        return new PaymentResponse(payment.getDate().toString(), payment.getAmount());
    }

    public static List<PaymentResponse> paymentResponseList(List<Payment> paymentList) {
        return paymentList.stream().map(PaymentMapper::paymentResponse).toList();
    }
}
