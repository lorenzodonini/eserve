package de.tum.ecorp.reservationapp.model;

import java.util.Date;
import java.util.Set;

public class Reservation extends Entity {

    private long restaurantId;
    private Date date;
    private Set<TimeSlot> timeSlots;
    private Table table;

    public Reservation() {
        //TODO
    }
}
