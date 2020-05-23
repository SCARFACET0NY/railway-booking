package com.anton.railway.booking.converter;

import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.enitity.Trip;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TripToTripDto implements Converter<Trip, TripDto> {
    @Override
    public TripDto convert(Trip trip) {
        LocalDateTime departure = LocalDateTime.of(trip.getDepartureDate(), trip.getDepartureTime());
        LocalDateTime arrival = departure.plusMinutes(trip.getRoute().getDurationInMinutes());

        return TripDto.builder()
                .tripId(trip.getTripId())
                .tripStatus(trip.getTripStatus())
                .route(trip.getRoute())
                .train(trip.getTrain())
                .departure(departure)
                .arrival(arrival).build();
    }
}
