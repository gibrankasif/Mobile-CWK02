package com.gibran.mobile_cwk02;

public class Movie {
    private String title;
    private int year;
    private String director;
    private String actors;
    private int rating;
    private String review;
    private boolean favourite;

    public Movie(String title, int year, String director, String actors, int rating, String review, boolean favourite) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.actors = actors;
        this.rating = rating;
        this.review = review;
        this.favourite = favourite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String[] arrayOfActors() {
        if (actors == null || actors.isEmpty()) {
            return null;
        }
        else {
            return actors.split("\\s*,\\s*");
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", favourite=" + favourite +
                '}';
    }
}
