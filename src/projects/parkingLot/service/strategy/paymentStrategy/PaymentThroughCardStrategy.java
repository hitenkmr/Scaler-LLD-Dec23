package projects.parkingLot.service.strategy.paymentStrategy;

import projects.parkingLot.models.Bill;
import projects.parkingLot.models.Payment;
import projects.parkingLot.models.enums.PaymentMode;
import projects.parkingLot.models.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentThroughCardStrategy implements PaymentStrategy {
    @Override
    public Payment makePayement(Bill bill) {
        Payment payment = new Payment();
        payment.setAmount(bill.getAmount());
        payment.setPaymentMode(PaymentMode.CARD);
        payment.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESSFUL);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setTransactionRefNumber(UUID.randomUUID().toString());
        payment.setBill(bill);
        return payment;
    }
}
