package de.tum.ecorp.reservationapp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tum.ecorp.reservationapp.model.Reservation;
import de.tum.ecorp.reservationapp.model.Table;
import de.tum.ecorp.reservationapp.model.TimeSlot;

public interface ReservationService {

    void addReservation(Reservation reservation);
    void removeReservation(Reservation reservation);

    List<Table> getAvailableTables(Integer restaurantId, Date date);
    List<Table> getAvailableTables(Integer restaurantId, Date date, int numberOfSeats);
    List<Table> getAvailableTables(Integer restaurantId, Date date, List<TimeSlot> timeSlots);
    List<Table> getAvailableTables(Integer restaurantId, Date date, int numberOfSeats, List<TimeSlot> timeSlots);

    List<TimeSlot> getAvailableTimeSlots(Integer restaurantId, Date date);
    List<TimeSlot> getAvailableTimeSlots(Integer restaurantId, Date date, Table table);
}
