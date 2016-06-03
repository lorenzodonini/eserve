package de.tum.ecorp.reservationapp;

import android.location.Location;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.resource.MockRestaurantResource;
import de.tum.ecorp.reservationapp.resource.Task;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MockRestaurantResourceTest {

    static MockRestaurantResource mrr;

    @BeforeClass
    public static void setup() {
        mrr = new MockRestaurantResource();
    }

    @Test
    public void get_restaurants() throws Exception {
        assertEquals(mrr.getRestaurants().size(), 3);
    }

    @Test
    public void get_restaurants_by_search_string() throws Exception {
        assertEquals(mrr.getRestaurantsBySearchString("cuca").size(), 1);
        assertEquals(mrr.getRestaurantsBySearchString("ECORP").size(), 1);
        assertEquals(mrr.getRestaurantsBySearchString("ecorp").size(), 1);
        assertEquals(mrr.getRestaurantsBySearchString("ecORp").size(), 1);
    }

    @Ignore("Location is always <null> in JUnit...")
    @Test
    public void get_restaurants_nearby() throws Exception {
        Location location = new Location("testprovider");
        location.setLatitude(48.1390143);
        location.setLongitude(11.5541695);

        assertEquals(mrr.getRestaurantsNearby(location, 20).size(), 1);
        assertEquals(mrr.getRestaurantsNearby(location, 5000).size(), 3);
    }

    //TODO: Add test cases for getRestaurantsFiltered(Map<Filter, Object> filters);
}
