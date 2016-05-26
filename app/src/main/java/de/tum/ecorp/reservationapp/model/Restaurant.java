package de.tum.ecorp.reservationapp.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

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

    public Restaurant(String name, String category, PriceRange priceRange, List<Review> reviews, Location location) {
        this.name = name;
        this.category = category;
        this.reviews = (reviews != null) ? reviews : new ArrayList<Review>();
        this.priceRange = priceRange;
        this.location = location;
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

    public int getNumerOfReviews() {
        return this.reviews.size();
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }
}
