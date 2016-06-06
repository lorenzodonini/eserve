package de.tum.ecorp.reservationapp.resource;

import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tum.ecorp.reservationapp.R;
import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.model.Review;

public class MockRestaurantResource implements RestaurantResource {

    private Map<Long, Restaurant> restaurants;

    public MockRestaurantResource() {
        restaurants = new HashMap<>();

        List<Restaurant> newRestaurants = createRestaurants();

        for (Restaurant restaurant : newRestaurants) {
            restaurants.put(restaurant.getId(), restaurant);
        }
    }

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
                return new ArrayList<>(restaurants.values());
            }
        }.execute();
    }

    @Override
    public void getRestaurant(final ParameterTask<Long, Restaurant> task) {
        new AsyncTask<Long, Void, Restaurant>() {

            @Override
            protected void onPreExecute() {
                task.before();
            }

            @Override
            protected void onPostExecute(Restaurant restaurant) {
                task.handleResult(restaurant);
            }

            @Override
            protected Restaurant doInBackground(Long... params) {
                return restaurants.get(params[0]);
            }
        }.execute(task.getParameters());
    }

    private List<Restaurant> createRestaurants() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("Trololol, bad waiters", 2));
        reviews.add(new Review("This weeks' product owner sucks :)", 4));
        Location restaurantLocation = new Location("dummyProvider");
        restaurantLocation.setLatitude(48.130350972491556);
        restaurantLocation.setLongitude(11.561393737792969);
        String [] imageUris = new String[3];
        imageUris[0] = "drawable://" + R.drawable.ecorp_rest1;
        imageUris[1] = "drawable://" + R.drawable.ecorp_rest2;
        imageUris[2] = "drawable://" + R.drawable.ecorp_rest3;
        Restaurant sample1 = new Restaurant("ECorp creepy restaurant", "Nerdy restaurant",
                "Boltzmannstraße 3, 85748 Garching bei München", "www.ecorp.com",
                Restaurant.PriceRange.HIGH, reviews, restaurantLocation, imageUris);

        reviews = new ArrayList<>();
        reviews.add(new Review("Best restaurant ever", 5));
        reviews.add(new Review("Cookies! Om nom nom..", 4));
        restaurantLocation = new Location("dummyProvider");
        restaurantLocation.setLatitude(48.13825869999999);
        restaurantLocation.setLongitude(11.578163000000018);
        Restaurant sample2 = new Restaurant("America Graffiti", "American Diner restaurant",
                "SomeRandomStreet 25, 666 Gotham, World", "www.inyourface.org",
                Restaurant.PriceRange.LOW, reviews, restaurantLocation, imageUris);

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(sample1);
        restaurants.add(sample2);

        return restaurants;
    }

}
