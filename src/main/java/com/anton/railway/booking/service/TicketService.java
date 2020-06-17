package com.anton.railway.booking.service;

import com.anton.railway.booking.dto.TicketDto;
import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.Ticket;
import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.entity.TripSeat;
import org.springframework.data.domain.Page;

public interface TicketService extends CrudService<Ticket, Long> {
    Ticket createTicket(TripSeat tripSeat);

    TicketDto createTicketDto(Ticket ticket, TripDto tripDto);

    Ticket changeAndSaveTicket(Ticket ticket, TripSeat tripSeat);

    Page<Ticket> getTicketsPageForTrip(Trip trip, Integer pageNumber);
}
