package de.tum.ecorp.reservationapp.view;

import android.content.Context;
import android.media.Image;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

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
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ViewGroup expandableContainer;
    private boolean bOpeningHoursExpanded = false;

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
        View rootView = inflater.inflate(R.layout.fragment_restaurant_det, container, false);
        if (mRestaurant != null) {
            TextView name = (TextView)rootView.findViewById(R.id.restaurantDetailName);
            TextView ratingNum = (TextView)rootView.findViewById(R.id.restaurantDetailRatingNum);
            RatingBar ratingBar = (RatingBar)rootView.findViewById(R.id.restaurantRatingBar);
            TextView reviewAmount = (TextView)rootView.findViewById(R.id.restaurantDetailReviewAmount);
            TextView category = (TextView)rootView.findViewById(R.id.restaurantDetailCategory);
            TextView price = (TextView)rootView.findViewById(R.id.restaurantDetailPrice);
            TextView address = (TextView)rootView.findViewById(R.id.restaurantDetailAddress);
            TextView website = (TextView)rootView.findViewById(R.id.restaurantDetailWebsite);
            expandableContainer = (ViewGroup)rootView.findViewById(R.id.expandable_container);

            //Settings values
            /*for (String img: mRestaurant.getImageUris()) {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageLoader.displayImage(img, imageView);
            }*/
            name.setText(mRestaurant.getName());
            ratingNum.setText(ViewUtility.formatFloat(mRestaurant.getRating(),1));
            ratingBar.setRating(mRestaurant.getRating());
            reviewAmount.setText(getActivity().getString(R.string.review_amount_label, mRestaurant.getNumerOfReviews()));
            category.setText(mRestaurant.getCategory());
            price.setText(ViewUtility.formatPriceRange(mRestaurant.getPriceRange()));
            address.setText(mRestaurant.getAddress());
            website.setText(mRestaurant.getWebsite());
            if (bOpeningHoursExpanded) {
                inflater.inflate(R.layout.expanded_opening_hours, expandableContainer);
                ListView openingHoursList = (ListView)rootView.findViewById(R.id.opening_times_list);
                OpeningHoursArrayAdapter adapter = new OpeningHoursArrayAdapter(getContext(),
                        R.layout.opening_hours_listitem);
                //Creating mock values
                List<String> openingTimes = new ArrayList<>();
                openingTimes.add("12:00 PM-3:00 PM");
                openingTimes.add("5:00 PM-10:00 PM");
                OpeningTime time = new OpeningTime(0, null);
                adapter.add(time);
                time = new OpeningTime(1, openingTimes);
                adapter.add(time);
                time = new OpeningTime(2, openingTimes);
                adapter.add(time);
                time = new OpeningTime(3, openingTimes);
                adapter.add(time);
                time = new OpeningTime(4, openingTimes);
                adapter.add(time);
                time = new OpeningTime(5, openingTimes);
                adapter.add(time);
                time = new OpeningTime(6, openingTimes);
                adapter.add(time);
                openingHoursList.setAdapter(adapter);
            } else {
                inflater.inflate(R.layout.collapsed_opening_hours, expandableContainer);

            }
            expandableContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater =
                            (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    if (expandableContainer.findViewById(R.id.expandedContainer) != null) {
                        expandableContainer.removeAllViews();
                        inflater.inflate(R.layout.collapsed_opening_hours, expandableContainer);
                    } else if (expandableContainer.findViewById(R.id.collapsedContainer) != null) {
                        expandableContainer.removeAllViews();
                        inflater.inflate(R.layout.expanded_opening_hours, expandableContainer);
                        //TODO: this probably doesn't work, cause the view is being added but not initialized
                    }
                }
            });
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

    private void initializeExpandedOpeningHours() {

    }

    private void initializeCollapsedOpeningHours() {

    }
}
