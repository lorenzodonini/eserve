package de.tum.ecorp.reservationapp.model;

import android.location.Location;

public class UserManager {
    private Location currentLocation;
    //TODO: add additional information, like name, phone, preferences etc...

    private static UserManager mInstance = new UserManager();

    private UserManager() {
        //Setting dummy location
        Location marienplatzLocation = new Location("dummyProvider");
        marienplatzLocation.setLatitude(48.13703389999999);
        marienplatzLocation.setLongitude(11.575813400000015);
        currentLocation = marienplatzLocation;
    }

    public static UserManager getInstance() {
        return mInstance;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
