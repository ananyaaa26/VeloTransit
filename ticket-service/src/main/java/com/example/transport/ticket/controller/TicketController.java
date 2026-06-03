package com.example.transport.ticket.controller;

import com.example.transport.ticket.model.Ticket;
import com.example.transport.ticket.model.User;
import com.example.transport.ticket.repository.TicketRepository;
import com.example.transport.ticket.service.BookingService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
@CrossOrigin
public class TicketController {

    private final BookingService bookingService;
    private final TicketRepository ticketRepository;

    public TicketController(BookingService bookingService, TicketRepository ticketRepository) {
        this.bookingService = bookingService;
        this.ticketRepository = ticketRepository;
    }

    @PostMapping
    public ResponseEntity<?> bookTicket(@RequestBody BookRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Optional<Ticket> newTicket = bookingService.bookTicket(user.getId(), request.getRouteId(), request.getSeatNo());

        return newTicket.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body(Map.of("error", "Seat not available or route is full.")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Optional<Ticket> ticketOpt = ticketRepository.findById(id);

        if (ticketOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticket = ticketOpt.get();
        // Ensure the user owns this ticket (or holds administrative privileges)
        if (!ticket.getUserId().equals(user.getId()) && !user.getRole().equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(Map.of("error", "Forbidden"));
        }

        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/booked-seats/{routeId}")
    public ResponseEntity<List<String>> getBookedSeats(@PathVariable Long routeId) {
        List<String> bookedSeatNumbers = bookingService.getBookedSeatsForRoute(routeId)
                .stream()
                .map(Ticket::getSeatNo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookedSeatNumbers);
    }

    @Data
    static class BookRequest {
        private Long routeId;
        private String seatNo;
    }
}