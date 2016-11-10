package de.tum.ecorp.reservationapp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import de.tum.ecorp.reservationapp.R;
import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.service.UserManager;

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Restaurant[] restaurants;
    private Context mContext;

    public RestaurantAdapter(Restaurant [] restaurants, Context context) {
        this.mContext = context;
        this.restaurants = restaurants;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Restaurant restaurant = restaurants[position];
        RestaurantViewHolder viewHolder = (RestaurantViewHolder)holder;
        viewHolder.restaurantNameLabel.setText(restaurant.getName());
        viewHolder.restaurantCategoryLabel.setText(restaurant.getCategory());
        viewHolder.ratingBar.setRating(restaurant.getRating());
        viewHolder.priceRangeLabel.setText(ViewUtility.formatPriceRange(restaurant.getPriceRange()));
        viewHolder.distanceLabel.setText(ViewUtility.formatDistance(
                restaurant.getLocation().distanceTo(UserManager.getInstance().getCurrentLocation())));
        viewHolder.reviewAmountLabel.setText(mContext.getString(
                R.string.review_amount_label, restaurant.getNumberOfReviews()));

    }

    public Restaurant getRestaurantAtPosition(int position) {
        if (position >= restaurants.length || position < 0) {
            return null;
        }
        return restaurants[position];
    }

    public void updateRestaurantList(Restaurant [] restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    public Restaurant [] getCurrentRestaurants() {
        return restaurants;
    }

    @Override
    public int getItemCount() {
        return restaurants.length;
    }


    private static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantNameLabel;
        TextView restaurantCategoryLabel;
        TextView reviewAmountLabel;
        TextView priceRangeLabel;
        TextView distanceLabel;
        RatingBar ratingBar;


        RestaurantViewHolder(View view) {
            super(view);

            restaurantNameLabel = (TextView) view.findViewById(R.id.restaurantNameLabel);
            restaurantCategoryLabel = (TextView) view.findViewById(R.id.restaurantTypeLabel);
            reviewAmountLabel = (TextView) view.findViewById(R.id.restaurantReviewAmountLabel);
            priceRangeLabel = (TextView) view.findViewById(R.id.restaurantPriceRangeLabel);
            distanceLabel = (TextView) view.findViewById(R.id.restaurantDistanceLabel);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        }
    }
}
