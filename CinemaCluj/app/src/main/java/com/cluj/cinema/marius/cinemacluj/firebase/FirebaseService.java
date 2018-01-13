package com.cluj.cinema.marius.cinemacluj.firebase;

import com.cluj.cinema.marius.cinemacluj.model.Association;
import com.cluj.cinema.marius.cinemacluj.model.Cinema;
import com.cluj.cinema.marius.cinemacluj.model.Movie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by marius on 1/13/2018.
 */

public class FirebaseService {
    private FirebaseDatabase database=FirebaseDatabase.getInstance();

    public String addMovie(Movie movie){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference movieRef=ref.child("movies");

        String key=movieRef.push().getKey();
        movie.setFirebaseKey(key);
        movieRef.child(key).setValue(movie);
        return key;
    }

    public void updateMovie(Movie movie){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference movieRef=ref.child("movies");

        movieRef.child(movie.getFirebaseKey()).setValue(movie);
    }

    public void deleteMovie(String key){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference movieRef=ref.child("movies");

        movieRef.child(key).removeValue();
    }

    public String addCinema(Cinema cinema){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference cinemaRef=ref.child("cinemas");

        String key=cinemaRef.push().getKey();
        cinema.setFirebaseKey(key);
        cinemaRef.child(key).setValue(cinema);
        return key;
    }

    public void updateCinema(Cinema cinema){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference cinemaRef=ref.child("cinemas");

        cinemaRef.child(cinema.getFirebaseKey()).setValue(cinema);
    }

    public void deleteCinema(String key){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference cinemaRef=ref.child("cinemas");

        cinemaRef.child(key).removeValue();
    }

    public String addAssociation(Association association){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference associationRef=ref.child("associations");

        String key=associationRef.push().getKey();
        association.setFirebaseKey(key);
        associationRef.child(key).setValue(association);
        return key;
    }

    public void updateAssociation(Association association){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference associationRef=ref.child("associations");

        associationRef.child(association.getFirebaseKey()).setValue(association);
    }

    public void deleteAssociation(String key){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference associationRef=ref.child("associations");

        associationRef.child(key).removeValue();
    }
}
