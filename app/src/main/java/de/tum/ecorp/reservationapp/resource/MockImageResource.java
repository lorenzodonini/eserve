package de.tum.ecorp.reservationapp.resource;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import android.os.AsyncTask;
import android.view.View;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tum.ecorp.reservationapp.model.Restaurant;

public class MockImageResource {

    private Context context;
    private Resources resources;

    private ImageLoader imageLoader;
    private Map<Long, List<String>> restaurantImages;

    public MockImageResource(Context context, Resources resources) {
        this.context = context;
        this.resources = resources;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        restaurantImages = initializeMockRestaurantImages();
    }

    public List<String> getRestaurantImageUris(Restaurant restaurant) {
        return restaurantImages.get(restaurant.getId());
    }

    public void getRestaurantImageUrisAsync(final Task<List<String>> task, final Restaurant restaurant) {
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

        List<String> list = new ArrayList<>();

        list.add("http://www.nandos.com/sites/all/themes/nandos/images/restaurants/restaurant-carousel-1.jpg");
        result.put(1l, list);

        return result;
    }
}
