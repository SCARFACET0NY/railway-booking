package com.anton.railway.booking.enitity;

import com.anton.railway.booking.enitity.enums.SeatClass;
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
    @Column(name = "seat_number")
    private String seatNumber;
    @Column(name = "seat_class")
    @Enumerated(value = EnumType.STRING)
    private SeatClass seatClass;
    @ManyToOne()
    @JoinColumn(name = "wagon_id")
    private Wagon wagon;
}
