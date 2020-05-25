package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.dto.TicketDto;
import com.anton.railway.booking.entity.Payment;
import com.anton.railway.booking.repository.PaymentRepository;
import com.anton.railway.booking.repository.TicketRepository;
import com.anton.railway.booking.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final TicketRepository ticketRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, TicketRepository ticketRepository) {
        this.paymentRepository = paymentRepository;
        this.ticketRepository = ticketRepository;
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
}
