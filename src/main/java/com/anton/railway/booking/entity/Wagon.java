package com.anton.railway.booking.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.transaction.annotation.Transactional;

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
    @Column(name = "number")
    private String wagonNumber;
    @ManyToOne()
    @JoinColumn(name = "wagon_type_id")
    private WagonType wagonType;
    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wagon")
    private List<Seat> seats;
}
