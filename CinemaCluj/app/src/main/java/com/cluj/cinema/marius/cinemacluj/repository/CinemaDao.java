package com.cluj.cinema.marius.cinemacluj.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cluj.cinema.marius.cinemacluj.model.Cinema;

import java.util.List;

/**
 * Created by marius on 12/19/2017.
 */

@Dao
public interface CinemaDao {

    @Query("SELECT * FROM cinema")
    List<Cinema> getAll();

    @Query("SELECT * FROM cinema where firebaseKey = :firebaseKey")
    Cinema getCinemaById(String firebaseKey);

    @Insert
    void insertAll(Cinema... cinemas);


    @Delete
    void delete(Cinema cinema);


    @Update
    void update(Cinema cinema);
}
