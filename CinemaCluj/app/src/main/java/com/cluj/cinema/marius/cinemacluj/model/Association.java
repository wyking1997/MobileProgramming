package com.cluj.cinema.marius.cinemacluj.model;

/**
 * Created by marius on 12/18/2017.
 */

public class Association {
    private static int COUNTER = 0;

    private int id;
    private int cinema_id;
    private int movie_id;
    private String date;

    public Association(int cinema_id, int movie_id, String date) {
        this.cinema_id = cinema_id;
        this.movie_id = movie_id;
        this.date = date;
        id = COUNTER++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
