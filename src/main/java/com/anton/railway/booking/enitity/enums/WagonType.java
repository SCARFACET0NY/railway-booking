package com.anton.railway.booking.enitity.enums;

public enum WagonType {
    Large(50),
    Medium(40),
    Small(30);

    public final int capacity;

    WagonType(int capacity) {
        this.capacity = capacity;
    }
}
