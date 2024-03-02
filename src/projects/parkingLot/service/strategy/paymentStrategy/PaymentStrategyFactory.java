package projects.parkingLot.service.strategy.paymentStrategy;

import projects.parkingLot.models.Payment;
import projects.parkingLot.models.enums.PaymentMode;

public class PaymentStrategyFactory {
    public static PaymentStrategy getPaymentStrategy(PaymentMode mode){
        return switch (mode) {
            case CARD -> new PaymentThroughCardStrategy();
            case UPI -> new PaymentThroughUPIStrategy();
            case CASH -> new PaymentThroughCashStrategy();
        };
    }
}
