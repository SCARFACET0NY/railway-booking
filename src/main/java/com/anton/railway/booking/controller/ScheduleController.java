package com.anton.railway.booking.controller;

import com.anton.railway.booking.service.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {
    private final TripService tripService;

    public ScheduleController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/schedule")
    public String getSchedule(Model model) {
        model.addAttribute("trips", tripService.findAllScheduledTrips());

        return "schedule";
    }
}
