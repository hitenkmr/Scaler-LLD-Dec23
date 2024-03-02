package projects.parkingLot.service.strategy.spotAllocationStrategy;
import projects.parkingLot.models.enums.SpotAllocationStrategyType;

public class SpotAllocationStrategyFactory {
    //TODO : add more spot allocation strategies, with ENUM and implement here
    public static SpotAllocationStrategy getSpotAllocationStrategy(SpotAllocationStrategyType type){
        return switch (type){
            case LINEAR ->  new LinearSpotAllocationStrategy();
            case NEARESTTOGATE -> new NearestToGateSpotAllocationStrategy();
        };
    }
}
