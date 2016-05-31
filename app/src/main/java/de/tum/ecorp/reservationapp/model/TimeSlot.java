package de.tum.ecorp.reservationapp.model;

public class TimeSlot implements Comparable<TimeSlot> {

    private int startHour;
    private int startMinute;
    private int id;

    public TimeSlot(int startHour, int startMinute) {

        this.startHour = startHour;
        this.startMinute = startMinute;

        this.id = calculateId(this.startHour, this.startMinute);
    }

    public TimeSlot(int startHour) {
        this(startHour, 0);
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        int minute = this.startMinute / 30;

        return String.format("%d:%02d", this.startHour, minute * 30);
    }

    public int hashCode() {
        return this.id;
    }

    public boolean equals(Object o) {
        if (o instanceof TimeSlot) {
            TimeSlot timeSlot = (TimeSlot) o;
            return timeSlot.id == this.id;
        }

        return false;
    }

    @Override
    public int compareTo(TimeSlot timeSlot) {
        return this.id - timeSlot.id;
    }

    private int calculateId(int startHour, int startMinute) {
        return startHour * 2 + (startMinute / 30);
    }
}
