package de.tum.ecorp.reservationapp.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import de.tum.ecorp.reservationapp.R;
import de.tum.ecorp.reservationapp.model.OpeningTime;
import de.tum.ecorp.reservationapp.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestaurantDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RestaurantDetailFragment extends Fragment {
    public static final String ARG_RESTAURANT = "restaurant";

    private Restaurant mRestaurant;
    private ImageLoader imageLoader = ImageLoader.getInstance();

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
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
        //TODO: Get opening times from restaurant and display them properly, instead of default mocked data
        if (mRestaurant != null) {
            TextView name = (TextView)rootView.findViewById(R.id.restaurantDetailName);
            TextView ratingNum = (TextView)rootView.findViewById(R.id.restaurantDetailRatingNum);
            RatingBar ratingBar = (RatingBar)rootView.findViewById(R.id.restaurantRatingBar);
            TextView reviewAmount = (TextView)rootView.findViewById(R.id.restaurantDetailReviewAmount);
            TextView category = (TextView)rootView.findViewById(R.id.restaurantDetailCategory);
            TextView price = (TextView)rootView.findViewById(R.id.restaurantDetailPrice);
            TextView address = (TextView)rootView.findViewById(R.id.restaurantDetailAddress);
            TextView website = (TextView)rootView.findViewById(R.id.restaurantDetailWebsite);

            name.setText(mRestaurant.getName());
            ratingNum.setText(ViewUtility.formatFloat(mRestaurant.getRating(),1));
            ratingBar.setRating(mRestaurant.getRating());
            reviewAmount.setText(getActivity().getString(R.string.review_amount_label, mRestaurant.getNumberOfReviews()));
            category.setText(mRestaurant.getCategory());
            price.setText(ViewUtility.formatPriceRange(mRestaurant.getPriceRange()));
            address.setText(mRestaurant.getAddress());
            website.setText(mRestaurant.getWebsite());

            //Initializing mocked opening times
            OpeningTime [] openingTimes = new OpeningTime[7];
            OpeningTime time = new OpeningTime(0, null);
            openingTimes[0] = time;
            List<String> times = new ArrayList<>();
            times.add("12:00 PM-3:00 PM");
            times.add("5:00 PM-10:00 PM");
            time = new OpeningTime(1, times);
            openingTimes[1] = time;
            time = new OpeningTime(2, times);
            openingTimes[2] = time;
            time = new OpeningTime(3, times);
            openingTimes[3] = time;
            time = new OpeningTime(4, times);
            openingTimes[4] = time;
            time = new OpeningTime(5, times);
            openingTimes[5] = time;
            time = new OpeningTime(6, times);
            openingTimes[6] = time;

            setOpeningHoursForDay(rootView.findViewById(R.id.sundayHours), openingTimes[0]);
            setOpeningHoursForDay(rootView.findViewById(R.id.mondayHours), openingTimes[1]);
            setOpeningHoursForDay(rootView.findViewById(R.id.tuesdayHours), openingTimes[2]);
            setOpeningHoursForDay(rootView.findViewById(R.id.wednesdayHours), openingTimes[3]);
            setOpeningHoursForDay(rootView.findViewById(R.id.thursdayHours), openingTimes[4]);
            setOpeningHoursForDay(rootView.findViewById(R.id.fridayHours), openingTimes[5]);
            setOpeningHoursForDay(rootView.findViewById(R.id.saturdayHours), openingTimes[6]);
        }

        return rootView;
    }

    private void setOpeningHoursForDay(View dayView, OpeningTime openingTime) {
        TextView day = (TextView)dayView.findViewById(R.id.openingDayLabel);
        day.setText(openingTime.getWeekdayAsString());
        TextView hours = (TextView)dayView.findViewById(R.id.openingHoursLabel);
        hours.setText(openingTime.getConcatenatedOpeningTimes());
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
