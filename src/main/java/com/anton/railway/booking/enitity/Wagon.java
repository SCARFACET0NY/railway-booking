package com.anton.railway.booking.enitity;

import com.anton.railway.booking.enitity.enums.WagonType;
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
    @Column(name = "wagon_type")
    @Enumerated(value = EnumType.STRING)
    private WagonType wagonType;
    @Column(name = "capacity")
    private Integer capacity;
    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;
    @OneToMany(mappedBy = "wagon")
    private List<Seat> seats;
}
