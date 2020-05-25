package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.dto.TicketDto;
import com.anton.railway.booking.entity.Payment;
import com.anton.railway.booking.entity.Ticket;
import com.anton.railway.booking.entity.TripSeat;
import com.anton.railway.booking.entity.User;
import com.anton.railway.booking.entity.enums.SeatStatus;
import com.anton.railway.booking.repository.PaymentRepository;
import com.anton.railway.booking.repository.TicketRepository;
import com.anton.railway.booking.repository.TripSeatRepository;
import com.anton.railway.booking.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final TicketRepository ticketRepository;
    private final TripSeatRepository tripSeatRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, TicketRepository ticketRepository, TripSeatRepository tripSeatRepository) {
        this.paymentRepository = paymentRepository;
        this.ticketRepository = ticketRepository;
        this.tripSeatRepository = tripSeatRepository;
    }

    @Override
    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        paymentRepository.findAll().forEach(payments::add);

        return payments;
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Payment save(Payment payment) {
        payment.getTickets().forEach(ticket -> {
            ticket.setPayment(payment);
            ticketRepository.save(ticket);

            TripSeat tripSeat = tripSeatRepository.findById(ticket.getSeat().getTripSeatId()).orElse(null);
            tripSeat.setSeatStatus(SeatStatus.OCCUPIED);
            tripSeatRepository.save(tripSeat);
        });

        return paymentRepository.save(payment);
    }

    @Override
    public void delete(Payment payment) {
        paymentRepository.delete(payment);
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public BigDecimal getCartTotal(Map<Long, TicketDto> cart) {
        return cart.values().stream()
                .map(ticketDto -> ticketDto.getTicket().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Payment createPayment(User user, Map<Long, TicketDto> cart) {
        List<Ticket> tickets = new ArrayList<>();
        cart.values().forEach(ticketDto -> tickets.add(ticketDto.getTicket()));

        return Payment.builder()
                .paymentDate(LocalDateTime.now())
                .total(getCartTotal(cart))
                .tickets(tickets)
                .user(user).build();
    }
}
