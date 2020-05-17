package com.anton.railway.booking.enitity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @JoinColumn(name = "departure_station")
    private Station departureStation;
    @OneToOne
    @JoinColumn(name = "arrival_station")
    private Station arrivalStation;
    @Column(name = "duration_in_minutes")
    private Integer durationInMinutes;
    @Column(name = "code")
    private String code;
}
