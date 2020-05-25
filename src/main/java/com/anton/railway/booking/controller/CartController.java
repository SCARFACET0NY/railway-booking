package com.anton.railway.booking.controller;

import com.anton.railway.booking.auth.UserPrincipal;
import com.anton.railway.booking.dto.TicketDto;
import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.Ticket;
import com.anton.railway.booking.entity.User;
import com.anton.railway.booking.service.EmailService;
import com.anton.railway.booking.service.PaymentService;
import com.anton.railway.booking.service.TicketService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {
    private final EmailService emailService;
    private final PaymentService paymentService;
    private final TicketService ticketService;

    public CartController(EmailService emailService, PaymentService paymentService, TicketService ticketService) {
        this.emailService = emailService;
        this.paymentService = paymentService;
        this.ticketService = ticketService;
    }

    @GetMapping("/cart")
    public String getCartPage() {
        return "cart";
    }

    @PostMapping("/addTicket")
    public String addTicket(HttpSession session) {
        Map<Long, TicketDto> cart = (Map<Long, TicketDto>) session.getAttribute("cart");
        if (cart == null) cart = new HashMap<>();

        TicketDto ticketDto = ticketService.createTicketDto(
                (Ticket) session.getAttribute("ticket"),
                (TripDto) session.getAttribute("trip"));

        cart.put(ticketDto.getTicket().getSeat().getTripSeatId(), ticketDto);
        session.setAttribute("cart", cart);
        session.setAttribute("total", paymentService.getCartTotal(cart));

        session.removeAttribute("selectedWagonClass");
        session.removeAttribute("wagons");
        session.removeAttribute("selectedWagon");
        session.removeAttribute("seats");
        session.removeAttribute("selectedSeat");
        session.removeAttribute("ticket");

        return "redirect:trip";
    }

    @PostMapping("/removeTicket")
    public String removeTicket(@RequestParam("seat_id") String seatId, HttpSession session) {
        Map<Long, TicketDto> cart = (Map<Long, TicketDto>) session.getAttribute("cart");
        Long id = Long.parseLong(seatId);

        cart.keySet().removeIf(id::equals);

        session.setAttribute("cart", cart);
        session.setAttribute("total", paymentService.getCartTotal(cart));

        return "redirect:cart";
    }

    @PostMapping("/pay")
    public String pay(HttpSession session) {
        User user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Map<Long, TicketDto> cart = (Map<Long, TicketDto>) session.getAttribute("cart");
        BigDecimal total = (BigDecimal) session.getAttribute("total");

        if (!cart.isEmpty()) {
            paymentService.save(paymentService.createPayment(user, cart));
            emailService.sendEmail(user.getEmail(), "Tickets from Railway Booking",
                    emailService.createEmailText(user, cart, total));

            cart.clear();
            session.removeAttribute("cart");
            session.removeAttribute("total");
        }

        return "redirect:";
    }
}
