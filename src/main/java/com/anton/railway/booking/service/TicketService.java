package com.anton.railway.booking.service;

import com.anton.railway.booking.dto.TicketDto;
import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.Ticket;
import com.anton.railway.booking.entity.TripSeat;

public interface TicketService extends CrudService<Ticket, Long> {
    Ticket createTicket(TripSeat tripSeat);

    TicketDto createTicketDto(Ticket ticket, TripDto tripDto);
}
