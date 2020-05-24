package com.anton.railway.booking.converter;

import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.entity.enums.WagonClass;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class TripToTripDto implements Converter<Trip, TripDto> {
    @Override
    public TripDto convert(Trip trip) {
        LocalDateTime departure = LocalDateTime.of(trip.getDepartureDate(), trip.getDepartureTime());
        LocalDateTime arrival = departure.plusMinutes(trip.getRoute().getDurationInMinutes());

        Set<WagonClass> wagonClasses = new HashSet<>();
        trip.getTrain().getWagons().forEach(wagon -> wagonClasses.add(wagon.getWagonType().getWagonClass()));

        return TripDto.builder()
                .tripId(trip.getTripId())
                .tripStatus(trip.getTripStatus())
                .route(trip.getRoute())
                .train(trip.getTrain())
                .departure(departure)
                .arrival(arrival)
                .minPrice(trip.getRoute().getBasePrice().setScale(2, RoundingMode.HALF_UP))
                .wagonClasses(wagonClasses).build();
    }
}
