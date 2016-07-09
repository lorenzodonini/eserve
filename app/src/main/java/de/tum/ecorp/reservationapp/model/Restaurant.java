package de.tum.ecorp.reservationapp.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Restaurant extends Entity implements Parcelable {

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
    private ArrayList<Review> reviews;
    private Location location;
    private String [] imageUris;
    private ArrayList<Table> tables;
    private OpeningTimes openingTimes;

    public Restaurant(String name, String category, String address, String website, PriceRange priceRange, Location location,
                      List<Review> reviews, List<Table> tables, OpeningTimes openingTimes, String [] imageUris) {

        this.name = name;
        this.category = category;
        this.address = address;
        this.website = website;
        this.priceRange = priceRange;
        this.location = location;
        this.reviews = new ArrayList<>();
        this.reviews.addAll(reviews);
        this.tables = new ArrayList<>();
        this.tables.addAll(tables);
        this.openingTimes = openingTimes;
        this.imageUris = (imageUris != null) ? imageUris : new String[0];
    }

    public Restaurant(String name, String category, String address, String website, PriceRange priceRange, Location location) {
        this.name = name;
        this.category = category;
        this.address = address;
        this.website = website;
        this.priceRange = priceRange;
        this.location = location;
        this.reviews = new ArrayList<>();
        this.tables = new ArrayList<>();
        this.openingTimes = new OpeningTimes();
        this.imageUris = new String[0];
    }

    //GETTERS & SETTERS
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
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
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
        reviews.add(review);
    }

    public void addReviews(List<Review> reviews) {
        this.reviews.addAll(reviews);
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public int getNumberOfReviews() {
        return reviews.size();
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }

    public OpeningTimes getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(OpeningTimes openingTimes) {
        this.openingTimes = openingTimes;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public String[] getImageUris() {
        return imageUris;
    }

    public void setImageUris(String[] imageUris) {
        if (imageUris != null) {
            this.imageUris = imageUris;
        }
    }

    //PARCELABLE IMPLEMENTATION
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(address);
        dest.writeString(website);
        dest.writeString(priceRange.name());
        dest.writeParcelable(location, flags);
        //dest.writeParcelableArray(tables.toArray(new Table[tables.size()]),flags);
        dest.writeParcelableArray(reviews.toArray(new Review[reviews.size()]), flags);
        dest.writeInt(imageUris.length);
        dest.writeStringArray(imageUris);
    }

    private Restaurant(Parcel in) {
        name = in.readString();
        category = in.readString();
        address = in.readString();
        website = in.readString();
        priceRange = PriceRange.valueOf(in.readString());
        location = in.readParcelable(Location.class.getClassLoader());
//        Parcelable [] tableItems = in.readParcelableArray(Table.class.getClassLoader());
//        if (tableItems != null) {
//            tables = new ArrayList<>(tableItems.length);
//            for (Parcelable item : tableItems) {
//                tables.add((Table) item);
//            }
//        }
        Parcelable [] items = in.readParcelableArray(Review.class.getClassLoader());
        if (items != null) {
            reviews = new ArrayList<>(items.length);
            for (Parcelable item : items) {
                reviews.add((Review)item);
            }
        }
        int imageAmt = in.readInt();
        imageUris = new String[imageAmt];
        in.readStringArray(imageUris);
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
