package de.tum.ecorp.reservationapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.model.Review;
import de.tum.ecorp.reservationapp.view.RestaurantArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView restaurantListView;
    private ArrayAdapter<Restaurant> listAdapter;

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
        listAdapter = new RestaurantArrayAdapter(this, R.layout.restaurant_list_item, getMockRestaurants());
        //TODO: Add async mechanism to add restaurants (we are fetching them from a server)
        restaurantListView.setAdapter(listAdapter);
    }

    private List<Restaurant> getMockRestaurants() {
        //Mock values
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("Trololol, bad waiters", 2));
        reviews.add(new Review("This weeks' product owner sucks :)", 4));
        Restaurant sample1 = new Restaurant("ECorp creepy restaurant", "Nerdy restaurant",3.1f,reviews);
        reviews = new ArrayList<>();
        reviews.add(new Review("Best restaurant ever", 5));
        reviews.add(new Review("Cookies! Om nom nom..", 4));
        Restaurant sample2 = new Restaurant("America Graffiti", "American Diner restaurant",1.8f,reviews);

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(sample1);
        restaurants.add(sample2);
        return restaurants;
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
