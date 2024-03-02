package projects.parkingLot.service.strategy.paymentStrategy;

import projects.parkingLot.models.Bill;
import projects.parkingLot.models.Payment;

public interface PaymentStrategy {
    public Payment makePayement(Bill bill);
}
