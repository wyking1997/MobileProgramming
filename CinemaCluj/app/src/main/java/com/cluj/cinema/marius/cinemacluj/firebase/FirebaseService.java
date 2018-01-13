package com.cluj.cinema.marius.cinemacluj.firebase;

import com.cluj.cinema.marius.cinemacluj.model.Cinema;
import com.cluj.cinema.marius.cinemacluj.model.Movie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by marius on 1/13/2018.
 */

public class FirebaseService {
    private FirebaseDatabase database=FirebaseDatabase.getInstance();

    public FirebaseService() {

    }

    public void addMovie(Movie movie){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference movieRef=ref.child("movies");

        String key=movieRef.push().getKey();
        movie.setFirebaseKey(key);
        movieRef.child(key).setValue(movie);
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

    public void addCinema(Cinema cinema){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference cinemaRef=ref.child("cinemas");

        String key=cinemaRef.push().getKey();
        cinema.setFirebaseKey(key);
        cinemaRef.child(key).setValue(cinema);
    }

    public void updateCinema(Cinema cinema){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference cinemaRef=ref.child("cinema");

        cinemaRef.child(cinema.getFirebaseKey()).setValue(cinema);
    }

    public void deleteCinema(String key){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference cinemaRef=ref.child("cinemas");

        cinemaRef.child(key).removeValue();
    }
}
