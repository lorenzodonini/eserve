package de.tum.ecorp.reservationapp.resource;

import java.util.List;

import de.tum.ecorp.reservationapp.model.Restaurant;

public interface RestaurantResource {

    void getRestaurants(Task<List<Restaurant>> task);

    void getRestaurant(ParameterTask<Long, Restaurant> task);
}
