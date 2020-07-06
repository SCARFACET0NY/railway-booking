package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.converter.TripDtoToTrip;
import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.entity.TripSeat;
import com.anton.railway.booking.entity.Wagon;
import com.anton.railway.booking.entity.enums.SeatStatus;
import com.anton.railway.booking.exception.TripSeatException;
import com.anton.railway.booking.repository.TripSeatRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripSeatServiceImplTest {
    private static final long ID = 1L;
    private static final Trip trip = new Trip();
    private static final TripDto tripDto = new TripDto();
    private static final TripSeat tripSeat = new TripSeat();
    private static final List<TripSeat> tripSeats = new ArrayList<>();
    private static final Wagon wagon = new Wagon();
    @Mock
    TripSeatRepository tripSeatRepository;
    @Mock
    TripDtoToTrip tripDtoToTrip;
    @InjectMocks
    TripSeatServiceImpl tripSeatService;

    @BeforeAll
    static void beforeAll() {
        TripSeat tripSeat1 = new TripSeat();
        tripSeat.setTripSeatId(ID);

        tripSeats.add(tripSeat);
        tripSeats.add(tripSeat1);
    }

    @Test
    void findAllTest() {
        when(tripSeatRepository.findAll()).thenReturn(tripSeats);
        List<TripSeat> returnedTripSeats = tripSeatService.findAll();

        assertNotNull(returnedTripSeats);
        assertEquals(2, returnedTripSeats.size());
        assertEquals(tripSeats, returnedTripSeats);

        verify(tripSeatRepository).findAll();
    }

    @Test
    void findByIdTest() {
        when(tripSeatRepository.findById(anyLong()))
                .thenReturn(Optional.of(tripSeat))
                .thenReturn(Optional.empty());
        TripSeat returnedTripSeat = tripSeatService.findById(ID);

        assertNotNull(returnedTripSeat);
        assertEquals(tripSeat, returnedTripSeat);
        assertEquals(tripSeat.getTripSeatId(), returnedTripSeat.getTripSeatId());
        assertThrows(TripSeatException.class, () -> tripSeatService.findById(ID));

        verify(tripSeatRepository, times(2)).findById(anyLong());
    }

    @Test
    void saveTest() {
        when(tripSeatRepository.save(any(TripSeat.class))).thenReturn(tripSeat);

        assertEquals(tripSeatService.save(tripSeat), tripSeat);

        verify(tripSeatRepository).save(any(TripSeat.class));
    }

    @Test
    void deleteTest() {
        tripSeatService.delete(tripSeat);

        verify(tripSeatRepository).delete(any(TripSeat.class));
    }

    @Test
    void deleteByIdTest() {
        tripSeatService.deleteById(ID);

        verify(tripSeatRepository).deleteById(anyLong());
    }

    @Test
    void findWagonFreeSeatsForTripDtoTest() {
        when(tripDtoToTrip.convert(any(TripDto.class))).thenReturn(trip);
        when(tripSeatRepository.findAllByTripAndSeatWagonAndSeatStatus(any(Trip.class), any(Wagon.class), any(SeatStatus.class)))
                .thenReturn(tripSeats);
        List<TripSeat> returnedTripSeats = tripSeatService.findWagonFreeSeatsForTrip(wagon, tripDto);

        assertNotNull(returnedTripSeats);
        assertEquals(2, returnedTripSeats.size());
        assertEquals(tripSeats, returnedTripSeats);

        verify(tripDtoToTrip).convert(any(TripDto.class));
        verify(tripSeatRepository).findAllByTripAndSeatWagonAndSeatStatus(any(Trip.class), any(Wagon.class), any(SeatStatus.class));
    }

    @Test
    void testFindWagonFreeSeatsForTripTest() {
        when(tripSeatRepository.findAllByTripAndSeatWagonAndSeatStatus(any(Trip.class), any(Wagon.class), any(SeatStatus.class)))
                .thenReturn(tripSeats);
        List<TripSeat> returnedTripSeats = tripSeatService.findWagonFreeSeatsForTrip(wagon, trip);

        assertNotNull(returnedTripSeats);
        assertEquals(2, returnedTripSeats.size());
        assertEquals(tripSeats, returnedTripSeats);

        verify(tripSeatRepository).findAllByTripAndSeatWagonAndSeatStatus(any(Trip.class), any(Wagon.class), any(SeatStatus.class));
    }
}