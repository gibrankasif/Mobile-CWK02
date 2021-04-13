package com.gibran.mobile_cwk02;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class IMDBMovie implements Serializable {
    private String title;
    private String imageURL;
    private String rating;
    private Drawable drawable;
    private boolean isChecked;

    public IMDBMovie(String title, String imageURL, String rating) {
        this.title = title;
        this.imageURL = imageURL;
        this.rating = rating;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "IMBDMovie{" + " title='" + title + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
