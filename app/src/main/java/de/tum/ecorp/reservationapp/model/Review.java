package de.tum.ecorp.reservationapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Review extends Entity implements Parcelable {
    private String reviewText;
    private float rating;
    private Date reviewDate;

    public Review(String reviewText, float rating, Date reviewDate) {
        this.reviewText = reviewText;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    public String getReviewText() {
        return reviewText;
    }

    public float getRating() {
        return rating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    private Review(Parcel in) {
        reviewText = in.readString();
        rating = in.readFloat();
        reviewDate = new Date(in.readLong());
    }

    //PARCELABLE IMPLEMENTATION
    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reviewText);
        dest.writeFloat(rating);
        dest.writeLong(reviewDate.getTime());
    }
}
