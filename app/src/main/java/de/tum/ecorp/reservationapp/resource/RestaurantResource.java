package de.tum.ecorp.reservationapp.resource;

import android.location.Location;

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

    List<Restaurant> getRestaurants();
    List<Restaurant> getRestaurantsBySearchString(String searchString);
    List<Restaurant> getRestaurantsNearby(Location searchLocation, Double searchRadius);
    List<Restaurant> getRestaurantsFiltered(Map<Filter, Object> filters);

    Restaurant getRestaurant(Integer restaurantId);
}
