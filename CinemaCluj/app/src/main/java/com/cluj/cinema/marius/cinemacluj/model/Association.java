package com.cluj.cinema.marius.cinemacluj.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by marius on 12/18/2017.
 */

@Entity(tableName = "assoc")
public class Association {

    private String cinemaKey;
    private String movieKey;
    private String date;
    @PrimaryKey
    @NonNull
    private String firebaseKey;

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public Association(String cinemaKey, String movieKey, String date, String firebaseKey) {
        this.cinemaKey = cinemaKey;
        this.movieKey = movieKey;
        this.date = date;
        this.firebaseKey = firebaseKey;
    }

    @Ignore
    public Association(String cinemaKey, String movieKey, String date) {
        this.cinemaKey = cinemaKey;
        this.movieKey = movieKey;
        this.date = date;
    }

    @Ignore
    public Association() {
    }

    public String getCinemaKey() {
        return cinemaKey;
    }

    public void setCinemaKey(String cinemaKey) {
        this.cinemaKey = cinemaKey;
    }

    public String getMovieKey() {
        return movieKey;
    }

    public void setMovieKey(String movieKey) {
        this.movieKey = movieKey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
