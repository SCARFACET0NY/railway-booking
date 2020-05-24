package com.anton.railway.booking.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "locomotive")
public class Locomotive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locomotiveId;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "model")
    private String model;
    @Column(name = "manufacturing_year")
    private int manufacturingYear;
}
