package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.dto.TicketDto;
import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.Ticket;
import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.entity.TripSeat;
import com.anton.railway.booking.repository.TicketRepository;
import com.anton.railway.booking.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    public static final Integer ROWS_PER_PAGE = 2;
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

    @Override
    public TicketDto createTicketDto(Ticket ticket, TripDto tripDto) {
        return TicketDto.builder()
                .ticket(ticket).departure(tripDto.getDeparture()).arrival(tripDto.getArrival()).build();
    }

    @Override
    public Page<Ticket> getTicketsPageForTrip(Trip trip, Integer pageNumber) {
        return ticketRepository.findAllBySeatTrip(trip, PageRequest.of(pageNumber, ROWS_PER_PAGE));
    }
}
