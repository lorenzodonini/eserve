package de.tum.ecorp.reservationapp.model;

public class TimeSlot implements Comparable<TimeSlot> {

    private int startHour;
    private int startMinute;
    private int slotId;

    public TimeSlot(int startHour, int startMinute) {

        this.startHour = startHour;
        this.startMinute = startMinute;

        this.slotId = calculateSlotId(this.startHour, this.startMinute);
    }

    public TimeSlot(int startHour) {
        this(startHour, 0);
    }

    public int getSlotId() {
        return this.slotId;
    }

    @Override
    public String toString() {
        int minute = this.startMinute / 30;

        return String.format("%d:%02d", this.startHour, minute * 30);
    }

    @Override
    public int hashCode() {
        return this.slotId;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TimeSlot) {
            TimeSlot timeSlot = (TimeSlot) o;
            return timeSlot.slotId == this.slotId;
        }

        return false;
    }

    @Override
    public int compareTo(TimeSlot timeSlot) {
        return this.slotId - timeSlot.slotId;
    }

    private int calculateSlotId(int startHour, int startMinute) {
        return startHour * 2 + (startMinute / 30);
    }
}
