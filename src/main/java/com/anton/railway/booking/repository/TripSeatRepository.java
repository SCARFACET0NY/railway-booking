package com.anton.railway.booking.repository;

import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.entity.TripSeat;
import com.anton.railway.booking.entity.Wagon;
import com.anton.railway.booking.entity.enums.SeatStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TripSeatRepository extends CrudRepository<TripSeat, Long> {
    List<TripSeat> findAllByTripAndSeatWagonAndSeatStatus(Trip trip, Wagon wagon, SeatStatus seatStatus);
}
