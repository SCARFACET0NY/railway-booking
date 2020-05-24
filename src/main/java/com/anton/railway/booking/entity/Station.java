package com.anton.railway.booking.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stationId;
    @Column(name = "title")
    private String title;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "code")
    private String code;
}
