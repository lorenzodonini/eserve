package de.tum.ecorp.reservationapp;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.model.TimeSlot;
import de.tum.ecorp.reservationapp.resource.MockImageResource;
import de.tum.ecorp.reservationapp.resource.Task;
import de.tum.ecorp.reservationapp.view.RestaurantDetailFragment;
import de.tum.ecorp.reservationapp.view.RestaurantReservationFragment;
import de.tum.ecorp.reservationapp.view.RestaurantReviewsFragment;

import java.util.List;

public class RestaurantDetailsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the main section contents.
     */
    private ViewPager mViewPager;

    /**
     * The {@link ImagePagerAdapter} that will provide the appropriate
     * image view, according to the current position.
     * This could become very memory intensive, in case there a lot
     * of images to load. Needs optimisation.
     */
    private ImagePagerAdapter mImagePagerAdapter;

    /**
     * The {@link ViewPager} that will host the images of the restaurant.
     */
    private ViewPager mImagePager;

    private Restaurant mRestaurant;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private static final String DETAILS_TAB = "Details";
    private static final String REVIEWS_TAB = "Reviews";
    private static final String RESERVATION_TAB = "Reservation";
    public static final String ARG_RESTAURANT = "restaurant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            if (arguments.containsKey(ARG_RESTAURANT)) {
                mRestaurant = (Restaurant) arguments.get(ARG_RESTAURANT);
            }
        }

        setContentView(R.layout.activity_restaurant_details);

        // Set toolbar as support action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize image loader
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        }

        // Pager used for displaying restaurant images
        mImagePagerAdapter = new ImagePagerAdapter(this);
        mImagePager = (ViewPager)findViewById(R.id.restaurant_image_pager);
        mImagePager.setAdapter(mImagePagerAdapter);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.details_content);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Set up the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.details_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Setting title on the Collapsing Toolbar
        if (mRestaurant != null) {
            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
            collapsingToolbar.setTitle(mRestaurant.getName());
        }
    }

    //TODO: Consider reusing only if the image URIs will actually be parsed here
    /*private void loadImagesForRestaurant() {
        new MockImageResource().getRestaurantImageUrisAsync(mRestaurant, new Task<List<String>>() {
            @Override
            public void before() {
                //Do nothing
            }

            @Override
            public void handleResult(List<String> result) {
                mRestaurant.setImageUris(result.toArray(new String[result.size()]));
                mImagePagerAdapter.notifyDataSetChanged();
            }
        });
    }*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return RestaurantDetailFragment.newInstance(mRestaurant);
                case 1:
                    return RestaurantReviewsFragment.newInstance(mRestaurant.getReviews(), mRestaurant.getRating());
                default:
                    return RestaurantReservationFragment.newInstance(mRestaurant);//mRestaurant.getTables(), mRestaurant.getOpeningTimes());
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return DETAILS_TAB;
                case 1:
                    return REVIEWS_TAB;
                case 2:
                    return RESERVATION_TAB;
            }
            return null;
        }
    }

    /**
     * A {@link ImagePagerAdapter} that returns a view corresponding to the currently
     * chosen section (there are as many sections as there are available images)
     */
    private class ImagePagerAdapter extends PagerAdapter {
        private Context mContext;
        private LayoutInflater mLayoutInflater;

        public ImagePagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return (mRestaurant != null) ? mRestaurant.getImageUris().length : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.image_pager, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.restaurant_image);
            imageLoader.displayImage(mRestaurant.getImageUris()[position], imageView);

            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout)object);
        }
    }
}
