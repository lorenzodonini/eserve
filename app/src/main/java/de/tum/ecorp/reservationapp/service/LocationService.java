package de.tum.ecorp.reservationapp.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.Observable;

public class LocationService extends Observable implements LocationListener {

    /**
     * The minimum distance to change updates in meters
     */
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100;

    /**
     * The minimum time between updates in milliseconds
     */
    private static final long MIN_TIME_BW_UPDATES = 6000;

    private final Context context;

    private Location location;
    private LocationManager locationManager;

    private boolean gpsEnabled;
    private boolean networkEnabled;

    public LocationService(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
        this.location = calculateLocation();
    }

    public Double getLatitude() {
        return location != null ? location.getLatitude() : null;
    }

    public Double getLongitude() {
        return location != null ? location.getLongitude() : null;
    }

    public Location getLocation() {
        return location;
    }

    public boolean canGetLocation() {
        gpsEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        networkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return gpsEnabled || networkEnabled;
    }

    private Location calculateLocation() {
        Location location = null;

        if (canGetLocation()) {
            if (gpsEnabled) {
                location = getLocationForProvider(LocationManager.GPS_PROVIDER);
            }

            if (location == null && networkEnabled) {
                location = getLocationForProvider(LocationManager.NETWORK_PROVIDER);
            }
        }

        return location;
    }

    private void updateLocation(Location location) {
        this.location = location;
        setChanged();
        notifyObservers(location);
    }


    private Location getLocationForProvider(String networkProvider) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        Location location = null;

        if (locationManager != null) {
            locationManager.requestLocationUpdates(networkProvider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            location = locationManager.getLastKnownLocation(networkProvider);
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        updateLocation(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // do nothing
    }

    @Override
    public void onProviderEnabled(String provider) {
        if (LocationManager.GPS_PROVIDER.equals(provider) || LocationManager.NETWORK_PROVIDER.equals(provider)) {
            updateLocation(calculateLocation());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (LocationManager.GPS_PROVIDER.equals(provider) && status == LocationProvider.AVAILABLE) {
            updateLocation(calculateLocation());
        }
    }

}