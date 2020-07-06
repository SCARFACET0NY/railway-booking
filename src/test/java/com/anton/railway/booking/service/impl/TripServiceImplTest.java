package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.converter.TripToTripDto;
import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.Trip;
import com.anton.railway.booking.entity.enums.TripStatus;
import com.anton.railway.booking.exception.TripException;
import com.anton.railway.booking.exception.UserException;
import com.anton.railway.booking.repository.TripRepository;
import com.anton.railway.booking.service.TripService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceImplTest {
    private static final long ID = 1L;
    private static final String DEPARTURE_CITY = "Kiev";
    private static final String ARRIVAL_CITY = "Lviv";
    private static final List<Trip> trips = new ArrayList<>();
    private static final Trip trip = new Trip();
    private static final TripDto tripDto = new TripDto();
    private static final TripDto tripDto1 = new TripDto();
    @Mock
    TripRepository tripRepository;
    @Mock
    TripToTripDto tripToTripDto;
    @InjectMocks
    TripServiceImpl tripService;

    @BeforeAll
    static void beforeAll() {
        Trip trip1 = new Trip();
        trip.setTripId(ID);
        tripDto.setTripId(ID);

        trips.add(trip);
        trips.add(trip1);
    }

    @Test
    void findAllTest() {
        when(tripRepository.findAll()).thenReturn(trips);
        List<Trip> returnedTrips = tripService.findAll();

        assertNotNull(returnedTrips);
        assertEquals(2, returnedTrips.size());
        assertEquals(trips, returnedTrips);

        verify(tripRepository).findAll();
    }

    @Test
    void findByIdTest() {
        when(tripRepository.findById(anyLong()))
                .thenReturn(Optional.of(trip))
                .thenReturn(Optional.empty());
        Trip returnedTrip = tripService.findById(ID);

        assertNotNull(returnedTrip);
        assertEquals(trip, returnedTrip);
        assertEquals(trip.getTripId(), returnedTrip.getTripId());
        assertThrows(TripException.class, () -> tripService.findById(ID));

        verify(tripRepository, times(2)).findById(anyLong());
    }

    @Test
    void saveTest() {
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        assertEquals(tripService.save(trip), trip);

        verify(tripRepository).save(any(Trip.class));
    }

    @Test
    void deleteTest() {
        tripService.delete(trip);

        verify(tripRepository).delete(any(Trip.class));
    }

    @Test
    void deleteByIdTest() {
        tripService.deleteById(ID);

        verify(tripRepository).deleteById(anyLong());
    }

    @Test
    void findTripDtoByIdTest() {
        when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip));
        when(tripToTripDto.convert(any(Trip.class))).thenReturn(tripDto);

        TripDto returnedTripDto = tripService.findTripDtoById(ID);

        assertNotNull(returnedTripDto);
        assertEquals(tripDto, returnedTripDto);
        assertEquals(tripDto.getTripId(), returnedTripDto.getTripId());

        verify(tripToTripDto).convert(any(Trip.class));
        verify(tripRepository).findById(anyLong());
    }

    @Test
    void findAllScheduledTripsTest() {
        when(tripRepository.findAllByTripStatus(TripStatus.SCHEDULED)).thenReturn(trips);
        when(tripToTripDto.convert(any(Trip.class)))
                .thenReturn(tripDto)
                .thenReturn(tripDto1);
        List<TripDto> returnedTripDtos = tripService.findAllScheduledTrips();

        assertNotNull(returnedTripDtos);
        assertEquals(returnedTripDtos.size(), 2);
        assertTrue(returnedTripDtos.contains(tripDto));
        assertTrue(returnedTripDtos.contains(tripDto1));

        verify(tripToTripDto, times(2)).convert(any(Trip.class));
        verify(tripRepository).findAllByTripStatus(TripStatus.SCHEDULED);
    }

    @Test
    void findScheduledTripsForDate() {
        when(tripRepository.findAllByTripStatusAndAndDepartureDate(any(TripStatus.class), any(LocalDate.class)))
                .thenReturn(trips);

        List<Trip> returnedTrips = tripService.findScheduledTripsForDate(LocalDate.now());

        assertNotNull(returnedTrips);
        assertEquals(2, returnedTrips.size());
        assertEquals(trips, returnedTrips);

        verify(tripRepository).findAllByTripStatusAndAndDepartureDate(any(TripStatus.class), any(LocalDate.class));
    }

    @Test
    void searchTripsTest() {
        when(tripRepository.findAllByRouteDepartureStationCityAndRouteArrivalStationCity(anyString(), anyString()))
                .thenReturn(trips);
        when(tripToTripDto.convert(any(Trip.class)))
                .thenReturn(tripDto)
                .thenReturn(tripDto1);
        List<TripDto> returnedTripDtos = tripService.searchTrips(DEPARTURE_CITY, ARRIVAL_CITY);

        assertNotNull(returnedTripDtos);
        assertEquals(returnedTripDtos.size(), 2);
        assertTrue(returnedTripDtos.contains(tripDto));
        assertTrue(returnedTripDtos.contains(tripDto1));

        verify(tripToTripDto, times(2)).convert(any(Trip.class));
        verify(tripRepository).findAllByRouteDepartureStationCityAndRouteArrivalStationCity(anyString(), anyString());
    }

    @Test
    void testSearchTripsWithDateTest() {
        when(tripRepository.findAllByRouteDepartureStationCityAndRouteArrivalStationCityAndDepartureDate(
                anyString(), anyString(), any(LocalDate.class))).thenReturn(trips);
        when(tripToTripDto.convert(any(Trip.class)))
                .thenReturn(tripDto)
                .thenReturn(tripDto1);
        List<TripDto> returnedTripDtos = tripService.searchTrips(DEPARTURE_CITY, ARRIVAL_CITY, LocalDate.now());

        assertNotNull(returnedTripDtos);
        assertEquals(returnedTripDtos.size(), 2);
        assertTrue(returnedTripDtos.contains(tripDto));
        assertTrue(returnedTripDtos.contains(tripDto1));

        verify(tripToTripDto, times(2)).convert(any(Trip.class));
        verify(tripRepository).findAllByRouteDepartureStationCityAndRouteArrivalStationCityAndDepartureDate(
                anyString(), anyString(), any(LocalDate.class));
    }
}