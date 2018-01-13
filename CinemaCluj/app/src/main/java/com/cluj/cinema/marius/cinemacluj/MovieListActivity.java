package com.cluj.cinema.marius.cinemacluj;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cluj.cinema.marius.cinemacluj.model.Cinema;
import com.cluj.cinema.marius.cinemacluj.model.Movie;
import com.cluj.cinema.marius.cinemacluj.repository.AppDatabase;
import com.cluj.cinema.marius.cinemacluj.repository.AssociationRepository;
import com.cluj.cinema.marius.cinemacluj.repository.CinemaRepository;
import com.cluj.cinema.marius.cinemacluj.repository.MovieRepository;
import com.cluj.cinema.marius.cinemacluj.util.Globals;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

public class MovieListActivity extends ListActivity {

    public static final String EXTRA_MOVIE_POSITION_IN_LIST = "com.cluj.cinema.marius.cinemacluj.MovieListActivity.moviePositionInList";
    public static final int MOVIE_DETAIL_REQUEST = 1;
    public static final int CREATE_MOVIE_REQUEST = 2;
    public static final String ACTION_UPDATE = "UPDATE";
    public static final String ACTION_DELETE = "DELETE";
    public static ArrayAdapter<String> adapter;
    public static List<String> titles;
    private static boolean movieListLisenerSetedUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        if (Globals.cinemaRepository == null) {
            AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
            Globals.cinemaRepository = new CinemaRepository(appDatabase);
            Globals.movieRepository = new MovieRepository(appDatabase);
            Globals.associationRepository = new AssociationRepository(appDatabase);
        }

        titles = new ArrayList<>();
        for (Movie m : Globals.getMovies())
            titles.add(m.getListItemRepresentation());

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
                intent.putExtra(MovieListActivity.EXTRA_MOVIE_POSITION_IN_LIST, position);
                startActivityForResult(intent, MOVIE_DETAIL_REQUEST);
            }
        });

        // search how to set up custom list items (define our own adapter)
        adapter = new ArrayAdapter<String>(getListView().getContext(),
                android.R.layout.simple_list_item_1, titles);
        getListView().setAdapter(adapter);
    }

    public void goToAddNewMovie(View view){
        Intent intent = new Intent(this, CreateNewItemActivity.class);
        startActivityForResult(intent, CREATE_MOVIE_REQUEST);
    }

    public void goToCinemasList(View view){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MOVIE_DETAIL_REQUEST) {
                String action = data.getStringExtra("action");
                if (action.equals(ACTION_UPDATE)) {
                    String key = data.getStringExtra("key");
                    int position = -1;
                    Movie movie = null;
                    for (int i = 0; i < Globals.getMovies().size(); i++)
                        if (Globals.getMovie(i).getFirebaseKey() == key) {
                            movie = Globals.getMovie(i);
                            position = i;
                            break;
                        }
                    movie.setTitle(data.getStringExtra("title"));
                    movie.setYear(data.getStringExtra("year"));
                    movie.setDuration(data.getIntExtra("duration", -1));
                    Globals.updateMovie(movie);

                    MovieListActivity.titles.set(position, movie.getListItemRepresentation());
                    MovieListActivity.adapter.notifyDataSetChanged();
                } else if (action.equals(ACTION_DELETE)) {
                    String key = data.getStringExtra("key");
                    int position = -1;
                    for (int i = 0; i < Globals.getMovies().size(); i++)
                        if (Globals.getMovie(i).getFirebaseKey() == key) {
                            position = i;
                            break;
                        }
                    Globals.removeMovie(key);
                    titles.remove(position);
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == CREATE_MOVIE_REQUEST) {
                Movie movie = new Movie();
                movie.setDuration(data.getIntExtra("duration", -1));
                movie.setTitle(data.getStringExtra("title"));
                movie.setYear(data.getStringExtra("year"));
                movie.setDescription(data.getStringExtra("description"));
                Globals.addMovie(movie);
                titles.add(movie.getListItemRepresentation());
                adapter.notifyDataSetChanged();
            }
        }
    }

    public static void setUpMovieListener(){
        if (movieListLisenerSetedUp)
            return;
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("server");
        DatabaseReference bookRef=ref.child("movies");

        bookRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Movie m=dataSnapshot.getValue(Movie.class);

                if(Globals.getMovieByKey(m.getFirebaseKey()) == null){
                    Globals.movieRepository.add(m);
                    titles.add(m.getListItemRepresentation());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Movie newMovie = dataSnapshot.getValue(Movie.class);
                titles.remove(Globals.getMovieByKey(newMovie.getFirebaseKey()).getListItemRepresentation());
                Globals.updateMovie(newMovie);
                titles.add(newMovie.getListItemRepresentation());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for(int i=0;i<Globals.movieRepository.getAll().size();i++){
                    if(Globals.movieRepository.getMovie(i).getFirebaseKey().equals(dataSnapshot.getKey())){
                        Globals.movieRepository.delete(dataSnapshot.getKey());
                        titles.remove(i);
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        movieListLisenerSetedUp = true;
    }
}