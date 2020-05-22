package com.anton.railway.booking.repository;

import com.anton.railway.booking.enitity.Trip;
import com.anton.railway.booking.enitity.enums.TripStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TripRepository extends CrudRepository<Trip, Long> {
    List<Trip> findAllByTripStatus(TripStatus status);
}
