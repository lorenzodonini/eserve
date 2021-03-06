package de.tum.ecorp.reservationapp.resource;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tum.ecorp.reservationapp.model.Restaurant;

public class MockImageResource {

    private Map<Long, List<String>> restaurantImages;

    public MockImageResource() {
        restaurantImages = initializeMockRestaurantImages();
    }

    public List<String> getRestaurantImageUris(Restaurant restaurant) {
        return restaurantImages.get(restaurant.getId());
    }

    public void getRestaurantImageUrisAsync(final Restaurant restaurant, final Task<List<String>> task) {
        new AsyncTask<Void, Void, List<String>>() {

            @Override
            protected void onPreExecute() {
                task.before();
            }

            @Override
            protected void onPostExecute(List<String> imageURLs) {
                task.handleResult(imageURLs);
            }

            @Override
            protected List<String> doInBackground(Void... params) {
                return getRestaurantImageUris(restaurant);
            }

        }.execute();
    }

    private Map<Long, List<String>> initializeMockRestaurantImages() {
        Map<Long, List<String>> result = new HashMap<>();

        List<String> list;

        list = new ArrayList<>();
        list.add("https://www.omnihotels.com/-/media/images/hotels/homrst/restaurants/homrst-omni-homestead-resort-casino-restaurant.jpg");
        list.add("http://lxly7dz9m3-flywheel.netdna-ssl.com/wp-content/uploads/2015/06/2hawks.jpg");
        result.put(1l, list);

        list = new ArrayList<>();
        list.add("http://kingofwallpapers.com/restaurant/restaurant-010.jpg");
        list.add("https://upload.wikimedia.org/wikipedia/commons/1/1e/Tom's_Restaurant,_NYC.jpg");
        result.put(2l, list);

        list = new ArrayList<>();
        list.add("http://newyamya.indyco.net/admin/uploaded_image/11037_P1010505.JPG");
        list.add("http://restaurant-la-cucaracha-mex-bar.mux.de/images/1500x1200z/client/59228/86d6ecu4pv35/restaurant-bar-la-cucaracha-mex-bar-7.jpg");
        result.put(3l, list);

        return result;
    }
}
