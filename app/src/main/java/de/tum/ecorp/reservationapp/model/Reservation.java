package de.tum.ecorp.reservationapp.model;

import java.util.Date;
import java.util.Set;

public class Reservation extends Entity {

    private Integer restaurantId;
    private Date date;
    private Set<TimeSlot> timeSlots;
    private Table table;

    public Reservation() {
        //TODO
    }

    public Integer getRestaurantId() {
        return this.restaurantId;
    }

    public Date getDate() {
        return this.date;
    }

    public Table getTable() {
        return this.table;
    }

    public Set<TimeSlot> getTimeSlots() {
        return this.timeSlots;
    }
}
