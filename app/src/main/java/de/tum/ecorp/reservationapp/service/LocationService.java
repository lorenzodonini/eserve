package de.tum.ecorp.reservationapp.service;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.Observable;

class LocationService extends Observable implements LocationListener {

    public static final Location DEFAULT_LOCATION = new Location("dummyProvider");

    static {
        DEFAULT_LOCATION.setLatitude(48.13703389999999);
        DEFAULT_LOCATION.setLongitude(11.575813400000015);
    }

    /**
     * The minimum distance to change updates in meters
     */
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100;

    /**
     * The minimum time between updates in milliseconds
     */
    private static final long MIN_TIME_BW_UPDATES = 6000;

    private final LocationManager locationManager;

    public LocationService(LocationManager locationManager) {
        this.locationManager = locationManager;
        registerForLocationUpdates();
    }

    private void registerForLocationUpdates() throws SecurityException {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    }

    public Location getLocation() throws SecurityException {
        Location location = null;

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (location == null) {
            location = DEFAULT_LOCATION;
        }

        return location;
    }

    public boolean canGetLocation() {
        boolean gpsEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        boolean networkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return gpsEnabled || networkEnabled;
    }

    private void updateLocation(Location location) {
        setChanged();
        notifyObservers(location);
    }

    public void stopUsingGPS() throws SecurityException{
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            updateLocation(location);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // do nothing
    }

    @Override
    public void onProviderEnabled(String provider) {
        updateLocation(getLocation());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        updateLocation(getLocation());
    }

}