package com.anton.railway.booking.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    @Column(name = "total")
    private BigDecimal total;
    @OneToMany(mappedBy = "payment")
    private List<Ticket> tickets;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
