package com.anton.railway.booking.service;

import com.anton.railway.booking.enitity.Trip;

import java.time.LocalDate;
import java.util.List;

public interface TripService extends CrudService<Trip, Long> {
    List<Trip> findAllScheduledTrips();

    List<Trip> searchTrips(String departureCity, String arrivalCity);

    List<Trip> searchTrips(String departureCity, String arrivalCity, LocalDate date);
}