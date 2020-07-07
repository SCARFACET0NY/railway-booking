package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.converter.TripToTripDto;
import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.entity.enums.TripStatus;
import com.anton.railway.booking.entity.enums.WagonClass;
import com.anton.railway.booking.exception.TripException;
import com.anton.railway.booking.repository.TripRepository;
import com.anton.railway.booking.service.TripService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final TripToTripDto tripToTripDto;

    public TripServiceImpl(TripRepository tripRepository, TripToTripDto tripToTripDto) {
        this.tripRepository = tripRepository;
        this.tripToTripDto = tripToTripDto;
    }

    @Override
    public List<Trip> findAll() {
        List<Trip> trips = new ArrayList<>();
        tripRepository.findAll().forEach(trips::add);

        return trips;
    }

    @Override
    public Trip findById(Long id) {
        return tripRepository.findById(id).orElseThrow(() -> new TripException("Trip not found"));
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
    public TripDto findTripDtoById(Long id) {
        return tripToTripDto.convert(findById(id));
    }

    @Override
    public List<TripDto> findAllScheduledTrips() {
        return tripRepository.findAllByTripStatus(TripStatus.SCHEDULED).stream()
                .map(tripToTripDto::convert).collect(Collectors.toList());
    }

    @Override
    public List<Trip> findScheduledTripsForDate(LocalDate date) {
        return tripRepository.findAllByTripStatusAndAndDepartureDate(TripStatus.SCHEDULED, date);
    }

    @Override
    public List<TripDto> searchTrips(String departureCity, String arrivalCity) {
        return tripRepository.findAllByRouteDepartureStationCityAndRouteArrivalStationCity(departureCity, arrivalCity)
                .stream().map(tripToTripDto::convert).collect(Collectors.toList());
    }

    @Override
    public List<TripDto> searchTrips(String departureCity, String arrivalCity, LocalDate date) {
        return tripRepository.findAllByRouteDepartureStationCityAndRouteArrivalStationCityAndDepartureDate(
                departureCity, arrivalCity, date).stream()
                .map(tripToTripDto::convert).collect(Collectors.toList());
    }

    @Override
    public Set<WagonClass> getWagonClassesForTrip(Trip trip) {
        Set<WagonClass> wagonClasses = new HashSet<>();
        trip.getTrain().getWagons().forEach(wagon -> wagonClasses.add(wagon.getWagonType().getWagonClass()));

        return wagonClasses;
    }
}
