package com.anton.railway.booking.controller;

import com.anton.railway.booking.entity.Ticket;
import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.service.TicketService;
import com.anton.railway.booking.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class AdminController {
    private final TicketService ticketService;
    private final TripService tripService;

    public AdminController(TicketService ticketService, TripService tripService) {
        this.ticketService = ticketService;
        this.tripService = tripService;
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

}
