package de.tum.ecorp.reservationapp.model;

public class Table extends Entity {

    private int numberOfSeats;

    public Table(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return "TableFor" + numberOfSeats;
    }

    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

}
