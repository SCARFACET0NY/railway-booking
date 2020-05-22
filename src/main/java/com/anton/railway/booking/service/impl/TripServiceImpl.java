package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.enitity.Trip;
import com.anton.railway.booking.enitity.enums.TripStatus;
import com.anton.railway.booking.repository.TripRepository;
import com.anton.railway.booking.service.TripService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public List<Trip> findAll() {
        List<Trip> trips = new ArrayList<>();
        tripRepository.findAll().forEach(trips::add);

        return trips;
    }

    @Override
    public Trip findById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    @Override
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public void delete(Trip trip) {
        tripRepository.delete(trip);
    }

    @Override
    public void deleteById(Long id) {
        tripRepository.deleteById(id);
    }

    @Override
    public List<Trip> findAllScheduledTrips() {
        return tripRepository.findAllByTripStatus(TripStatus.SCHEDULED);
    }
}
