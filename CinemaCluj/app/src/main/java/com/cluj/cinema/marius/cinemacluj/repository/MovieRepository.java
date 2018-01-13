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
            if(sl.getFirebaseKey().equals(e.getFirebaseKey())) {
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

    public void delete(final String key) {

        for(Movie sl : repo) {
            if(sl.getFirebaseKey().equals(key)) {
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

    public Movie getMovieByKey(final String key) {
        for(Movie p:repo) {
            if(p.getFirebaseKey().equals(key)) {
                return p;
            }
        }
        return null;
    }

    public Movie getMovie(int index){
        return repo.get(index);
    }
}
