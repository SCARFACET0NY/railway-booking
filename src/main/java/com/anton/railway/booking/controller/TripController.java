package com.anton.railway.booking.controller;

import com.anton.railway.booking.service.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/")
    public String getAllTrips(Model model) {
        model.addAttribute("trips", tripService.findAll());

        return "index";
    }
}
