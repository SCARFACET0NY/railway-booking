package com.anton.railway.booking.repository;

import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.entity.enums.TripStatus;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TripRepository extends CrudRepository<Trip, Long> {
    List<Trip> findAllByTripStatus(TripStatus status);

    List<Trip> findAllByTripStatusAndAndDepartureDate(TripStatus status, LocalDate date);

    List<Trip> findAllByRouteDepartureStationCityAndRouteArrivalStationCity(String departureCity, String arrivalCity);

    List<Trip> findAllByRouteDepartureStationCityAndRouteArrivalStationCityAndDepartureDate(
            String departureCity, String arrivalCity, LocalDate date);
}
