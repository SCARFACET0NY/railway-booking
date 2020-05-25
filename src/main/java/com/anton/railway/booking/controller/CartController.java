package com.anton.railway.booking.controller;

import com.anton.railway.booking.entity.Ticket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {

    @PostMapping("/addTicket")
    public String addTicket(HttpSession session) {
        Map<Long, Ticket> cart = (Map<Long, Ticket>) session.getAttribute("cart");
        if (cart == null) cart = new HashMap<>();
        Ticket ticket = (Ticket) session.getAttribute("ticket");

        cart.put(ticket.getSeat().getTripSeatId(), ticket);
        session.setAttribute("cart", cart);

        session.setAttribute("selectedWagonClass", null);
        session.setAttribute("wagons", null);
        session.setAttribute("selectedWagon", null);
        session.setAttribute("seats", null);
        session.setAttribute("selectedSeat", null);
        session.setAttribute("ticket", null);

        return "redirect:trip";
    }
}
