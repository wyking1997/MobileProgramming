package com.cluj.cinema.marius.cinemacluj.util;

import com.cluj.cinema.marius.cinemacluj.model.Association;
import com.cluj.cinema.marius.cinemacluj.model.Cinema;
import com.cluj.cinema.marius.cinemacluj.model.Movie;
import com.cluj.cinema.marius.cinemacluj.repository.AssociationRepository;
import com.cluj.cinema.marius.cinemacluj.repository.CinemaRepository;
import com.cluj.cinema.marius.cinemacluj.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marius on 12/18/2017.
 */

public class Globals {

    public static CinemaRepository cinemaRepository = null;
    public static MovieRepository movieRepository = null;
    public static AssociationRepository associationRepository = null;




    public static Cinema getCINEMA(Long id){
        return cinemaRepository.getCinemaById(id);
    }

    public static List<Cinema> getCINEMAS(){
        return cinemaRepository.getAll();
    }

    public static Cinema getCinema(int index){
        return cinemaRepository.getCinema(index);
    }

    public static void addCinema(Cinema cinema){
        cinemaRepository.add(cinema);
    }

    public static void updateCinema(Cinema cinema){
        cinemaRepository.update(cinema);
    }

    public static void removeCinema(Long id){
        cinemaRepository.delete(id);
    }




    public static Movie getMOVIE(int index) {
        return movieRepository.getMovie(index);
    }

    public static List<Movie> getMOVIES(){
        return movieRepository.getAll();
    }

    public static void removeMovie(long id){
        movieRepository.delete(id);
    }

    public static void addMovie(Movie movie){
        movieRepository.add(movie);
    }

    public static void updateMovie(Movie movie){
        movieRepository.update(movie);
    }




    public static Association getASSOC(int index) {
        return associationRepository.getAssociation(index);
    }

    public static List<Association> getASSOCS() {
        return associationRepository.getAll();
    }

    public static void addAssoc(Association association){
        associationRepository.add(association);
    }

    public static void removeAssociation(long id){
        associationRepository.delete(id);
    }

    public static void updateAssoc(Association association){
        associationRepository.update(association);
    }
}
