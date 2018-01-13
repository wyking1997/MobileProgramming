package com.cluj.cinema.marius.cinemacluj.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cluj.cinema.marius.cinemacluj.model.Association;
import com.cluj.cinema.marius.cinemacluj.model.Movie;

import java.util.List;

/**
 * Created by marius on 12/19/2017.
 */
@Dao
public interface AssociationDao {
    @Query("SELECT * FROM assoc")
    List<Association> getAll();

    @Query("SELECT * FROM assoc where firebaseKey = :key")
    Association getMovieByKey(String key);

    @Insert
    void insertAll(Association... assocs);


    @Delete
    void delete(Association assoc);

    @Update
    void update(Association assoc);
}
