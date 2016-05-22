package de.tum.ecorp.reservationapp.model;

public class Review extends Entity {
    private String reviewText;
    private float rating;

    public Review(String reviewText, float rating) {
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public float getRating() {
        return rating;
    }
}
