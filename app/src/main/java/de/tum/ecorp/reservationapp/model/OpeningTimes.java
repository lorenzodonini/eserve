package de.tum.ecorp.reservationapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class OpeningTimes {

    private static int NUMBER_OF_WEEKDAYS = 7;
    private static int MONDAY = 0;
    private static int TUESDAY = 1;
    private static int WEDNESDAY = 2;
    private static int THURSDAY = 3;
    private static int FRIDAY = 4;
    private static int SATURDAY = 5;
    private static int SUNDAY = 6;

    private Set<TimeSlot>[] openingTimes;

    public OpeningTimes() {
        this.openingTimes = initialize();
    }

    public void addTimeSlotMonday(TimeSlot timeSlot) {
        this.openingTimes[MONDAY].add(timeSlot);
    }

    public void addTimeSlotTuesday(TimeSlot timeSlot) {
        this.openingTimes[TUESDAY].add(timeSlot);
    }

    public void addTimeSlotWednesday(TimeSlot timeSlot) {
        this.openingTimes[WEDNESDAY].add(timeSlot);
    }

    public void addTimeSlotThursday(TimeSlot timeSlot) {
        this.openingTimes[THURSDAY].add(timeSlot);
    }

    public void addTimeSlotFriday(TimeSlot timeSlot) {
        this.openingTimes[FRIDAY].add(timeSlot);
    }

    public void addTimeSlotSaturday(TimeSlot timeSlot) {
        this.openingTimes[SATURDAY].add(timeSlot);
    }

    public void addTimeSlotSunday(TimeSlot timeSlot) {
        this.openingTimes[SUNDAY].add(timeSlot);
    }

    public String toString() {
        String result = new String();
        for (int i=0; i<NUMBER_OF_WEEKDAYS; i++) {
            String weekday = summarizeTimeSlots(openingTimes[i]);

            if (! weekday.isEmpty()) {
                result += weekday + "\n";
            }
        }
        return result;
    }

    public String[] toStringArray() {
        String[] result = new String[NUMBER_OF_WEEKDAYS];
        for (int i=0; i<NUMBER_OF_WEEKDAYS; i++) {
            result[i] = summarizeTimeSlots(openingTimes[i]);
        }
        return result;
    }

    public String summarizeTimeSlots(Set<TimeSlot> timeSlots) {
        List<TimeSlot> slotList = new ArrayList<>(timeSlots);
        Collections.sort(slotList);

        String result = new String();
        for (Iterator<TimeSlot> it = slotList.iterator(); it.hasNext(); ) {
            TimeSlot ts = it.next();
            result += ts.toString();

            if (it.hasNext()) {
                result += ", ";
            }
        }

        return result; //TODO: Adjust format
    }

    private Set<TimeSlot>[] initialize() {
        Set<TimeSlot>[] result = new Set[NUMBER_OF_WEEKDAYS];
        for (int i=0; i<NUMBER_OF_WEEKDAYS; i++) {
            result[i] = new HashSet<>();
        }
        return result;
    }
}
