package com.anton.railway.booking.service;

import com.anton.railway.booking.dto.TicketDto;
import com.anton.railway.booking.entity.User;

import java.math.BigDecimal;
import java.util.Map;

public interface EmailService {
    String createEmailText(User user, Map<Long, TicketDto> cart, BigDecimal total);

    void sendEmail(String to, String subject, String text);
}
