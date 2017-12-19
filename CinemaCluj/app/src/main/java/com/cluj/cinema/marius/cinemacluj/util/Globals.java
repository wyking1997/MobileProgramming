package com.cluj.cinema.marius.cinemacluj.util;

import com.cluj.cinema.marius.cinemacluj.model.Association;
import com.cluj.cinema.marius.cinemacluj.model.Cinema;
import com.cluj.cinema.marius.cinemacluj.model.Movie;
import com.cluj.cinema.marius.cinemacluj.repository.CinemaRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marius on 12/18/2017.
 */

public class Globals {
//    private final static List<Cinema> CINEMAS;
    private final static List<Movie> MOVIES;
    private final static List<Association> ASSOC;

    public static CinemaRepository cinemaRepository = null;

    static{
        MOVIES = new ArrayList<>();
        MOVIES.addAll(Arrays.asList(new Movie[]{new Movie("2017-12-01", "Piratii din caraibe", 95, "betiv norocos"),
                new Movie("2017-12-14", "Omul paianjen", 80, "This is with benner!"),
                new Movie("2017-10-01", "Thor", 115, "big green animal"),
                new Movie("2015-02-25", "Neinfricata", 65, "roscata si trage cu arcul"),
                new Movie("2017-11-22", "Minionii 1", 87, "galbeni si multi 1"),
                new Movie("2014-12-12", "Minionii 2", 71, "galbeni si multi 2")
        }));

        ASSOC = new ArrayList<>();
        ASSOC.addAll(Arrays.asList(new Association[]{
                new Association(0, 0, "2017-12-01"),
                new Association(0, 0, "2017-12-02"),
                new Association(0, 0, "2017-12-03"),
                new Association(0, 2, "2017-12-02"),
                new Association(0, 2, "2017-12-03"),
                new Association(0, 3, "2017-12-03"),
                new Association(0, 4, "2017-12-01"),
                new Association(0, 4, "2017-12-02"),
                new Association(0, 4, "2017-12-03"),
                new Association(1, 1, "2017-12-01"),
                new Association(1, 1, "2017-12-01"),
                new Association(1, 1, "2017-12-02"),
                new Association(1, 1, "2017-12-02"),
                new Association(1, 1, "2017-12-03"),
                new Association(1, 1, "2017-12-04"),
                new Association(1, 2, "2017-12-02"),
                new Association(1, 2, "2017-12-04"),
                new Association(2, 0, "2017-12-01"),
                new Association(2, 0, "2017-12-02"),
                new Association(2, 3, "2017-12-02"),
                new Association(2, 3, "2017-12-02"),
                new Association(2, 3, "2017-12-03"),
                new Association(2, 3, "2017-12-01"),
                new Association(2, 3, "2017-12-01"),
                new Association(2, 4, "2017-12-01"),
                new Association(2, 4, "2017-12-02"),
                new Association(2, 4, "2017-12-03"),
                new Association(2, 4, "2017-12-04")
        }));
    }

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
        return MOVIES.get(index);
    }

    public static Association getASSOC(int index) {
        return ASSOC.get(index);
    }

    public static int getMOVIESListSize() {
        return MOVIES.size();
    }

    public static int getASSOCListSize() {
        return ASSOC.size();
    }

    public static void addMovie(Movie movie){
        MOVIES.add(movie);
    }

    public static void addAssoc(Association association){
        ASSOC.add(association);
    }

    public static void removeMovie(int index){
        MOVIES.remove(index);
    }
    public static void removeAssociation(int index){
        ASSOC.remove(index);
    }
}
