package de.tum.ecorp.reservationapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.resource.MockImageResource;
import de.tum.ecorp.reservationapp.resource.MockRestaurantResourceAsync;
import de.tum.ecorp.reservationapp.resource.Task;
import de.tum.ecorp.reservationapp.service.LocationAware;
import de.tum.ecorp.reservationapp.service.UserManager;
import de.tum.ecorp.reservationapp.view.*;

public class MainActivity extends AppCompatActivity implements LocationAware {
    private static final int MAX_DISPLAYED_RESULTS = 50;

    //private ListView restaurantListView;
    private RecyclerView restaurantListView;
    private RestaurantAdapter restaurantAdapter;
    private LocationManager locationManager;
    private UserManager userManager = UserManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userManager.canGetLocation()) {
                    Location currentLocation = userManager.getCurrentLocation();
                    final double latitude = currentLocation.getLatitude();
                    final double longitude = currentLocation.getLongitude();

                    Snackbar.make(view, latitude + " - " + longitude, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    showSettingsAlert();
                }
            }
        });


        restaurantListView = (RecyclerView) findViewById(R.id.restaurantList);
        restaurantListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        restaurantListView.addItemDecoration(new HorizontalDividerItemDecoration(this));

        //Creating adapter
        restaurantAdapter = new RestaurantAdapter(new Restaurant[0], this);

        restaurantListView.addOnItemTouchListener(new RecyclerViewClickListener(this, restaurantListView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Restaurant restaurant = restaurantAdapter.getRestaurantAtPosition(position);
                if (restaurant == null) {
                    return;
                }
                Context context = view.getContext();
                Intent intent = new Intent(context, RestaurantDetailsActivity.class);
                intent.putExtra(RestaurantDetailsActivity.ARG_RESTAURANT, restaurant);

                context.startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Do nothing
            }
        }));

        restaurantListView.setAdapter(restaurantAdapter);

        //Asynchronous call to populate the list
        populateListView();
    }

    private void populateListView() {
        new MockRestaurantResourceAsync().getRestaurantsAsync(new Task<List<Restaurant>>() {
            @Override
            public void before() {
                //Do nothing
            }

            @Override
            public void handleResult(List<Restaurant> result) {
                final Location searchLocation = UserManager.getInstance().getCurrentLocation();
                if (searchLocation != null) {
                    //Sorting results according to distance from search location
                    Collections.sort(result, new Comparator<Restaurant>() {
                        @Override
                        public int compare(Restaurant lhs, Restaurant rhs) {
                            return Float.compare(searchLocation.distanceTo(lhs.getLocation()),
                                    searchLocation.distanceTo(rhs.getLocation()));
                        }
                    });
                }
                //Only displaying the first N results, where N cannot be higher than the amount of results
                List<Restaurant> resultsToDisplay = result.subList(0, Math.min(result.size(), MAX_DISPLAYED_RESULTS));
                restaurantAdapter.updateRestaurantList(resultsToDisplay.toArray(new Restaurant[resultsToDisplay.size()]));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        userManager.stopUsingGPS();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userManager.enableLocationService(locationManager, Arrays.asList((LocationAware) this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will
     * launch Settings Options
     */
    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS settings");

        // Setting Dialog Message
        alertDialog
                .setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void updateLocation(Location location) {
        restaurantAdapter.notifyDataSetChanged();
    }
}
