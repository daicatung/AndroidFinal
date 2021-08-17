package com.example.androidfinal.api;

public class ApiGetMovie {
    public boolean adult;
    public String backdrop_path;
    public String title;
    public double vote_average;
    public String poster_path;
    public String overview;
    public String release_date;
    public int id;

    public ApiGetMovie(boolean adult, String backdrop_path, String title, double vote_average, String poster_path, String overview, String release_date) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
