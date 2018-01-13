package com.cluj.cinema.marius.cinemacluj.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by marius on 12/18/2017.
 */
@Entity(tableName = "cinema")
public class Cinema {

    private String name;
    private String address;
    private String phoneNumber;
    @PrimaryKey
    @NonNull
    private String firebaseKey;

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public Cinema(String name, String address, String phoneNumber, String firebaseKey) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.firebaseKey = firebaseKey;
    }

    @Ignore
    public Cinema(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Ignore
    public Cinema() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getListItemRepresentation(){
        return name + "\n" + address + "\n" + phoneNumber;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
