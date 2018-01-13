package com.cluj.cinema.marius.cinemacluj.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by marius on 11/2/2017.
 */

@Entity(tableName = "movie")
public class Movie{
    public void setId(long id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String year;
    private String title;
    private int duration;
    private String description;
    private String firebaseKey;

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public Movie(String year, String title, int duration, String description, String firebaseKey) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.firebaseKey = firebaseKey;
    }

    @Ignore
    public Movie() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (year != movie.year) return false;
        if (duration != movie.duration) return false;
        return title != null ? title.equals(movie.title) : movie.title == null;

    }

    @Override
    public int hashCode() {
        int result = year.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + duration;
        return result;
    }

    public Long getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "year=" + year +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }

    public String getListItemRepresentation(){
        return this.title + " " + this.year + "\n" + this.duration + " minutes";
    }
}


