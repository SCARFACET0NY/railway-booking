package com.anton.railway.booking.service;

import com.anton.railway.booking.dto.TicketDto;
import com.anton.railway.booking.entity.Payment;
import com.anton.railway.booking.entity.User;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentService extends CrudService<Payment, Long> {
    BigDecimal getCartTotal(Map<Long, TicketDto> cart);

    Payment createPayment(User user, Map<Long, TicketDto> cart);

    Payment savePaymentWithTickets(Payment payment);
}
