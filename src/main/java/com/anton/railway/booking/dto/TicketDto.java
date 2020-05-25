package com.anton.railway.booking.dto;

import com.anton.railway.booking.entity.Ticket;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto {
    private Ticket ticket;
    private LocalDateTime departure;
    private LocalDateTime arrival;
}
