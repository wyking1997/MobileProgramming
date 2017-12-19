package com.cluj.cinema.marius.cinemacluj.repository;

import com.cluj.cinema.marius.cinemacluj.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by marius on 12/19/2017.
 */

public class MovieRepository {
    private List<Movie> repo;
    private final AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public MovieRepository(final AppDatabase appDatabase) {
        this.repo = new ArrayList<>();
        this.appDatabase = appDatabase;

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                repo.addAll(appDatabase.movieDao().getAll());
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Movie m1 = new Movie();
//        m1.setYear("2017-12-01");
//        m1.setTitle("Piratii din caraibe");
//        m1.setDuration(95);
//        m1.setDescription("betiv norocos");
//        Movie m2 = getMovie("2017-12-14", "Omul paianjen", 80, "This is with benner!");
//        Movie m3 = getMovie("2017-10-01", "Thor", 115, "big green animal");
//        Movie m4 = getMovie("2015-02-25", "Neinfricata", 65, "roscata si trage cu arcul");
//        Movie m5 = getMovie("2017-11-22", "Minionii 1", 87, "galbeni si multi 1");
//        Movie m6 = getMovie("2014-12-12", "Minionii 2", 71, "galbeni si multi 2");
//        add(m1);add(m2);add(m6);add(m5);add(m4);add(m3);
    }

    private Movie getMovie(String year, String title, int duration, String description){
        Movie result = new Movie();
        result.setDescription(description);
        result.setTitle(title);
        result.setDuration(duration);
        result.setYear(year);
        return result;
    }

    public void add(final Movie e) {
        repo.add(e);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.movieDao().insertAll(e);
            }
        });
    }

    public void update(final Movie e) {
        Movie initialMovie = null;
        for(Movie sl : repo) {
            if(sl.getId() == e.getId()) {
                initialMovie = sl;
                break;
            }
        }
        initialMovie.setDuration(e.getDuration());
        initialMovie.setTitle(e.getTitle());
        initialMovie.setDescription(e.getDescription());
        initialMovie.setYear(e.getYear());

        final Movie toUpdateList = initialMovie;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.movieDao().update(toUpdateList);
            }
        });
    }

    public void delete(final Long id) {

        for(Movie sl : repo) {
            if(sl.getId() == id) {
                repo.remove(sl);
                final Movie toDelete = sl;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.movieDao().delete(toDelete);
                    }
                });
                break;
            }
        }
    }

    public List<Movie> getAll() {
        return repo;
    }

    public Movie getMovieById(final Long id) {
        for(Movie p:repo) {
            if(p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public Movie getMovie(int index){
        return repo.get(index);
    }
}
