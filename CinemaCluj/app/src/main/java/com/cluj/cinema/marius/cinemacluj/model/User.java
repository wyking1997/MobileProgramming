package com.cluj.cinema.marius.cinemacluj.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by marius on 1/13/2018.
 */

@Entity(tableName = "users")
public class User {
    @NonNull
    @PrimaryKey
    private String firebaseKey;

    private String username;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String firebaseKey, String username, String role) {
        this.firebaseKey = firebaseKey;
        this.username = username;
        this.role = role;
    }

    @Ignore
    public User(){}

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "firebaseKey='" + firebaseKey + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
