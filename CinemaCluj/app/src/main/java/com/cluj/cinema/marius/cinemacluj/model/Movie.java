package com.cluj.cinema.marius.cinemacluj.model;

import android.arch.persistence.room.Entity;

import java.io.Serializable;

/**
 * Created by marius on 11/2/2017.
 */

public class Movie implements Serializable {

    private static int COUNTER = 0;

    private String year;
    private String title;
    private int duration;
    private String description;
    private int id;

    public Movie(String year, String title, int duration, String description) {
        this.year = year;
        this.title = title;
        this.duration = duration;
        this.description = description;
        id = COUNTER++;
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

    public int getId() {
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


