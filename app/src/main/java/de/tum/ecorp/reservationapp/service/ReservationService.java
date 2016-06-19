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

    List<Table> getAvailableTables(Long restaurantId, Date date);
    List<Table> getAvailableTables(Long restaurantId, Date date, int numberOfSeats);
    List<Table> getAvailableTables(Long restaurantId, Date date, Set<TimeSlot> timeSlots);
    List<Table> getAvailableTables(Long restaurantId, Date date, int numberOfSeats, Set<TimeSlot> timeSlots);

    List<TimeSlot> getAvailableTimeSlots(Long restaurantId, Date date);
    List<TimeSlot> getAvailableTimeSlots(Long restaurantId, Date date, Table table);
}
