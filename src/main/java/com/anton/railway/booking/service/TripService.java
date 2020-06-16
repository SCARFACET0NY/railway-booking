package com.anton.railway.booking.service;

import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.entity.enums.WagonClass;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TripService extends CrudService<Trip, Long> {
    TripDto findTripDtoById(Long id);

    List<TripDto> findAllScheduledTrips();

    List<Trip> findScheduledTripsForDate(LocalDate date);

    List<TripDto> searchTrips(String departureCity, String arrivalCity);

    List<TripDto> searchTrips(String departureCity, String arrivalCity, LocalDate date);

    Set<WagonClass> getWagonClassesForTrip(Trip trip);
}