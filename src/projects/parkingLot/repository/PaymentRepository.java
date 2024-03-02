package projects.parkingLot.repository;

import projects.parkingLot.exception.BillNotFoundException;
import projects.parkingLot.exception.PaymentNotFoundException;
import projects.parkingLot.models.Bill;
import projects.parkingLot.models.Payment;

import java.util.HashMap;
import java.util.Map;

public class PaymentRepository {

    private Map<Integer, Payment> paymentMap; // table
    private static int idCounter = 0;
    public PaymentRepository() {
        this.paymentMap = new HashMap<>();
    }

    public Payment get(int paymentId){
        Payment payment = paymentMap.get(paymentId);
        if(payment == null){
            throw new PaymentNotFoundException("Payment not found for id : " + paymentId);
        }
        return payment;
    }

    public void put(Payment payment){
        payment.setId(++idCounter);
        paymentMap.put(payment.getId(), payment);
        System.out.println("Payment has been added successfully");
    }
}
