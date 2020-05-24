package com.anton.railway.booking.dto;

import com.anton.railway.booking.entity.Route;
import com.anton.railway.booking.entity.Train;
import com.anton.railway.booking.entity.enums.TripStatus;
import com.anton.railway.booking.entity.enums.WagonClass;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

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
    private BigDecimal minPrice;
    private Set<WagonClass> wagonClasses;
}
