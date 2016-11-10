package de.tum.ecorp.reservationapp.service;

import android.location.Location;

/**
 * Created by felix on 25/05/16.
 */
public interface LocationAware {

    void updateLocation(Location location);
}
