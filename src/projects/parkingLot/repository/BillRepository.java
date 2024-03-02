package projects.parkingLot.repository;

import projects.parkingLot.exception.BillNotFoundException;
import projects.parkingLot.exception.TicketNotFoundException;
import projects.parkingLot.models.Bill;
import projects.parkingLot.models.Ticket;

import java.util.HashMap;
import java.util.Map;

public class BillRepository {

    private Map<Integer, Bill> billMap; // table
    private int idCounter = 0;
    public BillRepository() {
        this.billMap = new HashMap<>();
    }

    public Bill get(int billId){
        Bill bill = billMap.get(billId);
        if(bill == null){
            throw new BillNotFoundException("Bill not found for id : " + billId);
        }
        return bill;
    }

    public void put(Bill bill){
        bill.setId(++idCounter);
        billMap.put(bill.getId(), bill);
        System.out.println("Bill has been added successfully");
    }
}
