package com.anton.railway.booking.converter;

import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.enitity.Trip;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TripDtoToTrip implements Converter<TripDto, Trip> {
    @Override
    public Trip convert(TripDto tripDto) {
        return Trip.builder()
                .tripId(tripDto.getTripId())
                .departureDate(tripDto.getDeparture().toLocalDate())
                .departureTime(tripDto.getDeparture().toLocalTime())
                .tripStatus(tripDto.getTripStatus())
                .route(tripDto.getRoute())
                .train(tripDto.getTrain()).build();
    }
}
