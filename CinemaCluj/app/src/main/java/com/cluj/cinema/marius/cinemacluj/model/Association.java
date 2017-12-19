package com.cluj.cinema.marius.cinemacluj.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by marius on 12/18/2017.
 */

@Entity(tableName = "assoc")
public class Association {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long cinema_id;
    private Long movie_id;
    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(Long cinema_id) {
        this.cinema_id = cinema_id;
    }

    public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Association{" +
                "id=" + id +
                ", cinema_id=" + cinema_id +
                ", movie_id=" + movie_id +
                ", date='" + date + '\'' +
                '}';
    }

    public void setDate(String date) {
        this.date = date;
    }
}
