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

//        Cinema t1 = new Cinema();
//        t1.setName("Cinema Marasti");
//        t1.setAddress( "Strada Aurel Vlaicu 3");
//        t1.setPhoneNumber("0264 598 784");
//        Cinema t2 = new Cinema();
//        t2.setName("Cinema Vicatoria");
//        t2.setAddress("Bulevardul Eroilor 51");
//        t2.setPhoneNumber("0264 450 143");
//        Cinema t3 = new Cinema();
//        t3.setName("Cinema Florin Piersic");
//        t3.setAddress("Piata Mihai Viteazu 11");
//        t3.setPhoneNumber("0264 433 477");
//        add(t1);add(t2);add(t3);
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
            if(sl.getId() == e.getId()) {
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

    public void delete(final Long id) {

        for(Cinema sl : repo) {
            if(sl.getId() == id) {
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

    public Cinema getCinemaById(final Long id) {
        for(Cinema p:repo) {
            if(p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public Cinema getCinema(int index){
        return repo.get(index);
    }
}
