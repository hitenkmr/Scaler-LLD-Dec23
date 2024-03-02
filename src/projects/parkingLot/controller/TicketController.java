package projects.parkingLot.controller;

import designPatterns.factory.components.button.Button;
import projects.parkingLot.models.Gate;
import projects.parkingLot.models.ParkingSpot;
import projects.parkingLot.models.Vehicle;
import projects.parkingLot.repository.ParkingFloorRepository;
import projects.parkingLot.service.TicketService;
import projects.parkingLot.models.Ticket;

public class TicketController {
    private TicketService ticketService;
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    public Ticket getTicket(Vehicle vehicle, ParkingSpot parkingSpot) {
        return ticketService.getTicket(vehicle, parkingSpot);
    }
}