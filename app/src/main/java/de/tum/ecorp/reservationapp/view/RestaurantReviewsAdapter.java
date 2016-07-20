package de.tum.ecorp.reservationapp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import de.tum.ecorp.reservationapp.R;
import de.tum.ecorp.reservationapp.model.Review;

public class RestaurantReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_VIEW_TYPE = 0;
    private static final int HEADER_VIEW_POSITION = 0;
    private static final int BODY_VIEW_TYPE = 1;
    private static final int HEADER_ELEMENT_COUNT = 1;
    private static int RATING_BAR_MAX_WIDTH = 180;

    private Review [] reviews;
    private float restaurantRating;
    private Context mContext;

    public RestaurantReviewsAdapter(float restaurantRating, Review [] reviews, Context context) {
        this.reviews = reviews;
        this.restaurantRating = restaurantRating;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER_VIEW_TYPE:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.reviews_header, parent, false));
            case BODY_VIEW_TYPE:
                return new ReviewViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.review_list_item, parent, false));
            default:
                return new ReviewViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.review_list_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == HEADER_VIEW_POSITION) {
            initializeHeader((HeaderViewHolder)holder);
        } else {
            Review review = reviews[position - HEADER_ELEMENT_COUNT];
            ReviewViewHolder reviewViewHolder = (ReviewViewHolder)holder;
            reviewViewHolder.reviewDateLabel.setText(DateFormat.format("MM/dd/yyyy", review.getReviewDate()));
            reviewViewHolder.reviewRating.setRating(review.getRating());
            reviewViewHolder.reviewText.setText(review.getReviewText());
        }
    }

    @Override
    public int getItemCount() {
        return reviews.length + HEADER_ELEMENT_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HEADER_VIEW_POSITION) {
            return HEADER_VIEW_TYPE;
        } else {
            return BODY_VIEW_TYPE;
        }
    }

    private void initializeHeader(HeaderViewHolder holder) {
        holder.restaurantRatingNum.setText(ViewUtility.formatFloat(restaurantRating, 1));
        holder.restaurantRating.setRating(restaurantRating);
        holder.restaurantRatingAmount.setText(mContext.getString(R.string.review_amount_label, reviews.length));

        int ratings [] = new int [5];
        for (Review review : reviews) {
            ratings[Math.round(review.getRating()) - 1]++;
        }

        RATING_BAR_MAX_WIDTH = holder.progressBar1.getLayoutParams().width;

        if (reviews.length == 0) {
            holder.progressBar1.getLayoutParams().width = 1;
            holder.progressBar2.getLayoutParams().width = 1;
            holder.progressBar3.getLayoutParams().width = 1;
            holder.progressBar4.getLayoutParams().width = 1;
            holder.progressBar5.getLayoutParams().width = 1;
        } else {
            holder.progressBar1.getLayoutParams().width = (ratings[0] == 0) ?
                    1 : Math.round((float)RATING_BAR_MAX_WIDTH * ((float) ratings[0] / (float) reviews.length));
            holder.progressBar2.getLayoutParams().width = (ratings[1] == 0) ?
                    1 : Math.round((float)RATING_BAR_MAX_WIDTH * ((float) ratings[1]) / (float) reviews.length);
            holder.progressBar3.getLayoutParams().width = (ratings[2] == 0) ?
                    1 : Math.round((float)RATING_BAR_MAX_WIDTH * ((float) ratings[2]) / (float) reviews.length);
            holder.progressBar4.getLayoutParams().width = (ratings[3] == 0) ?
                    1 : Math.round((float)RATING_BAR_MAX_WIDTH * ((float) ratings[3]) / (float) reviews.length);
            holder.progressBar5.getLayoutParams().width = (ratings[4] == 0) ?
                    1 : Math.round((float)RATING_BAR_MAX_WIDTH * ((float) ratings[4]) / (float) reviews.length);
        }

        holder.amount1.setText(Integer.toString(ratings[0]));
        holder.amount2.setText(Integer.toString(ratings[1]));
        holder.amount3.setText(Integer.toString(ratings[2]));
        holder.amount4.setText(Integer.toString(ratings[3]));
        holder.amount5.setText(Integer.toString(ratings[4]));
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView restaurantRatingNum;
        RatingBar restaurantRating;
        TextView restaurantRatingAmount;
        //5 stars
        RatingBar ratingBar5;
        ProgressBar progressBar5;
        TextView amount5;
        //4 stars
        RatingBar ratingBar4;
        ProgressBar progressBar4;
        TextView amount4;
        //3 stars
        RatingBar ratingBar3;
        ProgressBar progressBar3;
        TextView amount3;
        //2 stars
        RatingBar ratingBar2;
        ProgressBar progressBar2;
        TextView amount2;
        //1 star
        RatingBar ratingBar1;
        ProgressBar progressBar1;
        TextView amount1;

        HeaderViewHolder(View view) {
            super(view);
            restaurantRatingNum = (TextView) view.findViewById(R.id.restaurantRatingNum);
            restaurantRating = (RatingBar) view.findViewById(R.id.restaurantRatingBar);
            restaurantRatingAmount = (TextView) view.findViewById(R.id.restaurantReviewAmount);

            ratingBar1 = (RatingBar) view.findViewById(R.id.reviewStars1);
            ratingBar2 = (RatingBar) view.findViewById(R.id.reviewStars2);
            ratingBar3 = (RatingBar) view.findViewById(R.id.reviewStars3);
            ratingBar4 = (RatingBar) view.findViewById(R.id.reviewStars4);
            ratingBar5 = (RatingBar) view.findViewById(R.id.reviewStars5);

            progressBar1 = (ProgressBar) view.findViewById(R.id.reviewPercentage1);
            progressBar2 = (ProgressBar) view.findViewById(R.id.reviewPercentage2);
            progressBar3 = (ProgressBar) view.findViewById(R.id.reviewPercentage3);
            progressBar4 = (ProgressBar) view.findViewById(R.id.reviewPercentage4);
            progressBar5 = (ProgressBar) view.findViewById(R.id.reviewPercentage5);

            amount1 = (TextView) view.findViewById(R.id.reviewAmount1);
            amount2 = (TextView) view.findViewById(R.id.reviewAmount2);
            amount3 = (TextView) view.findViewById(R.id.reviewAmount3);
            amount4 = (TextView) view.findViewById(R.id.reviewAmount4);
            amount5 = (TextView) view.findViewById(R.id.reviewAmount5);
        }
    }

    private static class ReviewViewHolder extends RecyclerView.ViewHolder {
        RatingBar reviewRating;
        TextView reviewDateLabel;
        TextView reviewText;

        ReviewViewHolder(View view) {
            super(view);

            reviewRating = (RatingBar) view.findViewById(R.id.reviewRating);
            reviewDateLabel = (TextView) view.findViewById(R.id.reviewDateLabel);
            reviewText = (TextView) view.findViewById(R.id.reviewText);
        }
    }
}
