package projects.parkingLot.service.strategy.billCalculationStrategy;
import projects.parkingLot.models.enums.BillCalculationStrategyType;

import projects.parkingLot.models.Ticket;

public class BillCalculationStrategyFactory {
    public static BillCalculationStrategy getBill(BillCalculationStrategyType type,
                                                  double surge,
                                                  double baseRate,
                                                  double hourlyRate){
       return switch (type){
            case SIMPLE -> new SimpleBillCalculationStrategy();
            case HOURLY -> new HourlyBillCalculationStrategy(surge, baseRate, hourlyRate);
            case SURGE -> new SurgeBillCalculationStrategy(surge, baseRate, hourlyRate);
       };
    }
}
