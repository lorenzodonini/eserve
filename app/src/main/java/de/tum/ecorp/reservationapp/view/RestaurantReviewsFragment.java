package de.tum.ecorp.reservationapp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        View rootView = inflater.inflate(R.layout.fragment_restaurant_reviews, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.reviewsView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RestaurantReviewsAdapter(rating,
                reviews.toArray(new Review[reviews.size()]), getContext()));

        return rootView;
    }

}
