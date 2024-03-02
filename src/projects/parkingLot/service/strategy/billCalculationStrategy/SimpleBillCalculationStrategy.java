package projects.parkingLot.service.strategy.billCalculationStrategy;

import projects.parkingLot.models.Bill;
import projects.parkingLot.models.Gate;
import projects.parkingLot.models.Ticket;
import projects.parkingLot.models.enums.BillStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SimpleBillCalculationStrategy implements BillCalculationStrategy{
    //1 sec is 1 Rs.
    @Override
    public Bill generateBill(Ticket ticket, Gate exitGate) {
        LocalDateTime entryTime = ticket.getEntryTime();
        LocalDateTime exitTime = LocalDateTime.now();
        long amount = ChronoUnit.SECONDS.between(exitTime, entryTime);
        Bill bill = new Bill();
        bill.setAmount(amount);
        bill.setTicket(ticket);
        bill.setExitTime(exitTime);
        bill.setId(exitTime.hashCode());
        bill.setStatus(BillStatus.UNPAID);
        bill.setExitGate(exitGate);
        return bill;
    }
}
