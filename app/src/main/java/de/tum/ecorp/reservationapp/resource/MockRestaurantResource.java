package de.tum.ecorp.reservationapp.resource;

import android.location.Location;

import java.util.GregorianCalendar;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Calendar;
import java.util.Date;

import de.tum.ecorp.reservationapp.model.OpeningTimes;
import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.model.Review;
import de.tum.ecorp.reservationapp.model.Table;
import de.tum.ecorp.reservationapp.model.TimeSlot;

public class MockRestaurantResource implements RestaurantResource {

    private Map<Integer, Restaurant> restaurants;

    public MockRestaurantResource() {
        restaurants = new HashMap<>();

        List<Restaurant> newRestaurants = createMockRestaurants();

        for (Restaurant restaurant : newRestaurants) {
            restaurants.put(restaurant.getId(), restaurant);
        }
    }

    @Override
    public List<Restaurant> getRestaurants() {
        return new ArrayList<>(this.restaurants.values());
    }

    @Override
    public List<Restaurant> getRestaurantsFiltered(Map<Filter, Object> filters) {

        List<Restaurant> result = new ArrayList<>(this.restaurants.values());

        for (Iterator<Restaurant> iterator = result.iterator(); iterator.hasNext(); ) {
            Restaurant restaurant = iterator.next();
            if (isFilteredOut(restaurant, filters)) {
                iterator.remove();
            }
        }

        return result;
    }

    @Override
    public List<Restaurant> getRestaurantsBySearchString(final String searchString) {

        Map<Filter, Object> filters = new HashMap<>();
        filters.put(Filter.RESTAURANT_NAME, searchString);

        return getRestaurantsFiltered(filters);
    }

    @Override
    public List<Restaurant> getRestaurantsNearby(Location searchLocation, Double searchRadius) {

        Map<Filter, Object> filters = new HashMap<>();
        filters.put(Filter.SEARCH_LOCATION, searchLocation);
        filters.put(Filter.SEARCH_RADIUS, searchRadius);

        return getRestaurantsFiltered(filters);
    }

    @Override
    public Restaurant getRestaurant(Integer restaurantId) {
        return this.restaurants.get(restaurantId);
    }

    private List<Restaurant> createMockRestaurants() {

        List<Restaurant> result = new ArrayList<>();
        GregorianCalendar gc;
        List<Review> reviews;
        Location location;
        List<Table> tables;
        OpeningTimes openingTimes;
        String imageUris [];

        // RESTAURANT 1
        location = new Location("dummyProvider");
        location.setLatitude(48.130350972491556);
        location.setLongitude(11.561393737792969);

        reviews = new ArrayList<>();
        reviews.add(new Review("Trololo, bad waiters", 2, new Date()));
        reviews.add(new Review("This weeks' product owner sucks :)", 4, new Date()));

        tables = new ArrayList<>();
        tables.add(new Table(4));  // 1 table for 4
        tables.add(new Table(6));  // 1 table for 6

        openingTimes = new OpeningTimes();
        // This restaurant is opened from X to Y on mondays
        openingTimes.addTimeSlot(Calendar.MONDAY, new TimeSlot(13, 0));  //13.00-13.30
        // ...and half an hour on saturdays
        openingTimes.addTimeSlot(Calendar.SATURDAY, new TimeSlot(20, 0));  //20.00-20.30

        imageUris = new String[2];
        imageUris[0] = "https://www.omnihotels.com/-/media/images/hotels/homrst/restaurants/homrst-omni-homestead-resort-casino-restaurant.jpg";
        imageUris[1] = "http://lxly7dz9m3-flywheel.netdna-ssl.com/wp-content/uploads/2015/06/2hawks.jpg";

        result.add(new Restaurant(1,"ECorp creepy restaurant", "Nerdy restaurant",
                "Creepway 3, 80932 Munich", "www.ecorp.com",
                Restaurant.PriceRange.HIGH, location, reviews, tables, openingTimes, imageUris));

        // RESTAURANT 2
        location = new Location("dummyProvider");
        location.setLatitude(48.13825869999999);
        location.setLongitude(11.578163000000018);

        reviews = new ArrayList<>();
        reviews.add(new Review("Best restaurant ever", 5, new Date()));
        reviews.add(new Review("Cookies! Om nom nom..", 4, new Date()));

        tables = new ArrayList<>();
        tables.add(new Table(4));  // 1 table for 4
        tables.add(new Table(6));  // 1 table for 6

        openingTimes = new OpeningTimes();
        // This restaurant is opened from X to Y on mondays
        openingTimes.addTimeSlot(Calendar.MONDAY, new TimeSlot(13, 0));  //13.00-13.30
        // ...and half an hour on saturdays
        openingTimes.addTimeSlot(Calendar.SATURDAY, new TimeSlot(20, 0));  //20.00-20.30

        imageUris = new String[2];
        imageUris[0] = "http://kingofwallpapers.com/restaurant/restaurant-010.jpg";
        imageUris[1] = "https://upload.wikimedia.org/wikipedia/commons/1/1e/Tom's_Restaurant,_NYC.jpg";

        result.add(new Restaurant(2,"America Graffiti", "American Diner restaurant",
                "SomeRandomStreet 25, 666 Gotham, World", "www.inyourface.org",
                Restaurant.PriceRange.LOW, location, reviews, tables, openingTimes, imageUris));

        // RESTAURANT 3
        location = new Location("dummyProvider");
        //location.setLatitude(48.1390143);
        //location.setLongitude(11.5541695);
        location.setLatitude(48.1369415);
        location.setLongitude(11.573697);

        reviews = new ArrayList<>();
        reviews.add(new Review("As a mexican, this is the worst mexican food I have ever taste in my life. The burrito look more like a Calzone. Not coming back, adios amigos!", 1, new Date()));
        reviews.add(new Review("I liked the place.  Taste of food was good.", 4, new Date()));

        tables = new ArrayList<>();
        tables.add(new Table(4));  // 1 table for 4
        tables.add(new Table(4));  // another table for 4
        tables.add(new Table(10)); // 1 table for 10

        openingTimes = new OpeningTimes();
        // This restaurant is opened from X to Y on mondays
        openingTimes.addTimeSlot(Calendar.MONDAY, new TimeSlot(13, 0));  //13.00-13.30
        openingTimes.addTimeSlot(Calendar.MONDAY, new TimeSlot(13, 30)); //13.30-14.00
        openingTimes.addTimeSlot(Calendar.MONDAY, new TimeSlot(14, 0));  //14.00-14.30
        openingTimes.addTimeSlot(Calendar.MONDAY, new TimeSlot(14, 30)); //14.30-15.00
        // ...and half an hour on saturdays
        openingTimes.addTimeSlot(Calendar.SATURDAY, new TimeSlot(20, 0));  //20.00-20.30

        imageUris = new String[2];
        imageUris[0] = "http://newyamya.indyco.net/admin/uploaded_image/11037_P1010505.JPG";
        imageUris[1] = "http://restaurant-la-cucaracha-mex-bar.mux.de/images/1500x1200z/client/59228/86d6ecu4pv35/restaurant-bar-la-cucaracha-mex-bar-7.jpg";

        result.add(new Restaurant(3,"La Cucaracha", "Tex Mex Restaurant, Mexican Restaurant",
                "Bayerstraße 49, 80335 München, Deutschland", "http://www.la-cucaracha-muenchen.de/",
                Restaurant.PriceRange.MEDIUM, location, reviews, tables, openingTimes, imageUris));

        // RESTAURANT 4: Thai Magie
        location = new Location("dummyProvider");
        location.setLatitude(48.1351017);
        location.setLongitude(11.5752915);

        gc = new GregorianCalendar();
        reviews = new ArrayList<>();
        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 3, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));
        gc.set(2015, 4, 13);
        reviews.add(new Review("One of the best sushi places for this small amount of money. Awesome!", 5, gc.getTime()));

        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 5, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));
        gc.set(2015, 4, 13);
        reviews.add(new Review("One of the best sushi places for this small amount of money. Awesome!", 5, gc.getTime()));

        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 4, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));
        gc.set(2015, 4, 13);
        reviews.add(new Review("One of the best sushi places for this small amount of money. Awesome!", 5, gc.getTime()));

        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 4, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));
        gc.set(2015, 4, 13);
        reviews.add(new Review("One of the best sushi places for this small amount of money. Awesome!", 5, gc.getTime()));

        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 4, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));
        gc.set(2015, 4, 13);
        reviews.add(new Review("One of the best sushi places for this small amount of money. Awesome!", 5, gc.getTime()));

        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 5, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));
        gc.set(2015, 4, 13);
        reviews.add(new Review("One of the best sushi places for this small amount of money. Awesome!", 5, gc.getTime()));

        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 4, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));
        gc.set(2015, 4, 13);
        reviews.add(new Review("One of the best sushi places for this small amount of money. Awesome!", 5, gc.getTime()));

        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 4, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));
        gc.set(2015, 4, 13);
        reviews.add(new Review("One of the best sushi places for this small amount of money. Awesome!", 5, gc.getTime()));

        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 3, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));
        gc.set(2015, 4, 13);
        reviews.add(new Review("One of the best sushi places for this small amount of money. Awesome!", 5, gc.getTime()));

        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 2, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));
        gc.set(2015, 4, 13);
        reviews.add(new Review("One of the best sushi places for this small amount of money. Awesome!", 5, gc.getTime()));

        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 5, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));
        gc.set(2015, 4, 13);
        reviews.add(new Review("One of the best sushi places for this small amount of money. Awesome!", 5, gc.getTime()));

        gc.set(2016, 6, 25);
        reviews.add(new Review("It was ok. Not the best, but also not the worst. The only thing I didnt like was a quite weird waiter. He was kind of depressed or something. Didn't smile even once :)", 2, gc.getTime()));
        gc.set(2012, 8, 19);
        reviews.add(new Review("I was in dire need of some quality Thai food, and Sushi Magie came through in spades with a delicious plate of chicken Pad Thai. It gets packed (and cramped) at lunch, but that's how you know it's a good place!", 4, gc.getTime()));
        gc.set(2014, 8, 21);
        reviews.add(new Review("We found this place as we were walking around the city centre looking for sushi on a Sunday afternoon. Most of the places we would normally go to were shut and we ended up here. This is a place similar to a couple of others we have been to that serve thai and Japanese under one roof. This is the only country where I have seen this combo.", 4, gc.getTime()));

        tables = new ArrayList<>();
        tables.add(new Table(5));
        tables.add(new Table(5));
        tables.add(new Table(5));
        tables.add(new Table(4));  // 1 table for 4
        tables.add(new Table(4));  // another table for 4
        tables.add(new Table(4));  // another table for 4
        tables.add(new Table(4));  // another table for 4
        tables.add(new Table(4));  // another table for 4
        tables.add(new Table(4));  // another table for 4
        tables.add(new Table(4));  // another table for 4
        tables.add(new Table(4));  // another table for 4
        tables.add(new Table(6));  // table for 6
        tables.add(new Table(6));  // table for 6
        tables.add(new Table(6));  // table for 6
        tables.add(new Table(6));  // table for 6
        tables.add(new Table(6));  // table for 6
        tables.add(new Table(6));  // table for 6
        tables.add(new Table(6));  // table for 6
        tables.add(new Table(10)); // table for 10
        tables.add(new Table(10)); // table for 10
        tables.add(new Table(10)); // table for 10
        tables.add(new Table(10)); // table for 10

        openingTimes = new OpeningTimes();
        // This restaurant is opened from X to Y on mondays
        openingTimes.addTimeSlot(Calendar.MONDAY, new TimeSlot(17, 0));  //13.00-13.30
        openingTimes.addTimeSlot(Calendar.MONDAY, new TimeSlot(17, 30)); //13.30-14.00
        openingTimes.addTimeSlot(Calendar.MONDAY, new TimeSlot(18, 0));  //14.00-14.30
        openingTimes.addTimeSlot(Calendar.MONDAY, new TimeSlot(18, 30)); //14.30-15.00
        // ...and half an hour on saturdays
        openingTimes.addTimeSlot(Calendar.SATURDAY, new TimeSlot(20, 0));  //20.00-20.30

        imageUris = new String[3];
        imageUris[0] = "https://s3-media1.fl.yelpcdn.com/bphoto/WlrByfl-hdvY1DPEU-ztTQ/o.jpg";
        imageUris[1] = "https://s3-media4.fl.yelpcdn.com/bphoto/Oqb8tj-Y3tjmS8WIfggJNQ/o.jpg";
        imageUris[1] = "https://s3-media4.fl.yelpcdn.com/bphoto/4DLKxX9yGq2iPaflR2brlQ/o.jpg";

        result.add(new Restaurant(4,"Thai Magie", "Sushi, Asian Restaurant, Thai",
                "Westenriederstr. 13, 80331 Munich, Germany", "https://www.facebook.com/pages/Sushi-Magie/102082396551506",
                Restaurant.PriceRange.MEDIUM, location, reviews, tables, openingTimes, imageUris));

        // RESTAURANT 5: Sasou
        location = new Location("dummyProvider");
        location.setLatitude(48.1369325);
        location.setLongitude(11.573837);

        gc = new GregorianCalendar();
        reviews = new ArrayList<>();
        gc.set(2016, 6, 25);
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));

        tables = new ArrayList<>();
        openingTimes = new OpeningTimes();
        imageUris = new String[0];

        result.add(new Restaurant(5,"Sasou", "Japanese/Asian Restaurant",
                "Marienplatz 28, 80331 Munich, Germany", "https://www.facebook.com/pages/Sushi-Magie/102082396551506",
                Restaurant.PriceRange.MEDIUM, location, reviews, tables, openingTimes, imageUris));

        // RESTAURANT 6: China Moon Roof Terrace
        location = new Location("dummyProvider");
        location.setLatitude(48.136998);
        location.setLongitude(11.5726328);

        gc = new GregorianCalendar();
        reviews = new ArrayList<>();
        gc.set(2016, 6, 25);
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));

        tables = new ArrayList<>();
        openingTimes = new OpeningTimes();
        imageUris = new String[0];

        result.add(new Restaurant(6,"China Moon Roof Terrace", "Lounge, Asian Restaurant",
                "Mandarin Oriental Hotel Neuturmstr. 1, 80331 Munich, Germany", "https://www.facebook.com/pages/Sushi-Magie/102082396551506",
                Restaurant.PriceRange.HIGH, location, reviews, tables, openingTimes, imageUris));

        // RESTAURANT 7: Yum2take
        location = new Location("dummyProvider");
        location.setLatitude(48.1346182);
        location.setLongitude(11.5720964);

        gc = new GregorianCalendar();
        reviews = new ArrayList<>();
        gc.set(2016, 6, 25);

        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));

        tables = new ArrayList<>();
        openingTimes = new OpeningTimes();
        imageUris = new String[0];

        result.add(new Restaurant(7,"Yum2take", "Asian Fast Food, Thai Restaurant",
                "Sebastiansplatz 8, 80331 Munich, Germany", "https://www.facebook.com/pages/Sushi-Magie/102082396551506",
                Restaurant.PriceRange.LOW, location, reviews, tables, openingTimes, imageUris));

        // RESTAURANT 8: Ocui
        location = new Location("dummyProvider");
        location.setLatitude(48.1342616);
        location.setLongitude(11.5685944);

        gc = new GregorianCalendar();
        reviews = new ArrayList<>();
        gc.set(2016, 6, 25);
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));

        tables = new ArrayList<>();
        openingTimes = new OpeningTimes();
        imageUris = new String[0];

        result.add(new Restaurant(8,"Ocui", "Italian/Asian Restaurant",
                "Oberanger 31-33, 80331 Munich, Germany", "https://www.facebook.com/pages/Sushi-Magie/102082396551506",
                Restaurant.PriceRange.MEDIUM, location, reviews, tables, openingTimes, imageUris));

        // RESTAURANT 9: Kaiden Asia
        location = new Location("dummyProvider");
        location.setLatitude(48.1375124);
        location.setLongitude(11.5679561);

        gc = new GregorianCalendar();
        reviews = new ArrayList<>();
        gc.set(2016, 6, 25);
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));

        tables = new ArrayList<>();
        openingTimes = new OpeningTimes();
        imageUris = new String[0];

        result.add(new Restaurant(9,"Kaiden Asia", "Japanese/Chinese/Asian Restaurant, Sushi",
                "Altheimer Eck 12, 80331 Munich, Germany", "https://www.facebook.com/pages/Sushi-Magie/102082396551506",
                Restaurant.PriceRange.MEDIUM, location, reviews, tables, openingTimes, imageUris));

        // RESTAURANT 10: Hofbräuhaus
        location = new Location("dummyProvider");
        location.setLatitude(48.1369345);
        location.setLongitude(11.573817);
        //location.setLatitude(48.1369325);
        //location.setLongitude(11.573837);

        gc = new GregorianCalendar();
        reviews = new ArrayList<>();
        gc.set(2016, 6, 25);
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 4, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 4, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 4, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 4, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 4, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 4, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 4, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 4, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 4, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));

        tables = new ArrayList<>();
        openingTimes = new OpeningTimes();
        imageUris = new String[0];

        result.add(new Restaurant(10,"Hofbräuhaus", "German Restaurant, Bavarian Cuisine, Beergarden",
                "Platzl 9, 80331 Munich, Germany", "https://www.facebook.com/pages/Sushi-Magie/102082396551506",
                Restaurant.PriceRange.MEDIUM, location, reviews, tables, openingTimes, imageUris));


        // RESTAURANT 11: Zwickl
        location = new Location("dummyProvider");
        location.setLatitude(48.1369315);
        location.setLongitude(11.573897);
        //location.setLatitude(48.1369325);
        //location.setLongitude(11.573837);

        gc = new GregorianCalendar();
        reviews = new ArrayList<>();
        gc.set(2016, 6, 25);

        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 5, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));
        reviews.add(new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin mattis malesuada porta.", 3, gc.getTime()));

        tables = new ArrayList<>();
        openingTimes = new OpeningTimes();
        imageUris = new String[0];

        result.add(new Restaurant(11,"Zwickl", "Bavarian Cuisine, Bar",
                "Dreifaltigkeitsplatz 2, 80331 Munich, Germany", "https://www.facebook.com/pages/Sushi-Magie/102082396551506",
                Restaurant.PriceRange.MEDIUM, location, reviews, tables, openingTimes, imageUris));

        // RESTAURANT 11: Test 0 reviews
        location = new Location("dummyProvider");
        location.setLatitude(49.1369315);
        location.setLongitude(12.573897);

        gc = new GregorianCalendar();
        reviews = new ArrayList<>();
        tables = new ArrayList<>();
        openingTimes = new OpeningTimes();
        imageUris = new String[0];

        result.add(new Restaurant(12,"McDonalds", "Fast food",
                "somewhere", "https://www.example.com",
                Restaurant.PriceRange.MEDIUM, location, reviews, tables, openingTimes, imageUris));

        return result;
    }

    private boolean isFilteredOut(Restaurant restaurant, Map<Filter, Object> filters) {

        int timeSlots = -1;
        int startTime = -1;
        int endTime = -1;
        int numberOfVisitors = -1;

        Location searchLocation = null;
        double searchRadius = -1;

        for (Map.Entry<Filter, Object> entry : filters.entrySet()) {
            Object value = entry.getValue();

            switch (entry.getKey()) {
                case RESTAURANT_NAME:
                    String nameSearchString = (String) value;

                    if (! stringContainsString(restaurant.getName(), nameSearchString) && ! stringContainsString(restaurant.getCategory(), nameSearchString)) {
                        return true;
                    }
                    break;

                case RESTAURANT_CATEGORY:
                    String categorySearchString = (String) value;

                    if (! stringContainsString(restaurant.getCategory(), categorySearchString)) {
                        return true;
                    }
                    break;

                case PRICE_RANGE:
                    if (restaurant.getPriceRange().getNumberRepresentation() > (int) value) {
                        return true;
                    }
                    break;

                case AVERAGE_RATING:
                    if (restaurant.getRating() - (float) value < 0) {
                        return true;
                    }
                    break;

                case FREE_TIME_SLOTS:
                    timeSlots = (int) value;
                    if (! timeSlotsOkay(restaurant, timeSlots, startTime, endTime, numberOfVisitors)) {
                        return true;
                    }
                    break;

                case RESERVATION_START_TIME:
                    startTime = (int) value;
                    if (! timeSlotsOkay(restaurant, timeSlots, startTime, endTime, numberOfVisitors)) {
                        return true;
                    }
                    break;

                case RESERVATION_END_TIME:
                    endTime = (int) value;
                    if (! timeSlotsOkay(restaurant, timeSlots, startTime, endTime, numberOfVisitors)) {
                        return true;
                    }
                    break;

                case RESERVATION_GUESTS:
                    numberOfVisitors = (int) value;
                    if (! timeSlotsOkay(restaurant, timeSlots, startTime, endTime, numberOfVisitors)) {
                        return true;
                    }
                    break;

                case SEARCH_LOCATION:
                    searchLocation = (Location) value;
                    if (! locationOkay(restaurant, searchLocation, searchRadius)) {
                        return true;
                    }
                    break;

                case SEARCH_RADIUS:
                    searchRadius = (double) value;
                    if (! locationOkay(restaurant, searchLocation, searchRadius)) {
                        return true;
                    }
                    break;

                default:
                    //TODO: Log or sth?
            }

        }

        return false;
    }

    private boolean stringContainsString(String a, String b) {
        String normalizedA = a.toLowerCase().replaceAll("\\W", "");
        String normalizedB = b.toLowerCase().replaceAll("\\W", "");

        return normalizedA.contains(normalizedB);
    }

    private boolean timeSlotsOkay(Restaurant restaurant, int timeSlots, int startTime, int endTime, int numberOfVisitors ) {

        if (timeSlots == -1 || startTime == -1 || endTime == -1 || numberOfVisitors == -1) {
            // Not all fields have been filled. Keep going.
            return true;
        }

        //TODO: Implement time slot logic

        return true;
    }

    private boolean locationOkay(Restaurant restaurant, Location searchLocation, double searchRadius) {

        if (searchLocation == null || searchRadius == -1) {
            // Not all fields have been filled. Keep going.
            return true;
        }

        if (searchLocation.distanceTo(restaurant.getLocation()) > searchRadius) {
            return false;
        }

        return true;
    }

}
