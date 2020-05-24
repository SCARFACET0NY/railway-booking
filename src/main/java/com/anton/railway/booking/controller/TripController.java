package com.anton.railway.booking.controller;

import com.anton.railway.booking.converter.TripDtoToTrip;
import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.TripSeat;
import com.anton.railway.booking.entity.Wagon;
import com.anton.railway.booking.entity.enums.WagonClass;
import com.anton.railway.booking.repository.TripSeatRepository;
import com.anton.railway.booking.service.TripSeatService;
import com.anton.railway.booking.service.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TripController {
    private final TripService tripService;
    private final TripSeatService tripSeatService;

    public TripController(TripService tripService, TripSeatRepository tripSeatRepository, TripDtoToTrip tripDtoToTrip, TripSeatService tripSeatService) {
        this.tripService = tripService;
        this.tripSeatService = tripSeatService;
    }

    @GetMapping("/trip")
    public String getTrip(Model model, HttpSession session) {
        model.addAttribute("trip", session.getAttribute("trip"));
        model.addAttribute("selectedWagonClass", session.getAttribute("selectedWagonClass"));
        model.addAttribute("wagons", session.getAttribute("wagons"));
        model.addAttribute("selectedWagon", session.getAttribute("selectedWagon"));
        model.addAttribute("seats", session.getAttribute("seats"));
        model.addAttribute("selectedSeat", session.getAttribute("selectedSeat"));

        return "trip";
    }

    @GetMapping("/setTrip")
    public String setTrip(@RequestParam(required = false, name = "trip_id") String tripId, HttpSession session) {
        session.setAttribute("trip", tripService.findTripDtoById(Long.parseLong(tripId)));
        session.setAttribute("selectedWagonClass", null);
        session.setAttribute("wagons", null);
        session.setAttribute("wagon", null);
        session.setAttribute("seats", null);
        session.setAttribute("seat", null);

        return "redirect:trip";
    }

    @GetMapping("/setWagonClass")
    public String setWagonClass(@RequestParam("wagon_class") String wagonClass, HttpSession session) {
        List<Wagon> wagons = new ArrayList<>();
        TripDto trip = (TripDto) session.getAttribute("trip");

        trip.getTrain().getWagons().forEach(wagon -> {
            if (wagon.getWagonType().getWagonClass().toString().equals(wagonClass)) wagons.add(wagon);
        });

        session.setAttribute("selectedWagonClass", WagonClass.valueOf(wagonClass));
        session.setAttribute("wagons", wagons);

        return "redirect:trip";
    }

    @GetMapping("/setWagon")
    public String setWagon(@RequestParam("wagon_id") String wagonId, HttpSession session) {
        Long id = Long.parseLong(wagonId);
        TripDto tripDto = (TripDto) session.getAttribute("trip");
        List<Wagon> wagons = (List<Wagon>) session.getAttribute("wagons");
        List<TripSeat> seats = new ArrayList<>();

        wagons.forEach(wagon -> {
            if (wagon.getWagonId().equals(id)) {
                seats.addAll(tripSeatService.findWagonFreeSeatForTrip(wagon, tripDto));
                session.setAttribute("selectedWagon", wagon);
            };
        });

        session.setAttribute("seats", seats);

        return "redirect:trip";
    }

    @GetMapping("/setSeat")
    public String setSeat(@RequestParam("seat_id") String seatId, HttpSession session) {
        Long id = Long.parseLong(seatId);
        List<TripSeat> seats = (List<TripSeat>) session.getAttribute("seats");

        seats.forEach(seat -> {
            if (seat.getTripSeatId().equals(id)) session.setAttribute("selectedSeat", seat);
        });

        return "redirect:trip";
    }
}
