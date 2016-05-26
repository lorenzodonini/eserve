package de.tum.ecorp.reservationapp.service;

import android.location.Location;
import android.location.LocationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class UserManager implements Observer {
    private Location currentLocation = LocationService.DEFAULT_LOCATION;

    private LocationService locationService;

    private List<LocationAware> locationListeners = new ArrayList<>();

    private static final UserManager INSTANCE = new UserManager();

    private UserManager() {

    }

    public static UserManager getInstance() {
        return INSTANCE;
    }

    public void enableLocationService(LocationManager locationManager, List<LocationAware> listeners) {
        locationListeners.addAll(listeners);

        locationService = new LocationService(locationManager);
        locationService.addObserver(this);
        currentLocation = locationService.getLocation();

    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public boolean canGetLocation() {
        return locationService.canGetLocation();
    }

    public void registerForLocationUpdates(LocationAware observer) {
        locationListeners.add(observer);
    }

    @Override
    public void update(Observable observable, Object data) {
        Location location = (Location) data;
        currentLocation = location;
        for (LocationAware listener : locationListeners) {
            listener.updateLocation(location);
        }
    }
}
