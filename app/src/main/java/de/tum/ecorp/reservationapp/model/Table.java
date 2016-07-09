package de.tum.ecorp.reservationapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Table extends Entity {

    private int numberOfSeats;

    public Table(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    protected Table(Parcel in) {
        numberOfSeats = in.readInt();
    }

    @Override
    public String toString() {
        return "Table For " + numberOfSeats;
    }

    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

}
