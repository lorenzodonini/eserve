package de.tum.ecorp.reservationapp.resource;

import android.location.Location;

import java.util.List;
import java.util.Map;

import de.tum.ecorp.reservationapp.model.Restaurant;

public interface RestaurantResourceAsync {

    void getRestaurantsAsync(final Task<List<Restaurant>> task);
    void getRestaurantsFilteredAsync(final Task<List<Restaurant>> task, final Map<RestaurantResource.Filter, Object> filters);
    void getRestaurantsBySearchStringAsync(final Task<List<Restaurant>> task, final String searchString);
    void getRestaurantsNearbyAsync(final Task<List<Restaurant>> task, final Location searchLocation, final Double searchRadius);
    void getRestaurantAsync(final Task<Restaurant> task, Long restaurantId);

}
