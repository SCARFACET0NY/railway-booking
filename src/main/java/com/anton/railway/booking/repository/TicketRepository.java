package com.anton.railway.booking.repository;

import com.anton.railway.booking.entity.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
