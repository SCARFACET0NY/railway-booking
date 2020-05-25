package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.entity.Ticket;
import com.anton.railway.booking.entity.TripSeat;
import com.anton.railway.booking.repository.TicketRepository;
import com.anton.railway.booking.service.TicketService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        ticketRepository.findAll().forEach(tickets::add);
        return tickets;
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void delete(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    @Override
    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket createTicket(TripSeat tripSeat) {
        BigDecimal price = tripSeat.getTrip().getRoute().getBasePrice()
                .multiply(tripSeat.getSeat().getWagon().getWagonType().getPriceCoefficient())
                .setScale(2, RoundingMode.HALF_UP);

        return Ticket.builder().seat(tripSeat).price(price).build();
    }
}
