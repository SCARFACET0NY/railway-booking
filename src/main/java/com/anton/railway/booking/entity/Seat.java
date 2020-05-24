package com.anton.railway.booking.entity;

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
    @JoinColumn(name = "wagon_id")
    private Wagon wagon;
}
