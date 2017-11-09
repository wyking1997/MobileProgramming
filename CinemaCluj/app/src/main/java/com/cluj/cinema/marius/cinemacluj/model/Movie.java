package com.cluj.cinema.marius.cinemacluj.model;

import java.io.Serializable;

/**
 * Created by marius on 11/2/2017.
 */

public class Movie implements Serializable {

    private static int COUNTER = 0;

    private int year;
    private String title;
    private int duration;
    private int id;

    public Movie(int year, String title, int duration) {
        this.year = year;
        this.title = title;
        this.duration = duration;
        id = COUNTER++;
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
        int result = year;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + duration;
        return result;
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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
}


