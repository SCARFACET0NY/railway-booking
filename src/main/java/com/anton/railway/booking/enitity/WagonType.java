package com.anton.railway.booking.enitity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wagon_type")
public class WagonType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wagonTypeId;
    @Column(name = "type")
    private String size;
    @Column(name = "capacity")
    private Integer capacity;
}
