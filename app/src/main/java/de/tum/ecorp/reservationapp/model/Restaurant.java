package de.tum.ecorp.reservationapp.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String category;
    private float priceRange;
    private List<Review> reviewList;

    public Restaurant(String name, String category, float priceRange, List<Review> reviews) {
        this.name = name;
        this.category = category;
        this.reviewList = (reviews != null) ? reviews : new ArrayList<Review>();
        this.priceRange = priceRange;
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

    public float getRating() {
        if (reviewList == null || reviewList.isEmpty()) {
            return 0;
        }
        float rating = 0;
        for (Review r : reviewList) {
            rating += r.getRating();
        }
        return rating / reviewList.size();
    }

    public void addReview(Review review) {
        this.reviewList.add(review);
    }

    public void addReviews(List<Review> reviews) {
        this.reviewList.addAll(reviews);
    }

    public List<Review> getReviews() {
        return this.reviewList;
    }

    public float getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(float priceRange) {
        this.priceRange = priceRange;
    }
}
