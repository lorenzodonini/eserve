package de.tum.ecorp.reservationapp.resource;

import java.util.List;

import de.tum.ecorp.reservationapp.model.Restaurant;

public interface RestaurantResource {

    interface Task<ResultType> {
        void before();

        void handleResult(ResultType result);
    }

    void getRestaurants(Task<List<Restaurant>> task);
}
