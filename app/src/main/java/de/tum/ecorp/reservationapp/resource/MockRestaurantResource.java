package de.tum.ecorp.reservationapp.resource;

import android.location.Location;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tum.ecorp.reservationapp.model.OpeningTimes;
import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.model.Review;
import de.tum.ecorp.reservationapp.model.Table;
import de.tum.ecorp.reservationapp.model.TimeSlot;

public class MockRestaurantResource implements RestaurantResource {

    private Map<Long, Restaurant> restaurants;

    public MockRestaurantResource() {
        restaurants = new HashMap<>();

        List<Restaurant> newRestaurants = createMockRestaurants();

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
    public void getRestaurantsFiltered(final Task<List<Restaurant>> task, final Map<Filter, Object> filters) {
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

                List<Restaurant> result = new ArrayList<>(restaurants.values());

                for (Iterator<Restaurant> iterator = result.iterator(); iterator.hasNext(); ) {
                    Restaurant restaurant = iterator.next();
                    if (isFilteredOut(restaurant, filters)) {
                        iterator.remove();
                    }
                }

                return result;
            }
        }.execute();
    }

    @Override
    public void getRestaurantsBySearchString(final Task<List<Restaurant>> task, final String searchString) {
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

                List<Restaurant> result = new ArrayList<>(restaurants.values());

                Map<Filter, Object> filters = new HashMap<>();
                filters.put(Filter.RESTAURANT_NAME, searchString);

                for (Iterator<Restaurant> iterator = result.iterator(); iterator.hasNext(); ) {
                    Restaurant restaurant = iterator.next();
                    if (isFilteredOut(restaurant, filters)) {
                        iterator.remove();
                    }
                }

                return result;
            }
        }.execute();
    }

    @Override
    public void getRestaurantsNearby(final Task<List<Restaurant>> task, final Location searchLocation, final double searchRadius) {
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

                List<Restaurant> result = new ArrayList<>(restaurants.values());

                Map<Filter, Object> filters = new HashMap<>();
                filters.put(Filter.SEARCH_LOCATION, searchLocation);
                filters.put(Filter.SEARCH_RADIUS, searchRadius);

                for (Iterator<Restaurant> iterator = result.iterator(); iterator.hasNext(); ) {
                    Restaurant restaurant = iterator.next();
                    if (isFilteredOut(restaurant, filters)) {
                        iterator.remove();
                    }
                }

                return result;
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

    private List<Restaurant> createMockRestaurants() {

        List<Restaurant> result = new ArrayList<>();
        List<Review> reviews;
        Location location;
        List<Table> tables;
        OpeningTimes openingTimes;

        // RESTAURANT 1
        location = new Location("dummyProvider");
        location.setLatitude(48.130350972491556);
        location.setLongitude(11.561393737792969);

        reviews = new ArrayList<>();
        reviews.add(new Review("Trololol, bad waiters", 2));
        reviews.add(new Review("This weeks' product owner sucks :)", 4));

        tables = new ArrayList<>();
        tables.add(new Table(4));  // 1 table for 4
        tables.add(new Table(6));  // 1 table for 6

        openingTimes = new OpeningTimes();
        // This restaurant is opened from X to Y on mondays
        openingTimes.addTimeSlotMonday(new TimeSlot(13, 0));  //13.00-13.30
        // ...and half an hour on saturdays
        openingTimes.addTimeSlotSaturday(new TimeSlot(20, 0));  //20.00-20.30

        result.add(new Restaurant("ECorp creepy restaurant", "Nerdy restaurant",
                Restaurant.PriceRange.HIGH, location, reviews, tables, openingTimes));

        // RESTAURANT 2
        location = new Location("dummyProvider");
        location.setLatitude(48.13825869999999);
        location.setLongitude(11.578163000000018);

        reviews = new ArrayList<>();
        reviews.add(new Review("Best restaurant ever", 5));
        reviews.add(new Review("Cookies! Om nom nom..", 4));

        tables = new ArrayList<>();
        tables.add(new Table(4));  // 1 table for 4
        tables.add(new Table(6));  // 1 table for 6

        openingTimes = new OpeningTimes();
        // This restaurant is opened from X to Y on mondays
        openingTimes.addTimeSlotMonday(new TimeSlot(13, 0));  //13.00-13.30
        // ...and half an hour on saturdays
        openingTimes.addTimeSlotSaturday(new TimeSlot(20, 0));  //20.00-20.30

        result.add(new Restaurant("America Graffiti", "American Diner restaurant",
                Restaurant.PriceRange.LOW, location, reviews, tables, openingTimes));

        // RESTAURANT 3
        location = new Location("dummyProvider");
        location.setLatitude(48.1390143);
        location.setLongitude(11.5541695);

        reviews = new ArrayList<>();
        reviews.add(new Review("As a mexican, this is the worst mexican food I have ever taste in my life. The burrito look more like a Calzone. Not coming back, adios amigos!", 1));
        reviews.add(new Review("I liked the place.  Taste of food was good.", 4));

        tables = new ArrayList<>();
        tables.add(new Table(4));  // 1 table for 4
        tables.add(new Table(4));  // another table for 4
        tables.add(new Table(10)); // 1 table for 10

        openingTimes = new OpeningTimes();
        // This restaurant is opened from X to Y on mondays
        openingTimes.addTimeSlotMonday(new TimeSlot(13, 0));  //13.00-13.30
        openingTimes.addTimeSlotMonday(new TimeSlot(13, 30)); //13.30-14.00
        openingTimes.addTimeSlotMonday(new TimeSlot(14, 0));  //14.00-14.30
        openingTimes.addTimeSlotMonday(new TimeSlot(14, 30)); //14.30-15.00
        // ...and half an hour on saturdays
        openingTimes.addTimeSlotSaturday(new TimeSlot(20, 0));  //20.00-20.30

        openingTimes.toString();

        result.add(new Restaurant("La Cucaracha", "Tex Mex Restaurant, Mexican Restaurant",
                Restaurant.PriceRange.MEDIUM, location, reviews, tables, openingTimes));

        return result;
    }

    private boolean isFilteredOut(Restaurant restaurant, Map<Filter, Object> filters) {

        int timeSlots = -1;
        int startTime = -1;
        int endTime = -1;
        int numberOfVisitors = -1;

        Location searchLocation = null;
        double searchRadius = -1;

        for (Map.Entry<Filter, Object> entry : filters.entrySet()) {
            Object value = entry.getValue();

            switch (entry.getKey()) {
                case RESTAURANT_NAME:
                    String nameSearchString = (String) value;

                    if (! stringContainsString(restaurant.getName(), nameSearchString)) {
                        return true;
                    }
                    break;

                case RESTAURANT_CATEGORY:
                    String categorySearchString = (String) value;

                    if (! stringContainsString(restaurant.getCategory(), categorySearchString)) {
                        return true;
                    }
                    break;

                case PRICE_RANGE:
                    if (restaurant.getPriceRange().getNumberRepresentation() > (int) value) {
                        return true;
                    }
                    break;

                case AVERAGE_RATING:
                    if (restaurant.getRating() - (float) value < 0) {
                        return true;
                    }
                    break;

                case FREE_TIME_SLOTS:
                    timeSlots = (int) value;
                    if (! timeSlotsOkay(restaurant, timeSlots, startTime, endTime, numberOfVisitors)) {
                        return true;
                    }
                    break;

                case RESERVATION_START_TIME:
                    startTime = (int) value;
                    if (! timeSlotsOkay(restaurant, timeSlots, startTime, endTime, numberOfVisitors)) {
                        return true;
                    }
                    break;

                case RESERVATION_END_TIME:
                    endTime = (int) value;
                    if (! timeSlotsOkay(restaurant, timeSlots, startTime, endTime, numberOfVisitors)) {
                        return true;
                    }
                    break;

                case RESERVATION_GUESTS:
                    numberOfVisitors = (int) value;
                    if (! timeSlotsOkay(restaurant, timeSlots, startTime, endTime, numberOfVisitors)) {
                        return true;
                    }
                    break;

                case SEARCH_LOCATION:
                    searchLocation = (Location) value;
                    if (! locationOkay(restaurant, searchLocation, searchRadius)) {
                        return true;
                    }
                    break;

                case SEARCH_RADIUS:
                    searchRadius = (double) value;
                    if (! locationOkay(restaurant, searchLocation, searchRadius)) {
                        return true;
                    }
                    break;

                default:
                    //TODO: Log or sth?
            }

        }

        return false;
    }

    private boolean stringContainsString(String a, String b) {
        String normalizedA = a.toLowerCase().replaceAll("\\W", "");
        String normalizedB = b.toLowerCase().replaceAll("\\W", "");

        return normalizedA.contains(normalizedB);
    }

    private boolean timeSlotsOkay(Restaurant restaurant, int timeSlots, int startTime, int endTime, int numberOfVisitors ) {

        if (timeSlots == -1 || startTime == -1 || endTime == -1 || numberOfVisitors == -1) {
            // Not all fields have been filled. Keep going.
            return true;
        }

        //TODO: Implement time slot logic

        return true;
    }

    private boolean locationOkay(Restaurant restaurant, Location searchLocation, double searchRadius) {

        if (searchLocation == null || searchRadius == -1) {
            // Not all fields have been filled. Keep going.
            return true;
        }

        if (searchLocation.distanceTo(restaurant.getLocation()) > searchRadius) {
            return false;
        }

        return true;
    }

}
