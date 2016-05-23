package de.tum.ecorp.reservationapp;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.model.UserManager;
import de.tum.ecorp.reservationapp.resource.MockRestaurantResource;
import de.tum.ecorp.reservationapp.resource.Task;
import de.tum.ecorp.reservationapp.view.RestaurantArrayAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView restaurantListView;
    private ArrayAdapter<Restaurant> listAdapter;
    private int maxDisplayedResults = 50; //TODO: should be set via settings somehow, and not statically

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        restaurantListView = (ListView) findViewById(R.id.listView);

        //Creating adapter
        listAdapter = new RestaurantArrayAdapter(this, R.layout.restaurant_list_item);

        populateListView(listAdapter);

        restaurantListView.setAdapter(listAdapter);
    }

    private void populateListView(final ArrayAdapter<Restaurant> listAdapter) {
        new MockRestaurantResource().getRestaurants(new Task<List<Restaurant>>() {
            @Override
            public void before() {
                listAdapter.clear();
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
                List<Restaurant> resultsToDisplay = result.subList(0, Math.min(result.size(), maxDisplayedResults));
                listAdapter.addAll(resultsToDisplay);
            }
        });
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
}
