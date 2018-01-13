package com.cluj.cinema.marius.cinemacluj.util;

import com.cluj.cinema.marius.cinemacluj.model.Association;
import com.cluj.cinema.marius.cinemacluj.model.Cinema;
import com.cluj.cinema.marius.cinemacluj.model.Movie;
import com.cluj.cinema.marius.cinemacluj.repository.AssociationRepository;
import com.cluj.cinema.marius.cinemacluj.repository.CinemaRepository;
import com.cluj.cinema.marius.cinemacluj.repository.MovieRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by marius on 12/18/2017.
 */

public class Globals {
    private static DatabaseReference ref=FirebaseDatabase.getInstance().getReference("server");
    private static DatabaseReference cinemaRef=ref.child("cinemas");
    private static DatabaseReference movieRef=ref.child("movies");
    private static DatabaseReference associationRef=ref.child("associations");

    public static CinemaRepository cinemaRepository = null;
    public static MovieRepository movieRepository = null;
    public static AssociationRepository associationRepository = null;


    public static Cinema getCinemaByKey(String key){
        return cinemaRepository.getCinemaByKey(key);
    }

    public static List<Cinema> getCinemas(){
        return cinemaRepository.getAll();
    }

    public static Cinema getCinema(int index){
        return cinemaRepository.getCinema(index);
    }

    public static void addCinema(Cinema cinema){
        String key=cinemaRef.push().getKey();
        cinema.setFirebaseKey(key);
        cinemaRef.child(key).setValue(cinema);
        cinemaRepository.add(cinema);
    }

    public static void updateCinema(Cinema cinema){
        cinemaRef.child(cinema.getFirebaseKey()).setValue(cinema);
        cinemaRepository.update(cinema);
    }

    public static void removeCinema(String key){
        cinemaRef.child(key).removeValue();
        cinemaRepository.delete(key);
    }

    public static Movie getMovie(int index) {
        return movieRepository.getMovie(index);
    }

    public static Movie getMovieByKey(String key){
        return movieRepository.getMovieByKey(key);
    }

    public static List<Movie> getMovies(){
        return movieRepository.getAll();
    }

    public static void removeMovie(String key){
        movieRef.child(key).removeValue();
        movieRepository.delete(key);
    }

    public static void addMovie(Movie movie){
        String key = movieRef.push().getKey();
        movie.setFirebaseKey(key);
        movieRef.child(key).setValue(movie);
        movieRepository.add(movie);
    }

    public static void updateMovie(Movie movie){
        movieRef.child(movie.getFirebaseKey()).setValue(movie);
        movieRepository.update(movie);
    }

    public static Association getAssociation(int index) {
        return associationRepository.getAssociation(index);
    }

    public static Association getAssociationByKey(String key){
        return associationRepository.getAssociationByKey(key);
    }

    public static List<Association> getAssociations() {
        return associationRepository.getAll();
    }

    public static void addAssocation(Association association){
        String key=associationRef.push().getKey();
        association.setFirebaseKey(key);
        associationRef.child(key).setValue(association);
        associationRepository.add(association);
    }

    public static void removeAssociation(String key){
        associationRef.child(key).removeValue();
        associationRepository.delete(key);
    }

    public static void updateAssociation(Association association){
        associationRef.child(association.getFirebaseKey()).setValue(association);
        associationRepository.update(association);
    }
}
