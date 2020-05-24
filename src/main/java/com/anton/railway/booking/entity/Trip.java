package com.anton.railway.booking.entity;

import com.anton.railway.booking.entity.enums.TripStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;
    @Column(name = "departure_date")
    private LocalDate departureDate;
    @Column(name = "departure_time")
    private LocalTime departureTime;
    @Column(name = "trip_status")
    @Enumerated(value = EnumType.STRING)
    private TripStatus tripStatus;
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    @OneToOne
    @JoinColumn(name = "train_id")
    private Train train;
}
