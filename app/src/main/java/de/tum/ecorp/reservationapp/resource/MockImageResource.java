package de.tum.ecorp.reservationapp.resource;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockImageResource implements ImageResource {

    Context context;
    Resources resources;

    private ImageLoader imageLoader;
    private Map<Long, List<String>> restaurantImages;

    public MockImageResource(Context context, Resources resources) {
        this.context = context;
        this.resources = resources;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        restaurantImages = initializeMockRestaurantImages();
    }

    public List<Bitmap> loadImagesForRestaurant(long restaurantId) {
        List<Bitmap> result = new ArrayList<>();

        for (String str : restaurantImages.get(restaurantId)) {
            Bitmap b = imageLoader.loadImageSync(str);
            result.add(b);
        }

        return new ArrayList<>();
    }

    private Map<Long, List<String>> initializeMockRestaurantImages() {
        Map<Long, List<String>> result = new HashMap<>();

        List<String> list = new ArrayList<>();

        list.add("http://www.nandos.com/sites/all/themes/nandos/images/restaurants/restaurant-carousel-1.jpg");
        result.put(1l, list);

        return result;
    }
}
