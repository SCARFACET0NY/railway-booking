package com.anton.railway.booking.dto;

import com.anton.railway.booking.enitity.Route;
import com.anton.railway.booking.enitity.Train;
import com.anton.railway.booking.enitity.enums.TripStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDto {
    private Long tripId;
    private TripStatus tripStatus;
    private Route route;
    private Train train;
    private LocalDateTime departure;
    private LocalDateTime arrival;
}
