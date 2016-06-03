package de.tum.ecorp.reservationapp.model;

import android.location.Location;

import java.util.ArrayList;
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
    private String address;
    private String website;

    private PriceRange priceRange;
    private Location location;

    private List<Review> reviews;
    private List<Table> tables;
    private OpeningTimes openingTimes;

    public Restaurant(String name, String category, String address, String website, PriceRange priceRange, Location location,
                      List<Review> reviews, List<Table> tables, OpeningTimes openingTimes) {

        this.name = name;
        this.category = category;
        this.address = address;
        this.website = website;

        this.priceRange = priceRange;
        this.location = location;

        this.reviews = reviews;
        this.tables = tables;
        this.openingTimes = openingTimes;
    }

    public Restaurant(String name, String category, String address, String website, PriceRange priceRange, Location location) {
        this.name = name;
        this.category = category;
        this.address = address;
        this.website = website;

        this.priceRange = priceRange;
        this.location = location;

        this.reviews = new ArrayList<Review>();
        this.tables = new ArrayList<Table>();
        this.openingTimes = new OpeningTimes();
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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite() {
        this.website = website;
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

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }

    public OpeningTimes getOpeningTimes() {
        return this.openingTimes;
    }

    public List<Table> getTables() {
        return this.tables;
    }
}
