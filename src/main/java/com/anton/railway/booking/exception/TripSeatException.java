package com.anton.railway.booking.exception;

public class TripSeatException extends RuntimeException {
    public TripSeatException(String message) {
        super(message);
    }

    public TripSeatException(String message, Throwable cause) {
        super(message, cause);
    }
}
