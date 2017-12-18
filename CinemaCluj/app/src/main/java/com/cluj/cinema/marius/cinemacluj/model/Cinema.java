package com.cluj.cinema.marius.cinemacluj.model;

/**
 * Created by marius on 12/18/2017.
 */

public class Cinema {
    private static int COUNTER = 0;

    private String name;
    private String address;
    private String phoneNumber;
    private int id;

    public Cinema(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        id = COUNTER++;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
