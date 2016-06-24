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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.common.base.Function;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.resource.MockRestaurantResourceAsync;
import de.tum.ecorp.reservationapp.resource.Task;
import de.tum.ecorp.reservationapp.service.LocationAware;
import de.tum.ecorp.reservationapp.service.UserManager;
import de.tum.ecorp.reservationapp.view.RestaurantArrayAdapter;
import de.tum.ecorp.reservationapp.view.SearchViewController;

public class MainActivity extends AppCompatActivity implements LocationAware {
    private static final int MAX_DISPLAYED_RESULTS = 50;

    private ListView restaurantListView;
    private ArrayAdapter<Restaurant> listAdapter;
    private LocationManager locationManager;
    private UserManager userManager = UserManager.getInstance();
    private MockRestaurantResourceAsync restaurantResourceAsync = new MockRestaurantResourceAsync();
    private SearchViewController searchViewController;

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
                searchViewController.showSearchBar();
            }
        });

        restaurantListView = (ListView) findViewById(R.id.listView);
        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = (Restaurant) parent.getItemAtPosition(position);
                Context context = view.getContext();
                Intent intent = new Intent(context, RestaurantDetailsActivity.class);
                intent.putExtra(RestaurantDetailsActivity.ARG_RESTAURANT, restaurant);

                context.startActivity(intent);
            }
        });

        //Creating adapter
        listAdapter = new RestaurantArrayAdapter(this, R.layout.restaurant_list_item);
        //Asynchronous call to populate the list
        populateListView(listAdapter);
        restaurantListView.setAdapter(listAdapter);

        initialiseSearchView();
    }

    private void populateListView(final ArrayAdapter<Restaurant> listAdapter) {
        restaurantResourceAsync.getRestaurantsAsync(new Task<List<Restaurant>>() {
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
                listAdapter.clear();
                listAdapter.addAll(resultsToDisplay);
                listAdapter.notifyDataSetChanged();
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
        // hide the menu
        return false;
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

    private void initialiseSearchView() {
        View searchContainer = findViewById(R.id.search_container);
        EditText toolbarSearchView = (EditText) findViewById(R.id.search_view);
        ImageView searchClearButton = (ImageView) findViewById(R.id.search_clear);
        ImageView searchCloseButton = (ImageView) findViewById(R.id.search_close);

        searchViewController = new SearchViewController(searchContainer, searchClearButton, searchCloseButton, toolbarSearchView, new Function<String, Void>() {
            @Override
            public Void apply(String input) {
                restaurantResourceAsync.getRestaurantsBySearchStringAsync(new Task<List<Restaurant>>() {
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
                        listAdapter.clear();
                        listAdapter.addAll(resultsToDisplay);
                        listAdapter.notifyDataSetChanged();
                    }
                }, input);

                return null;
            }
        });

        searchViewController.initialiseSearchView();
    }


    @Override
    public void updateLocation(Location location) {
        populateListView(listAdapter);
    }

}
