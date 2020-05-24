package com.anton.railway.booking.service;

import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.Trip;

import java.time.LocalDate;
import java.util.List;

public interface TripService extends CrudService<Trip, Long> {
    TripDto findTripDtoById(Long id);

    List<TripDto> findAllScheduledTrips();

    List<TripDto> searchTrips(String departureCity, String arrivalCity);

    List<TripDto> searchTrips(String departureCity, String arrivalCity, LocalDate date);
}