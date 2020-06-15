package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.converter.TripDtoToTrip;
import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.entity.TripSeat;
import com.anton.railway.booking.entity.Wagon;
import com.anton.railway.booking.entity.enums.SeatStatus;
import com.anton.railway.booking.repository.TripSeatRepository;
import com.anton.railway.booking.service.TripSeatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripSeatServiceImpl implements TripSeatService {
    private final TripSeatRepository tripSeatRepository;
    private final TripDtoToTrip tripDtoToTrip;

    public TripSeatServiceImpl(TripSeatRepository tripSeatRepository, TripDtoToTrip tripDtoToTrip) {
        this.tripSeatRepository = tripSeatRepository;
        this.tripDtoToTrip = tripDtoToTrip;
    }

    @Override
    public List<TripSeat> findAll() {
        List<TripSeat> tripSeats = new ArrayList<>();
        tripSeatRepository.findAll().forEach(tripSeats::add);

        return tripSeats;
    }

    @Override
    public TripSeat findById(Long id) {
        return tripSeatRepository.findById(id).orElse(null);
    }

    @Override
    public TripSeat save(TripSeat tripSeat) {
        return tripSeatRepository.save(tripSeat);
    }

    @Override
    public void delete(TripSeat tripSeat) {
        tripSeatRepository.delete(tripSeat);
    }

    @Override
    public void deleteById(Long id) {
        tripSeatRepository.deleteById(id);
    }

    @Override
    public List<TripSeat> findWagonFreeSeatsForTrip(Wagon wagon, TripDto tripDto) {
        return tripSeatRepository.findAllByTripAndSeatWagonAndSeatStatus(
                tripDtoToTrip.convert(tripDto), wagon, SeatStatus.FREE);
    }
}
