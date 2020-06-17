package com.anton.railway.booking.controller;

import com.anton.railway.booking.entity.Ticket;
import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.entity.TripSeat;
import com.anton.railway.booking.entity.Wagon;
import com.anton.railway.booking.entity.enums.WagonClass;
import com.anton.railway.booking.service.TicketService;
import com.anton.railway.booking.service.TripSeatService;
import com.anton.railway.booking.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private final TicketService ticketService;
    private final TripService tripService;
    private final TripSeatService tripSeatService;

    public AdminController(TicketService ticketService, TripService tripService, TripSeatService tripSeatService) {
        this.ticketService = ticketService;
        this.tripService = tripService;
        this.tripSeatService = tripSeatService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAdminPage(@RequestParam(defaultValue = "0", name = "page") int page, Model model, HttpSession session) {
        Trip trip = (Trip) session.getAttribute("selectedTrip");

        if (trip != null) {
            Page<Ticket> ticketsPage = ticketService.getTicketsPageForTrip(trip, page);

            model.addAttribute("tickets", ticketsPage.getContent());
            model.addAttribute("numOfPages", ticketsPage.getTotalPages());
            model.addAttribute("page", page);
        }

        return "admin";
    }

    @GetMapping("/chooseTripDate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String chooseTripDate(@RequestParam(name = "date") String date, HttpSession session) {
        session.setAttribute("date", date);
        session.setAttribute("trips", tripService.findScheduledTripsForDate(LocalDate.parse(date)));
        session.setAttribute("selectedTrip", null);

        return "redirect:admin";
    }

    @GetMapping("/chooseTrip")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String ChooseTrip(@RequestParam(name = "trip_id") String tripId, HttpSession session) {
        Trip trip = tripService.findById(Long.valueOf(tripId));
        session.setAttribute("selectedTrip", trip);
        session.setAttribute("wagonClasses", tripService.getWagonClassesForTrip(trip));

        return "redirect:admin";
    }

    @PostMapping("/chooseWagonClass")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String chooseWagonClass(@RequestParam(name = "wagon_class") String wagonClass,
                                   @RequestParam(name = "index") int index, HttpSession session) {
        List<Wagon> wagons = new ArrayList<>();
        Trip trip = (Trip) session.getAttribute("selectedTrip");

        trip.getTrain().getWagons().forEach(wagon -> {
            if (wagon.getWagonType().getWagonClass().toString().equals(wagonClass)) wagons.add(wagon);
        });

        session.setAttribute("selectedWagonClass", WagonClass.valueOf(wagonClass));
        session.setAttribute("wagons", wagons);
        session.setAttribute("index", index);

        return "redirect:admin";
    }

    @PostMapping("/chooseWagon")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String chooseWagon(@RequestParam(name = "wagon_id") Long wagonId, HttpSession session) {
        Trip trip = (Trip) session.getAttribute("selectedTrip");
        List<Wagon> wagons = (List<Wagon>) session.getAttribute("wagons");
        List<TripSeat> seats = new ArrayList<>();

        wagons.forEach(wagon -> {
            if (wagon.getWagonId().equals(wagonId)) {
                seats.addAll(tripSeatService.findWagonFreeSeatsForTrip(wagon, trip));
                session.setAttribute("selectedWagon", wagon);
            }
            ;
        });
        session.setAttribute("seats", seats);

        return "redirect:admin";
    }

    @PostMapping("/chooseSeat")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String chooseSeat(@RequestParam(name = "seat_id") Long seatId,
                             @RequestParam(name = "ticket_id") Long ticketId, HttpSession session) {
        List<TripSeat> seats = (List<TripSeat>) session.getAttribute("seats");
        Ticket ticket = ticketService.findById(ticketId);

        seats.forEach(seat -> {
            if (seat.getTripSeatId().equals(seatId)) ticketService.changeAndSaveTicket(ticket, seat);
        });

        session.setAttribute("index", null);
        session.setAttribute("selectedWagonClass", null);
        session.setAttribute("selectedWagon", null);
        session.setAttribute("wagons", null);
        session.setAttribute("seats", null);

        return "redirect:admin";
    }

    @GetMapping("/cancelSeatChange")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String cancelSeatChange(HttpSession session) {
        session.setAttribute("index", null);
        session.setAttribute("selectedWagonClass", null);
        session.setAttribute("selectedWagon", null);
        session.setAttribute("wagons", null);
        session.setAttribute("seats", null);

        return "redirect:admin";
    }
}
