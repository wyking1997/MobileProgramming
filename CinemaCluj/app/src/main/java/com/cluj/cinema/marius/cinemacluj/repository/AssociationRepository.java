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

//        Association a1 = getAssociation(0, 0, "2017-12-01");
//        Association a2 = getAssociation(0, 0, "2017-12-02");
//        Association a3 = getAssociation(0, 0, "2017-12-03");
//        Association a4 = getAssociation(0, 2, "2017-12-02");
//        Association a5 = getAssociation(0, 2, "2017-12-03");
//        Association a6 = getAssociation(0, 3, "2017-12-03");
//        Association a7 = getAssociation(0, 4, "2017-12-01");
//        Association a8 = getAssociation(0, 4, "2017-12-02");
//        Association a9 = getAssociation(0, 4, "2017-12-03");
//        Association a10 = getAssociation(1, 1, "2017-12-01");
//        Association a11 = getAssociation(1, 1, "2017-12-01");
//        Association a12 = getAssociation(1, 1, "2017-12-02");
//        Association a13 = getAssociation(1, 1, "2017-12-02");
//        Association a14 = getAssociation(1, 1, "2017-12-03");
//        Association a15 = getAssociation(1, 1, "2017-12-04");
//        Association a16 = getAssociation(1, 2, "2017-12-02");
//        Association a17 = getAssociation(1, 2, "2017-12-04");
//        Association a18 = getAssociation(2, 0, "2017-12-01");
//        Association a19 = getAssociation(2, 0, "2017-12-02");
//        Association a20 = getAssociation(2, 3, "2017-12-02");
//        Association a21 = getAssociation(2, 3, "2017-12-02");
//        Association a22 = getAssociation(2, 3, "2017-12-03");
//        Association a23 = getAssociation(2, 3, "2017-12-01");
//        Association a24 = getAssociation(2, 3, "2017-12-01");
//        Association a25 = getAssociation(2, 4, "2017-12-01");
//        Association a26 = getAssociation(2, 4, "2017-12-02");
//        Association a28 = getAssociation(2, 4, "2017-12-03");
//        Association a27 = getAssociation(2, 4, "2017-12-04");
//
//        add(a1);add(a3);add(a5);add(a7);add(a9);add(a11);add(a13);add(a15);add(a17);add(a19);add(a21);add(a23);add(a25);add(a27);
//        add(a2);add(a4);add(a6);add(a8);add(a10);add(a12);add(a14);add(a16);add(a18);add(a20);add(a22);add(a24);add(a26);add(a28);
    }
    
    public Association getAssociation(long cinema_id, long movie_id, String date){
        Association result = new Association();
        result.setDate(date);
        result.setMovie_id(movie_id);
        result.setCinema_id(cinema_id);
        return result;
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
            if(sl.getId() == e.getId()) {
                initialAssociation = sl;
                break;
            }
        }
        initialAssociation.setCinema_id(e.getCinema_id());
        initialAssociation.setDate(e.getDate());
        initialAssociation.setMovie_id(e.getMovie_id());

        final Association toUpdateList = initialAssociation;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.associationDao().update(toUpdateList);
            }
        });
    }

    public void delete(final Long id) {

        for(Association sl : repo) {
            if(sl.getId() == id) {
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

    public Association getAssociationById(final Long id) {
        for(Association p:repo) {
            if(p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public Association getAssociation(int index){
        return repo.get(index);
    }
}
