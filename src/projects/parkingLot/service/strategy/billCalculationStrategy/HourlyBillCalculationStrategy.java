package projects.parkingLot.service.strategy.billCalculationStrategy;

import projects.parkingLot.models.Bill;
import projects.parkingLot.models.Gate;
import projects.parkingLot.models.Ticket;
import projects.parkingLot.models.enums.BillStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class HourlyBillCalculationStrategy implements  BillCalculationStrategy {
    private final double baseRate;
    private final double hourlyRate;
    public HourlyBillCalculationStrategy(double surge, double baseRate, double hourlyRate){
        this.baseRate = baseRate;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public Bill generateBill(Ticket ticket, Gate exitGate) {
        LocalDateTime entryTime = ticket.getEntryTime();
        LocalDateTime exitTime = LocalDateTime.now();
        long seconds = ChronoUnit.SECONDS.between(exitTime, entryTime);
        int numOfHours = (int) ((seconds/60)/60);
        Bill bill = new Bill();
        bill.setAmount(baseRate + hourlyRate*numOfHours);
        bill.setTicket(ticket);
        bill.setExitTime(exitTime);
        bill.setId(exitTime.hashCode());
        bill.setStatus(BillStatus.UNPAID);
        bill.setExitGate(exitGate);
        return bill;
    }
}
