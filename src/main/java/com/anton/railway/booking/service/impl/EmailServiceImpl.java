package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.dto.TicketDto;
import com.anton.railway.booking.entity.User;
import com.anton.railway.booking.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String createEmailText(User user, Map<Long, TicketDto> cart, BigDecimal total) {
        String cardNumber = String.valueOf(user.getCardNumber());
        String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);

        StringBuilder message = new StringBuilder("<h2>Tickets</h2>");
        for (TicketDto ticketDto : cart.values()) {
            message.append("<p>Train Number: " + ticketDto.getTicket().getSeat().getTrip().getTrain().getTrainNumber());
            message.append(", From: " + ticketDto.getTicket().getSeat().getTrip().getRoute().getDepartureStation().getCity());
            message.append(", To: " + ticketDto.getTicket().getSeat().getTrip().getRoute().getArrivalStation().getCity());
            message.append(", Departure: " + ticketDto.getDeparture());
            message.append(", Arrival: " + ticketDto.getArrival());
            message.append(", Wagon Number: " + ticketDto.getTicket().getSeat().getSeat().getWagon().getWagonNumber());
            message.append(", Seat Number: " + ticketDto.getTicket().getSeat().getSeat().getSeatNumber());
            message.append(", Price: " + ticketDto.getTicket().getPrice() + "</p>");
        }

        message.append("<br/>");
        message.append("<p>Buyer: " + user.getFirstName() + " " + user.getLastName() + ", ");
        message.append("Card: ******" + lastFourDigits + ", Total: " + total + "</p>");

        return message.toString();
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }
}
