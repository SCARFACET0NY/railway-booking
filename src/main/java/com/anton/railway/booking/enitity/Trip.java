package com.anton.railway.booking.enitity;

import com.anton.railway.booking.enitity.enums.TripStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
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
