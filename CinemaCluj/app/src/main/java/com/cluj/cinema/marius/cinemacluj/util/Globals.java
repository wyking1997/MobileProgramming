package com.cluj.cinema.marius.cinemacluj.util;

import com.cluj.cinema.marius.cinemacluj.model.Association;
import com.cluj.cinema.marius.cinemacluj.model.Cinema;
import com.cluj.cinema.marius.cinemacluj.model.Movie;
import com.cluj.cinema.marius.cinemacluj.model.User;
import com.cluj.cinema.marius.cinemacluj.repository.AssociationRepository;
import com.cluj.cinema.marius.cinemacluj.repository.CinemaRepository;
import com.cluj.cinema.marius.cinemacluj.repository.MovieRepository;
import com.cluj.cinema.marius.cinemacluj.repository.UserRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by marius on 12/18/2017.
 */

public class Globals {
    private static DatabaseReference ref;
    private static DatabaseReference cinemaRef;
    private static DatabaseReference movieRef;
    private static DatabaseReference associationRef;
    private static DatabaseReference userRef;

    public static CinemaRepository cinemaRepository = null;
    public static MovieRepository movieRepository = null;
    public static AssociationRepository associationRepository = null;
    public static UserRepository userRepository = null;


    private static boolean initialSetupMade = false;
    public static void initializeAppGlobals(){
        if (initialSetupMade)
            return;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        ref=FirebaseDatabase.getInstance().getReference("server");
        cinemaRef=ref.child("cinemas");
        movieRef=ref.child("movies");
        associationRef=ref.child("associations");
        userRef=ref.child("users");
        initialSetupMade = true;
    }

    public static User getUserByKey(String key){
        return userRepository.getUserByKey(key);
    }

    public static User getUserByUsername(String username){
        return userRepository.getUserByName(username);
    }

    public static User getUser(int index){
        return userRepository.getUser(index);
    }

    public static void addUser(User user){
        String key=userRef.push().getKey();
        user.setFirebaseKey(key);
        userRef.child(key).setValue(user);
        userRepository.add(user);
    }

    public static void updateUser(User user){
        userRef.child(user.getFirebaseKey()).setValue(user);
        userRepository.update(user);
    }

    public static void removeUser(String key){
        userRef.child(key).removeValue();
        userRepository.delete(key);
    }

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
