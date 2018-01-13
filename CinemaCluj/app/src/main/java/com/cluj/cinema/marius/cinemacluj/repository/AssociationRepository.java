package com.cluj.cinema.marius.cinemacluj.repository;

import com.cluj.cinema.marius.cinemacluj.model.Association;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by marius on 12/19/2017.
 */

public class AssociationRepository {

    private List<Association> repo;
    private final AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public AssociationRepository(final AppDatabase appDatabase) {
        this.repo = new ArrayList<>();
        this.appDatabase = appDatabase;

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                repo.addAll(appDatabase.associationDao().getAll());
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void add(final Association e) {
        repo.add(e);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.associationDao().insertAll(e);
            }
        });
    }

    public void update(final Association e) {
        Association initialAssociation = null;
        for(Association sl : repo) {
            if(sl.getFirebaseKey().equals(e.getFirebaseKey())) {
                initialAssociation = sl;
                break;
            }
        }
        initialAssociation.setCinemaKey(e.getCinemaKey());
        initialAssociation.setDate(e.getDate());
        initialAssociation.setMovieKey(e.getMovieKey());

        final Association toUpdateList = initialAssociation;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.associationDao().update(toUpdateList);
            }
        });
    }

    public void delete(final String firebase_key) {

        for(Association sl : repo) {
            if(sl.getFirebaseKey().equals(firebase_key)) {
                repo.remove(sl);
                final Association toDelete = sl;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.associationDao().delete(toDelete);
                    }
                });
                break;
            }
        }
    }

    public List<Association> getAll() {
        return repo;
    }

    public Association getAssociationByKey(final String key) {
        for(Association p:repo) {
            if(p.getFirebaseKey().equals(key)) {
                return p;
            }
        }
        return null;
    }

    public Association getAssociation(int index){
        return repo.get(index);
    }
}
