package com.cluj.cinema.marius.cinemacluj.repository;

import com.cluj.cinema.marius.cinemacluj.model.Cinema;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by marius on 12/19/2017.
 */

public class CinemaRepository {

    private List<Cinema> repo;
    private final AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CinemaRepository(final AppDatabase appDatabase) {
        this.repo = new ArrayList<>();
        this.appDatabase = appDatabase;

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                repo.addAll(appDatabase.cinemaDao().getAll());
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void add(final Cinema e) {
        repo.add(e);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.cinemaDao().insertAll(e);
            }
        });
    }

    public void update(final Cinema e) {
        Cinema initialCinema = null;
        for(Cinema sl : repo) {
            if(sl.getFirebaseKey() == e.getFirebaseKey()) {
                initialCinema = sl;
                break;
            }
        }
        initialCinema.setAddress(e.getAddress());
        initialCinema.setName(e.getName());
        initialCinema.setPhoneNumber(e.getPhoneNumber());

        final Cinema toUpdateList = initialCinema;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.cinemaDao().update(toUpdateList);
            }
        });
    }

    public void delete(final String key) {

        for(Cinema sl : repo) {
            if(sl.getFirebaseKey() == key) {
                repo.remove(sl);
                final Cinema toDelete = sl;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.cinemaDao().delete(toDelete);
                    }
                });
                break;
            }
        }
    }

    public List<Cinema> getAll() {
        return repo;
    }

    public Cinema getCinemaByKey(final String key) {
        for(Cinema p:repo) {
            if(p.getFirebaseKey().equals(key)) {
                return p;
            }
        }
        return null;
    }

    public Cinema getCinema(int index){
        return repo.get(index);
    }
}
