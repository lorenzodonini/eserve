package de.tum.ecorp.reservationapp.resource;

import android.location.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tum.ecorp.reservationapp.model.Restaurant;

public interface RestaurantResource {

    public enum Filter {
        RESTAURANT_NAME,        // String
        RESTAURANT_CATEGORY,    // String
        PRICE_RANGE,            // PriceRange
        AVERAGE_RATING,         // Integer
        FREE_TIME_SLOTS,        // Integer
        RESERVATION_START_TIME, // ?
        RESERVATION_END_TIME,   // ?
        RESERVATION_GUESTS,     // Integer
        SEARCH_LOCATION,        // Location
        SEARCH_RADIUS           // double
    }

    void getRestaurants(Task<List<Restaurant>> task);
    void getRestaurantsBySearchString(Task<List<Restaurant>> task, String searchString);
    void getRestaurantsNearby(Task<List<Restaurant>> task, Location searchLocation, double searchRadius);
    void getRestaurantsFiltered(Task<List<Restaurant>> task, Map<Filter, Object> filters);

    void getRestaurant(ParameterTask<Long, Restaurant> task);
}
