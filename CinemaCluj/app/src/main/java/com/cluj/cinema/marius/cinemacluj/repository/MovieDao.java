package com.cluj.cinema.marius.cinemacluj.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cluj.cinema.marius.cinemacluj.model.Movie;

import java.util.List;

/**
 * Created by marius on 12/19/2017.
 */

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("SELECT * FROM movie where id = :id")
    Movie getMovieById(Long id);

    @Insert
    void insertAll(Movie... cinemas);


    @Delete
    void delete(Movie cinema);


    @Update
    void update(Movie cinema);
}
