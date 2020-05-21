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
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    @Column(name = "number")
    private String seatNumber;
    @ManyToOne()
    @JoinColumn(name = "seat_type_id")
    private SeatType seatType;
    @ManyToOne()
    @JoinColumn(name = "wagon_id")
    private Wagon wagon;
}
