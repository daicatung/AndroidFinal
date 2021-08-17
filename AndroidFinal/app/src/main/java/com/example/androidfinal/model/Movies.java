package com.example.androidfinal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "movies")
public class Movies implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String movieTitle;
    private int movieImg;
    private String movieReleaseDate;
    private String movieRating;
    private String movieOverView;
    private String movieType;
    private boolean movieFavorite;
    @TypeConverters(MoviesConverter.class)
    private List<Cast> listCast;
    private String imgPoster;
    private long timeReminder;
    private String timeReminderDisplay;
    private int idMovie;

    public Movies(String movieTitle, int movieImg, String movieReleaseDate, String movieRating, String movieOverView, String movieType, boolean movieFavorite) {
        this.movieTitle = movieTitle;
        this.movieImg = movieImg;
        this.movieReleaseDate = movieReleaseDate;
        this.movieRating = movieRating;
        this.movieOverView = movieOverView;
        this.movieType = movieType;
        this.movieFavorite = movieFavorite;
    }

    public Movies() {

    }

    public String getTimeReminderDisplay() {
        return timeReminderDisplay;
    }

    public void setTimeReminderDisplay(String timeReminderDisplay) {
        this.timeReminderDisplay = timeReminderDisplay;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public long getTimeReminder() {
        return timeReminder;
    }

    public void setTimeReminder(long timeReminder) {
        this.timeReminder = timeReminder;
    }

    public List<Cast> getListCast() {
        return listCast;
    }

    public void setListCast(List<Cast> listCast) {
        this.listCast = listCast;
    }

    public String getImgPoster() {
        return imgPoster;
    }

    public void setImgPoster(String imgPoster) {
        this.imgPoster = imgPoster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getMovieImg() {
        return movieImg;
    }

    public void setMovieImg(int movieImg) {
        this.movieImg = movieImg;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieOverView() {
        return movieOverView;
    }

    public void setMovieOverView(String movieOverView) {
        this.movieOverView = movieOverView;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public boolean isMovieFavorite() {
        return movieFavorite;
    }

    public void setMovieFavorite(boolean movieFavorite) {
        this.movieFavorite = movieFavorite;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
