package de.tum.ecorp.reservationapp.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import de.tum.ecorp.reservationapp.R;
import de.tum.ecorp.reservationapp.model.Restaurant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestaurantDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RestaurantDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    public static final String ARG_RESTAURANT = "restaurant";

    private Restaurant mRestaurant;

    private OnFragmentInteractionListener mListener;

    public RestaurantDetailFragment() {
        // Required empty public constructor
    }

    public static RestaurantDetailFragment newInstance(Restaurant restaurant) {
        RestaurantDetailFragment fragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.containsKey(ARG_RESTAURANT)) {
                mRestaurant = (Restaurant) arguments.get(ARG_RESTAURANT);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
        if (mRestaurant != null) {
            TextView name = (TextView)rootView.findViewById(R.id.restaurantDetailName);
            TextView ratingNum = (TextView)rootView.findViewById(R.id.restaurantDetailRatingNum);
            RatingBar ratingBar = (RatingBar)rootView.findViewById(R.id.restaurantRatingBar);
            TextView reviewAmount = (TextView)rootView.findViewById(R.id.restaurantDetailReviewAmount);
            TextView category = (TextView)rootView.findViewById(R.id.restaurantDetailCategory);
            TextView price = (TextView)rootView.findViewById(R.id.restaurantDetailPrice);
            TextView address = (TextView)rootView.findViewById(R.id.restaurantDetailAddress);
            TextView website = (TextView)rootView.findViewById(R.id.restaurantDetailWebsite);
            TextView openingTimes = (TextView)rootView.findViewById(R.id.restaurantDetailOpeningTimes);

            name.setText(mRestaurant.getName());
            ratingNum.setText(ViewUtility.formatFloat(mRestaurant.getRating(),1));
            ratingBar.setRating(mRestaurant.getRating());
            reviewAmount.setText(getActivity().getString(R.string.review_amount_label, mRestaurant.getNumerOfReviews()));
            category.setText(mRestaurant.getCategory());
            price.setText(ViewUtility.formatPriceRange(mRestaurant.getPriceRange()));
            address.setText(mRestaurant.getAddress());
            website.setText(mRestaurant.getWebsite());
            //TODO: implement opening times correctly
            openingTimes.setText("Closed today!");
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
