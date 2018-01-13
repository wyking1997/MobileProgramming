package com.cluj.cinema.marius.cinemacluj.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cluj.cinema.marius.cinemacluj.model.User;

import java.util.List;

/**
 * Created by marius on 1/13/2018.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users where firebaseKey = :key")
    User getUserByKey(String key);

    @Insert
    void insertAll(User... user);


    @Delete
    void delete(User user);

    @Update
    void update(User user);
}
