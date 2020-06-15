package com.anton.railway.booking.service;

import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.TripSeat;
import com.anton.railway.booking.entity.Wagon;

import java.util.List;

public interface TripSeatService extends CrudService<TripSeat, Long> {
    List<TripSeat> findWagonFreeSeatsForTrip(Wagon wagon, TripDto tripDto);
}
