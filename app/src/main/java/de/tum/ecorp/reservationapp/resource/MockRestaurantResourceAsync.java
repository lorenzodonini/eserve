package de.tum.ecorp.reservationapp.resource;

import android.location.Location;
import android.os.AsyncTask;

import java.util.List;
import java.util.Map;

import de.tum.ecorp.reservationapp.model.Restaurant;

public class MockRestaurantResourceAsync implements RestaurantResourceAsync {

    RestaurantResource restaurantResource;

    public MockRestaurantResourceAsync() {
        this.restaurantResource = new MockRestaurantResource();
    }

    @Override
    public void getRestaurantsAsync(final Task<List<Restaurant>> task) {
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
                return restaurantResource.getRestaurants();
            }

        }.execute();
    }

    @Override
    public void getRestaurantsFilteredAsync(final Task<List<Restaurant>> task, final Map<RestaurantResource.Filter, Object> filters) {
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
                return restaurantResource.getRestaurantsFiltered(filters);
            }

        }.execute();
    }

    @Override
    public void getRestaurantsBySearchStringAsync(final Task<List<Restaurant>> task, final String searchString) {
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
                return restaurantResource.getRestaurantsBySearchString(searchString);
            }

        }.execute();
    }

    @Override
    public void getRestaurantsNearbyAsync(final Task<List<Restaurant>> task, final Location searchLocation, final Double searchRadius) {
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
                return restaurantResource.getRestaurantsNearby(searchLocation, searchRadius);
            }

        }.execute();
    }

    @Override
    public void getRestaurantAsync(final Task<Restaurant> task, final Long restaurantId) {
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
                return restaurantResource.getRestaurant(restaurantId);
            }

        }.execute();
    }
}
