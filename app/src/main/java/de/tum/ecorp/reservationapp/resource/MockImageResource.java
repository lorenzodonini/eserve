package de.tum.ecorp.reservationapp.resource;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import android.view.View;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

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

        for (String str: restaurantImages.get(restaurantId)) {
            imageLoader.loadImage(str, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    System.out.println(imageUri);
                    //TODO: finish the moron's work
                }
            });
        }

        return result;
    }

    private Map<Long, List<String>> initializeMockRestaurantImages() {
        Map<Long, List<String>> result = new HashMap<>();

        List<String> list = new ArrayList<>();

        list.add("http://www.nandos.com/sites/all/themes/nandos/images/restaurants/restaurant-carousel-1.jpg");
        result.put(1l, list);

        return result;
    }
}
