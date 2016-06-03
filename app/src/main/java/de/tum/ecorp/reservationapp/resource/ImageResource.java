package de.tum.ecorp.reservationapp.resource;

import android.graphics.Bitmap;

import java.util.List;

public interface ImageResource {

    public List<Bitmap> loadImagesForRestaurant(long restaurantId);

}
