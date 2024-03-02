package projects.parkingLot.service;

import projects.parkingLot.models.*;
import projects.parkingLot.repository.ParkingFloorRepository;
import projects.parkingLot.repository.TicketRepository;

import java.time.LocalDateTime;

public class TicketService {
    private TicketRepository ticketRepository;
    private ParkingFloorRepository parkingFloorRepository;

    public TicketService(TicketRepository ticketRepository,
                         ParkingFloorRepository parkingFloorRepository){
        this.ticketRepository = ticketRepository;
        this.parkingFloorRepository = parkingFloorRepository;
    }

    public Ticket getTicket(Vehicle vehicle, ParkingSpot parkingSpot){
        int floorId = parkingFloorRepository.get(parkingSpot.getNumber()/100).getId();
        ParkingFloor floor = parkingFloorRepository.get(floorId);
        Ticket ticket = getNewTicket(vehicle, parkingSpot, floor.getEntryGate());
        ticketRepository.put(ticket);
        return ticketRepository.get(ticket.getId());
    }

    private Ticket getNewTicket(Vehicle vehicle, ParkingSpot parkingSpot, Gate entryGate) {
        Ticket ticket = new Ticket();
        ticket.setEntryTime(LocalDateTime.now());
        ticket.setVehicle(vehicle);
        ticket.setParkingSpot(parkingSpot);
        ticket.setEntryGate(entryGate);
        return ticket;
    }
}


