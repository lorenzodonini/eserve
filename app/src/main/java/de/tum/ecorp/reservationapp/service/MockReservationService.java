package de.tum.ecorp.reservationapp.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tum.ecorp.reservationapp.model.Reservation;
import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.model.Table;
import de.tum.ecorp.reservationapp.model.TimeSlot;
import de.tum.ecorp.reservationapp.resource.RestaurantResource;

public class MockReservationService implements ReservationService {

    private RestaurantResource restaurantResource;
    private Map<Long, Map<Date, Map<Table, Map<TimeSlot, Boolean>>>> reservations;

    public MockReservationService(RestaurantResource restaurantResource) {
        this.restaurantResource = restaurantResource;
        this.reservations = new HashMap<>();
    }

    private Map<Date, Map<Table, Map<TimeSlot, Boolean>>> getReservationsForRestaurant(Integer restaurantId) {
        Map<Date, Map<Table, Map<TimeSlot, Boolean>>> result = this.reservations.get(restaurantId);

        if (result != null) {
            return result;
        } else {
            return new HashMap<>();
        }
    }

    private Map<Table, Map<TimeSlot, Boolean>> getReservationsForRestaurantOn(Integer restaurantId, Date date) {
        Map<Table, Map<TimeSlot, Boolean>> result = getReservationsForRestaurant(restaurantId).get(date);

        if (result != null) {
            return result;
        } else {
            return new HashMap<>();
        }
    }

    private Map<TimeSlot, Boolean> getReservationsForRestaurantOnTable(Integer restaurantId, Date date, Table table) {
        Map<TimeSlot, Boolean> result = getReservationsForRestaurantOn(restaurantId, date).get(table);

        if (result != null) {
            return result;
        } else {
            return new HashMap<>();
        }
    }

    @Override
    public void addReservation(Reservation reservation) {

        Integer restaurantId = reservation.getRestaurantId();
        Date date = reservation.getDate();
        Table table = reservation.getTable();

        for (TimeSlot ts : reservation.getTimeSlots()) {
            getReservationsForRestaurantOnTable(restaurantId, date, table).put(ts, true);
        }
    }

    @Override
    public void removeReservation(Reservation reservation) {
        Integer restaurantId = reservation.getRestaurantId();
        Date date = reservation.getDate();
        Table table = reservation.getTable();

        for (TimeSlot ts : reservation.getTimeSlots()) {
            getReservationsForRestaurantOnTable(restaurantId, date, table).remove(ts);
        }
    }

    @Override
    public List<Table> getAvailableTables(Integer restaurantId, Date date) {

        Restaurant restaurant = restaurantResource.getRestaurant(restaurantId);
        List<Table> tables = restaurant.getTables();

        Map<Table, Map<TimeSlot, Boolean>> reservedTables = getReservationsForRestaurantOn(restaurantId, date);

        for (Iterator<Table> iterator = tables.iterator(); iterator.hasNext(); ) {
            Table t = iterator.next();

            Map<TimeSlot, Boolean> timeSlots = reservedTables.get(t);
            if (timeSlots == null) {
                continue;
            } else {
                for (Boolean bool : timeSlots.values()) {
                    if (bool == false) {
                        continue;
                    } else {
                        iterator.remove();
                    }
                }
            }
        }

        return tables;
    }

    @Override
    public List<Table> getAvailableTables(Integer restaurantId, Date date, int numberOfSeats) {

        List<Table> tables = getAvailableTables(restaurantId, date);

        for (Iterator<Table> iterator = tables.iterator(); iterator.hasNext(); ) {
            Table table = iterator.next();

            if (table.getNumberOfSeats() < numberOfSeats) {
                iterator.remove();
            }
        }

        return tables;
    }

    @Override
    public List<Table> getAvailableTables(Integer restaurantId, Date date, List<TimeSlot> timeSlots) {

        Restaurant restaurant = restaurantResource.getRestaurant(restaurantId);
        List<Table> tables = restaurant.getTables();

        Map<Table, Map<TimeSlot, Boolean>> reservedTables = getReservationsForRestaurantOn(restaurantId, date);

        for (Iterator<Table> iterator = tables.iterator(); iterator.hasNext(); ) {
            Table t = iterator.next();

            for (TimeSlot ts : timeSlots) {
                if (reservedTables.containsKey(t)) {
                    // check if slot is still free
                    if (reservedTables.get(t).containsKey(ts)) {
                        if (reservedTables.get(t).get(ts) == true) {
                            iterator.remove();
                        } else {
                            continue;
                        }
                    }
                } else {
                    continue;
                }
            }
        }

        return tables;
    }

    @Override
    public List<Table> getAvailableTables(Integer restaurantId, Date date, int numberOfSeats, List<TimeSlot> timeSlots) {

        List<Table> tables = getAvailableTables(restaurantId, date, timeSlots);

        for (Iterator<Table> it = tables.iterator(); it.hasNext(); ) {
            Table t = it.next();
            if (t.getNumberOfSeats() < numberOfSeats) {
                it.remove();
            }
        }

        return tables;
    }

    @Override
    public List<TimeSlot> getAvailableTimeSlots(Integer restaurantId, Date date) {

        Map<Table, Map<TimeSlot, Boolean>> reservedTables = getReservationsForRestaurantOn(restaurantId, date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int weekDay = c.get(Calendar.DAY_OF_WEEK);
        Log.d("Day  ", ""+weekDay);
        Log.d("INSIDE 2", "" + restaurantResource.getRestaurant(restaurantId).getOpeningTimes());
        Log.d("INSIDE 3", "" + restaurantResource.getRestaurant(restaurantId).getOpeningTimes().getTimeSlots(weekDay));
        List<TimeSlot> result = new ArrayList<>(restaurantResource.getRestaurant(restaurantId).getOpeningTimes().getTimeSlots(weekDay));

        for (Iterator<Map.Entry<Table, Map<TimeSlot, Boolean>>> it = reservedTables.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Table, Map<TimeSlot, Boolean>> entry = it.next();

            for (Iterator<Map.Entry<TimeSlot, Boolean>> iter = entry.getValue().entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry<TimeSlot, Boolean> tsb = iter.next();

                if (tsb.getValue() == true) {
                    result.remove(tsb.getKey());
                } else {
                    continue;
                }
            }
        }

        return result;
    }

    @Override
    public List<TimeSlot> getAvailableTimeSlots(Integer restaurantId, Date date, Table table) {

        Map<Table, Map<TimeSlot, Boolean>> reservedTables = getReservationsForRestaurantOn(restaurantId, date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int weekDay = c.get(Calendar.DAY_OF_WEEK);
        List<TimeSlot> result = new ArrayList<>(restaurantResource.getRestaurant(restaurantId).getOpeningTimes().getTimeSlots(weekDay));

        Map<TimeSlot, Boolean> tableReservations = reservedTables.get(table);
        if (tableReservations == null) {
            return result;
        } else {

            for (Iterator<Map.Entry<TimeSlot, Boolean>> it = tableReservations.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<TimeSlot, Boolean> entry = it.next();

                if (entry.getValue() == true) {
                    result.remove(entry.getKey());
                } else {
                    continue;
                }
            }
        }

        return result;
    }
}
