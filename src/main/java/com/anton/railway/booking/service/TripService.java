package com.anton.railway.booking.service;

import com.anton.railway.booking.enitity.Trip;

import java.util.List;

public interface TripService extends CrudService<Trip, Long> {
    List<Trip> findAllScheduledTrips();
}