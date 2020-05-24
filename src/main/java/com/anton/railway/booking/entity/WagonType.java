package com.anton.railway.booking.entity;

import com.anton.railway.booking.entity.enums.WagonClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wagon_type")
public class WagonType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wagonTypeId;
    @Column(name = "class")
    @Enumerated(value = EnumType.STRING)
    private WagonClass wagonClass;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "price_coefficient")
    private BigDecimal priceCoefficient;
}
