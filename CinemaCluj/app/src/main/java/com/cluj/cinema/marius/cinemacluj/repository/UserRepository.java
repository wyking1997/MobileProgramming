package com.cluj.cinema.marius.cinemacluj.repository;

import com.cluj.cinema.marius.cinemacluj.model.Association;
import com.cluj.cinema.marius.cinemacluj.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by marius on 1/13/2018.
 */

public class UserRepository {
    private List<User> repo;
    private final AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public UserRepository(final AppDatabase appDatabase) {
        this.repo = new ArrayList<>();
        this.appDatabase = appDatabase;

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                repo.addAll(appDatabase.userDao().getAll());
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void add(final User e) {
        repo.add(e);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().insertAll(e);
            }
        });
    }

    public void update(final User e) {
        User initialUser = null;
        for(User sl : repo) {
            if(sl.getFirebaseKey().equals(e.getFirebaseKey())) {
                initialUser = sl;
                break;
            }
        }
        initialUser.setUsername(e.getUsername());
        initialUser.setRole(e.getRole());

        final User toUpdateList = initialUser;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().update(toUpdateList);
            }
        });
    }

    public void delete(final String firebase_key) {

        for(User sl : repo) {
            if(sl.getFirebaseKey().equals(firebase_key)) {
                repo.remove(sl);
                final User toDelete = sl;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.userDao().delete(toDelete);
                    }
                });
                break;
            }
        }
    }

    public List<User> getAll() {
        return repo;
    }

    public User getUserByKey(final String key) {
        for(User p:repo) {
            if(p.getFirebaseKey().equals(key)) {
                return p;
            }
        }
        return null;
    }

    public User getUser(int index){
        return repo.get(index);
    }

    public User getUserByName(String username){
        for (User u : repo)
            if (u.getUsername().equals(username))
                return u;
        return null;
    }
}
