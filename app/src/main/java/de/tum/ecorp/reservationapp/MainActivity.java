package de.tum.ecorp.reservationapp;

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

import java.util.List;

import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.resource.MockRestaurantResource;
import de.tum.ecorp.reservationapp.resource.Task;
import de.tum.ecorp.reservationapp.service.LocationService;
import de.tum.ecorp.reservationapp.view.RestaurantArrayAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView restaurantListView;
    private ArrayAdapter<Restaurant> listAdapter;
    private LocationService locationService;

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
                locationService = new LocationService(MainActivity.this);
                if (locationService.canGetLocation()) {
                    final double latitude = locationService.getLatitude();
                    final double longitude = locationService.getLongitude();

                    Snackbar.make(view, latitude + " - " + longitude, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    locationService.showSettingsAlert();
                }
            }
        });

        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

        //locationService = new LocationService(this);
    }


    private void populateListView(final ArrayAdapter<Restaurant> listAdapter) {
        new MockRestaurantResource().getRestaurants(new Task<List<Restaurant>>() {
            @Override
            public void before() {
                listAdapter.clear();
            }

            @Override
            public void handleResult(List<Restaurant> result) {
                listAdapter.addAll(result);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //locationService.connect();

        restaurantListView = (ListView) findViewById(R.id.listView);

        //Creating adapter
        listAdapter = new RestaurantArrayAdapter(this, R.layout.restaurant_list_item);

        populateListView(listAdapter);

        restaurantListView.setAdapter(listAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //locationService.disconnect();
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
