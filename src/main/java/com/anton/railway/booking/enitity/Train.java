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
@Table(name = "train")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainId;
    @ManyToOne
    @JoinColumn(name = "locomotive_id")
    private Locomotive locomotive;
    @Column(name = "number")
    private String trainNumber;
    @OneToMany(mappedBy = "train")
    private List<Wagon> wagons;
}
