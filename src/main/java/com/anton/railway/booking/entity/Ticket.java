package com.anton.railway.booking.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    @OneToOne
    @JoinColumn(name = "trip_seat_id")
    private TripSeat seat;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne()
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
