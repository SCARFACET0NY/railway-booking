package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.dto.TicketDto;
import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.Ticket;
import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.entity.TripSeat;
import com.anton.railway.booking.entity.WagonType;
import com.anton.railway.booking.entity.enums.SeatStatus;
import com.anton.railway.booking.exception.PaymentException;
import com.anton.railway.booking.exception.TicketException;
import com.anton.railway.booking.repository.PaymentRepository;
import com.anton.railway.booking.repository.TicketRepository;
import com.anton.railway.booking.repository.TripSeatRepository;
import com.anton.railway.booking.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    public static final Integer ROWS_PER_PAGE = 2;
    private final PaymentRepository paymentRepository;
    private final TicketRepository ticketRepository;
    private final TripSeatRepository tripSeatRepository;

    public TicketServiceImpl(PaymentRepository paymentRepository, TicketRepository ticketRepository, TripSeatRepository tripSeatRepository) {
        this.paymentRepository = paymentRepository;
        this.ticketRepository = ticketRepository;
        this.tripSeatRepository = tripSeatRepository;
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        ticketRepository.findAll().forEach(tickets::add);
        return tickets;
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new TicketException("Ticket not found"));
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
    @Transactional
    public Ticket changeAndSaveTicket(Ticket ticket, TripSeat tripSeat) {
        WagonType oldWagonType = ticket.getSeat().getSeat().getWagon().getWagonType();
        WagonType newWagonType = tripSeat.getSeat().getWagon().getWagonType();

        if (!oldWagonType.equals(newWagonType)) {
            BigDecimal oldPrice = ticket.getPrice();
            BigDecimal newPrice = ticket.getSeat().getTrip().getRoute().getBasePrice()
                    .multiply(newWagonType.getPriceCoefficient())
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal difference = newPrice.subtract(oldPrice);
            ticket.getPayment().setTotal(ticket.getPayment().getTotal().add(difference));
            ticket.setPrice(newPrice);

            paymentRepository.save(ticket.getPayment());
        }

        ticket.getSeat().setSeatStatus(SeatStatus.FREE);
        tripSeatRepository.save(ticket.getSeat());

        tripSeat.setSeatStatus(SeatStatus.OCCUPIED);
        ticket.setSeat(tripSeat);
        tripSeatRepository.save(tripSeat);

        return ticketRepository.save(ticket);
    }

    @Override
    public Page<Ticket> getTicketsPageForTrip(Trip trip, Integer pageNumber) {
        return ticketRepository.findAllBySeatTrip(trip, PageRequest.of(pageNumber, ROWS_PER_PAGE));
    }
}
