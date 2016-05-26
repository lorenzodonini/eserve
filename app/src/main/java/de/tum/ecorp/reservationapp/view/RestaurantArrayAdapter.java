package de.tum.ecorp.reservationapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import de.tum.ecorp.reservationapp.R;
import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.model.UserManager;

public class RestaurantArrayAdapter extends ArrayAdapter<Restaurant> {
    private final Context context;

    public RestaurantArrayAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.restaurant_list_item, parent, false);

            viewHolder = new ViewHolder();
            //Getting subViews
            viewHolder.restaurantNameLabel = (TextView) convertView.findViewById(R.id.restaurantNameLabel);
            viewHolder.restaurantCategoryLabel = (TextView) convertView.findViewById(R.id.restaurantTypeLabel);
            viewHolder.reviewAmountLabel = (TextView) convertView.findViewById(R.id.restaurantReviewAmountLabel);
            viewHolder.priceRangeLabel = (TextView) convertView.findViewById(R.id.restaurantPriceRangeLabel);
            viewHolder.distanceLabel = (TextView) convertView.findViewById(R.id.restaurantDistanceLabel);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Restaurant restaurant = getItem(position);
        if (restaurant != null) {
            viewHolder.restaurantNameLabel.setText(restaurant.getName());
            viewHolder.restaurantCategoryLabel.setText(restaurant.getCategory());
            viewHolder.reviewAmountLabel.setText(
                    context.getString(R.string.review_amount_label, restaurant.getNumerOfReviews()));
            viewHolder.priceRangeLabel.setText(ViewUtility.formatPriceRange(restaurant.getPriceRange()));
            viewHolder.distanceLabel.setText(ViewUtility.formatDistance(
                    restaurant.getLocation().distanceTo(UserManager.getInstance().getCurrentLocation())));
            viewHolder.ratingBar.setRating(restaurant.getRating());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView restaurantNameLabel;
        TextView restaurantCategoryLabel;
        TextView reviewAmountLabel;
        TextView priceRangeLabel;
        TextView distanceLabel;
        RatingBar ratingBar;
    }
}
