package projects.parkingLot.service.strategy.billCalculationStrategy;

import projects.parkingLot.models.Bill;
import projects.parkingLot.models.Gate;
import projects.parkingLot.models.Ticket;
import projects.parkingLot.models.enums.BillStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SurgeBillCalculationStrategy implements BillCalculationStrategy {
    private final double baseRate;
    private final double hourlyRate;
    private final double surge;

    public SurgeBillCalculationStrategy(double surge,
                                     double baseRate,
                                     double hourlyRate){
        this.surge = surge;
        this.baseRate = baseRate;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public Bill generateBill(Ticket ticket, Gate exitGate) {
        LocalDateTime entryTime = ticket.getEntryTime();
        LocalDateTime exitTime = LocalDateTime.now();
        long seconds = ChronoUnit.SECONDS.between(entryTime, exitTime);
        double numOfHours = seconds;
//        double numOfHours = ((double) (seconds / 60) /60);
        Bill bill = new Bill();
        bill.setAmount(calculateAmount(numOfHours));
        bill.setTicket(ticket);
        bill.setExitTime(exitTime);
        bill.setId(exitTime.hashCode());
        bill.setStatus(BillStatus.UNPAID);
        bill.setExitGate(exitGate);
        return bill;
    }

    private double calculateAmount(double numOfHours){
        double amount = baseRate + hourlyRate*numOfHours;
        amount = amount + surge*amount;
        return amount;
    }
}
