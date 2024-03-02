package projects.parkingLot;

import projects.parkingLot.controller.InitController;
import projects.parkingLot.controller.TicketController;
import projects.parkingLot.models.*;
import projects.parkingLot.models.enums.*;
import projects.parkingLot.repository.*;
import projects.parkingLot.service.InitialisationService;
import projects.parkingLot.service.TicketService;
import projects.parkingLot.service.strategy.billCalculationStrategy.SurgeBillCalculationStrategy;
import projects.parkingLot.service.strategy.spotAllocationStrategy.SpotAllocationStrategyFactory;

import java.util.*;

public class ParkingLotMain {

    public static void main(String[] args) throws InterruptedException {
        ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
        ParkingSpotRepository parkingSpotRepository = new ParkingSpotRepository();
        ParkingFloorRepository parkingFloorRepository = new ParkingFloorRepository();
        GateRepository gateRepository = new GateRepository();
        TicketRepository ticketRepository = new TicketRepository();
        PaymentRepository paymentRepository = new PaymentRepository();
        BillRepository billRepository = new BillRepository();

        InitialisationService initialisationService = new InitialisationService(
                gateRepository,
                parkingLotRepository,
                parkingFloorRepository,
                parkingSpotRepository,
                ticketRepository,
                billRepository,
                paymentRepository
        );

        InitController initController = new InitController(initialisationService);

        ParkingLot parkingLot = initController.init();

        Scanner userInput = new Scanner(System.in);
        if (parkingLot.getParkingLotStatus().equals(ParkingLotStatus.FULL) ||
                parkingLot.getParkingLotStatus().equals(ParkingLotStatus.CLOSED)) {
            System.out.println("We are sorry our " + parkingLot.getName() + " parking lot is:" + parkingLot.getParkingLotStatus());
        } else {
            Vehicle vehicle = new Vehicle();
            System.out.println("Please Enter the Vehicle Number:");
            String vehicleNumber = userInput.next();
            System.out.println("Please Enter the Vehicle Color:");
            String vehicleColor = userInput.next();
            System.out.println("Please Enter the Vehicle Type:");
            String vehicle_type = userInput.next();
            vehicle.setVehicleNumber(vehicleNumber);
            vehicle.setColor(vehicleColor);
            vehicle.setVehicleType(getVehicleType(vehicle_type));

            parkingLot.setSpotAllocationStrategy(SpotAllocationStrategyFactory.getSpotAllocationStrategy(SpotAllocationStrategyType.LINEAR));
            ParkingSpot parkingSpot = parkingLot.getSpotAllocationStrategy().getSpotForVehicle(parkingLot, vehicle);
            TicketController ticketController = new TicketController(new TicketService(ticketRepository, parkingFloorRepository));
            Ticket ticket = ticketController.getTicket(vehicle, parkingSpot);
            System.out.println("Ticket has been generated");
            System.out.println("Ticket id is: " + ticket.getId());

            // wait for these many seconds
            System.out.println("Enter the number of seconds you want to park your car for");
            long seconds = userInput.nextInt();
            Thread.sleep(seconds*1000);

            parkingLot.setBillCalculationStrategy(new SurgeBillCalculationStrategy(getSurge(parkingLot), 100, 50));

            int floorNumber = parkingFloorRepository.get(parkingSpot.getNumber()/100).getFloorNumber();
            Bill bill =  parkingLot.getBillCalculationStrategy().generateBill(ticket, gateRepository.get(floorNumber*1000 + 2));
            System.out.println("Bill has been generated");
            System.out.println("Amount is: " + bill.getAmount());
        }
        System.out.println("END");
    }

    private static  double getSurge(ParkingLot parkingLot) {
        double capacity = parkingLot.getCapacity();
        int filledSpots = 0;
        for (ParkingFloor floor: parkingLot.getFloors()){
            for (ParkingSpot spot: floor.getParkingSpots()) {
                if(spot.getParkingSpotStatus() == ParkingSpotStatus.OCCUPIED) {
                    filledSpots++;
                }
            }
        }
        double filledPercentage = (filledSpots/capacity)*100;
        if (filledPercentage >= 50 && filledPercentage <= 75) return 0.5;
        else if(filledPercentage > 75 && filledPercentage <= 90) return 0.75;
        else return 0.9;
    }

    private static VehicleType getVehicleType(String vehicle_type){
        if(vehicle_type.equalsIgnoreCase(String.valueOf(VehicleType.EV))) return VehicleType.EV;
        if(vehicle_type.equalsIgnoreCase(String.valueOf(VehicleType.LUXE))) return VehicleType.LUXE;
        if(vehicle_type.equalsIgnoreCase(String.valueOf(VehicleType.FOUR_WHEELER))) return VehicleType.FOUR_WHEELER;
        return VehicleType.TWO_WHEELER;
    }
}