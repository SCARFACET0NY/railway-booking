package com.anton.railway.booking.enitity;

import com.anton.railway.booking.enitity.enums.SeatStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private SeatStatus seatStatus;
}
