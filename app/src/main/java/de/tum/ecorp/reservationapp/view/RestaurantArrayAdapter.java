package de.tum.ecorp.reservationapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

import de.tum.ecorp.reservationapp.R;
import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.service.UserManager;

public class RestaurantArrayAdapter extends ArrayAdapter<Restaurant> {
    private final Context context;
    //TODO: Decide if we want to support miles as well, then we need to use Locale information
    private final static String UNIT_KILOMETER = "km";
    private final static String UNIT_METER = "m";

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
            viewHolder.reviewAmountLabel = (TextView) convertView.findViewById(R.id.reviewAmountLabel);
            viewHolder.priceRangeLabel = (TextView) convertView.findViewById(R.id.priceRangeLabel);
            viewHolder.distanceLabel = (TextView) convertView.findViewById(R.id.distanceLabel);
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
            viewHolder.priceRangeLabel.setText(formatPriceRange(restaurant.getPriceRange()));
            viewHolder.distanceLabel.setText(formatDistance(
                    restaurant.getLocation().distanceTo(UserManager.getInstance().getCurrentLocation())));
            viewHolder.ratingBar.setRating(restaurant.getRating());
        }

        return convertView;
    }

    private String formatPriceRange(Restaurant.PriceRange priceRange) {
        Currency currency = Currency.getInstance(Locale.getDefault());

        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= priceRange.getNumberRepresentation(); i++) {
            result.append(currency.getSymbol());
        }

        return result.toString();
    }

    private String formatDistance(float distance) {
        StringBuilder result = new StringBuilder();
        if (distance < 1000) {
            result.append((int) distance);
            result.append(UNIT_METER);
        } else {
            double kilometerValue = distance / 1000;
            DecimalFormat oneDecForm = new DecimalFormat("#.#");
            oneDecForm.setRoundingMode(RoundingMode.FLOOR);
            result.append(oneDecForm.format(kilometerValue));
            result.append(UNIT_KILOMETER);
        }

        return result.toString();
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
