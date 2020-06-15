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
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;
    @OneToOne
    @JoinColumn(name = "departure_station_id")
    private Station departureStation;
    @OneToOne
    @JoinColumn(name = "arrival_station_id")
    private Station arrivalStation;
    @Column(name = "duration_in_minutes")
    private Integer durationInMinutes;
    @Column(name = "base_price")
    private BigDecimal basePrice;
    @Column(name = "code")
    private String code;
}
