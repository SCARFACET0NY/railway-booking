package com.anton.railway.booking.enitity;

import com.anton.railway.booking.enitity.Seat;
import com.anton.railway.booking.enitity.Trip;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trip_seat")
public class TripSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripSeatId;
    @OneToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    @Column(name = "fare")
    private BigDecimal fare;
}
