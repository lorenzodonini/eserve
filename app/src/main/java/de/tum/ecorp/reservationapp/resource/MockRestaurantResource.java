package de.tum.ecorp.reservationapp.resource;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.model.Review;

public class MockRestaurantResource implements RestaurantResource {

    @Override
    public void getRestaurants(final Task<List<Restaurant>> task) {
        new AsyncTask<Void, Void, List<Restaurant>>() {

            @Override
            protected void onPreExecute() {
                task.before();
            }

            @Override
            protected void onPostExecute(List<Restaurant> restaurants) {
                task.handleResult(restaurants);
            }

            @Override
            protected List<Restaurant> doInBackground(Void... params) {
                List<Review> reviews = new ArrayList<>();
                reviews.add(new Review("Trololol, bad waiters", 2));
                reviews.add(new Review("This weeks' product owner sucks :)", 4));
                Restaurant sample1 = new Restaurant("ECorp creepy restaurant", "Nerdy restaurant", Restaurant.PriceRange.HIGH, reviews);

                reviews = new ArrayList<>();
                reviews.add(new Review("Best restaurant ever", 5));
                reviews.add(new Review("Cookies! Om nom nom..", 4));
                Restaurant sample2 = new Restaurant("America Graffiti", "American Diner restaurant", Restaurant.PriceRange.LOW, reviews);

                List<Restaurant> restaurants = new ArrayList<>();
                restaurants.add(sample1);
                restaurants.add(sample2);
                return restaurants;
            }
        }.execute();
    }


}
