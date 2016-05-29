package de.tum.ecorp.reservationapp.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Restaurant extends Entity {

    public enum PriceRange {
        LOW(1), MEDIUM(2), HIGH(3);

        int numberRepresentation;

        PriceRange(int numberRepresentation) {
            this.numberRepresentation = numberRepresentation;
        }

        public int getNumberRepresentation() {
            return numberRepresentation;
        }
    }

    private String name;
    private String category;
    private PriceRange priceRange;
    private List<Review> reviews;
    private Location location;
    private Set<Integer> freeTimeSlots;

    public Restaurant(String name, String category, PriceRange priceRange, List<Review> reviews, Location location, Set<Integer> freeTimeSlots) {
        this.name = name;
        this.category = category;
        this.reviews = (reviews != null) ? reviews : new ArrayList<Review>();
        this.priceRange = priceRange;
        this.location = location;
        this.freeTimeSlots = (freeTimeSlots != null) ? freeTimeSlots : new HashSet<Integer>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getRating() {
        if (reviews == null || reviews.isEmpty()) {
            return 0;
        }
        float rating = 0;
        for (Review r : reviews) {
            rating += r.getRating();
        }
        return rating / reviews.size();
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void addReviews(List<Review> reviews) {
        this.reviews.addAll(reviews);
    }

    public List<Review> getReviews() {
        return this.reviews;
    }

    public int getNumberOfReviews() {
        return this.reviews.size();
    }

    public void addFreeTimeSlot(Integer timeSlot) {
        this.freeTimeSlots.add(timeSlot);
    }

    public void addFreeTimeSlots(Set<Integer> timeSlots) {
        this.freeTimeSlots.addAll(timeSlots);
    }

    public Set<Integer> getFreeTimeSlots() {
        return this.freeTimeSlots;
    }

    public int getNumberOfFreeTimeSlots() {
        return this.freeTimeSlots.size();
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }
}
