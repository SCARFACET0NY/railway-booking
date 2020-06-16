package com.anton.railway.booking.repository;

import com.anton.railway.booking.entity.Ticket;
import com.anton.railway.booking.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {
    Page<Ticket> findAllBySeatTrip(Trip trip, Pageable pageable);
}
