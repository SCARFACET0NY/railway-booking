package com.anton.railway.booking.enitity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wagon")
public class Wagon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wagonId;
    @ManyToOne()
    @JoinColumn(name = "wagon_type_id")
    private WagonType wagonType;
    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;
    @OneToMany(mappedBy = "wagon")
    private List<Seat> seats;
}
