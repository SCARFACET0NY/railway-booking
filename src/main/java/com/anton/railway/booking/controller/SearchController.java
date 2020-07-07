package com.anton.railway.booking.controller;

import com.anton.railway.booking.service.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class SearchController {
    private final TripService tripService;

    public SearchController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/")
    public String getSearchPage(Model model, HttpSession session) {
        if (session.getAttribute("mailSuccess") != null) {
            model.addAttribute("mailSuccess", session.getAttribute("mailSuccess"));
            session.setAttribute("mailSuccess", null);
        }
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("departure") String departure, @RequestParam("arrival") String arrival,
                         @RequestParam(required = false, name = "date") String date, Model model) {
        if (date.isEmpty()) {
            model.addAttribute("trips", tripService.searchTrips(departure, arrival));
        } else {
            model.addAttribute("trips", tripService.searchTrips(departure, arrival, LocalDate.parse(date)));
        }

        return "index";
    }
}
