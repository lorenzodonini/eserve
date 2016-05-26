package de.tum.ecorp.reservationapp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.tum.ecorp.reservationapp.R;
import de.tum.ecorp.reservationapp.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantReviewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_REVIEWS = "restaurant_reviews";
    public static final String ARG_RATING = "avg_rating";

    // TODO: Rename and change types of parameters
    private List<Review> reviews;
    private float rating;


    public RestaurantReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param reviews Parameter 1.
     * @param rating Parameter 2.
     * @return A new instance of fragment RestaurantReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantReviewsFragment newInstance(ArrayList<Review> reviews, float rating) {
        RestaurantReviewsFragment fragment = new RestaurantReviewsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_REVIEWS, reviews);
        args.putFloat(ARG_RATING, rating);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.containsKey(ARG_REVIEWS)) {
                reviews = arguments.getParcelableArrayList(ARG_REVIEWS);
            }
            if (arguments.containsKey(ARG_RATING)) {
                rating = arguments.getFloat(ARG_RATING);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_reviews, container, false);
    }

}
