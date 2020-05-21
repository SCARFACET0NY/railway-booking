package com.anton.railway.booking.enitity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "seat_type")
public class SeatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatTypeId;
    @Column(name = "class")
    private String seatClass;
    @Column(name = "price_coefficient")
    private BigDecimal priceCoefficient;
}
