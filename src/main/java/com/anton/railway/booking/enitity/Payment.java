package com.anton.railway.booking.enitity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
