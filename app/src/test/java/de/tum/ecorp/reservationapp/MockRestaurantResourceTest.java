package de.tum.ecorp.reservationapp;

import org.junit.BeforeClass;
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

    /* These tests do not work. Why? Because AsyncTask s are a pain to test.
       See http://stackoverflow.com/questions/9774792/asynctask-onpostexecute-not-called-in-unit-test-case

       An alternative would be to change the stuff to a Service. Let's think about that.
     */

//    @Test
//    public void get_all_restaurants() throws Exception {
//
//        mrr.getRestaurants(new Task<List<Restaurant>>() {
//            @Override
//            public void before() {
//                //
//            }
//
//            @Override
//            public void handleResult(List<Restaurant> result) {
//                assertEquals(result.size(), 3);
//            }
//        });
//    }

//    @Test
//    public void get_restaurant_filtered_by_name() throws Exception {
//
//        final CountDownLatch lock = new CountDownLatch(1);
//
//        mrr.getRestaurantsBySearchString(new Task<List<Restaurant>>() {
//            @Override
//            public void before() {
//                //
//            }
//
//            @Override
//            public void handleResult(List<Restaurant> result) {
//
//                assertNotNull(result);
//                assertEquals(result.size(), 1);
//                lock.countDown();
//            }
//
//        }, "ecorp");
//
//        lock.await();
//    }
}
