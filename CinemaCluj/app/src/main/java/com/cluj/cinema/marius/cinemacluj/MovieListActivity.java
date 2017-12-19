package com.cluj.cinema.marius.cinemacluj;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cluj.cinema.marius.cinemacluj.model.Movie;
import com.cluj.cinema.marius.cinemacluj.repository.AppDatabase;
import com.cluj.cinema.marius.cinemacluj.repository.CinemaRepository;
import com.cluj.cinema.marius.cinemacluj.util.Globals;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        if (Globals.cinemaRepository == null) {
            AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
            Globals.cinemaRepository = new CinemaRepository(appDatabase);
        }

        titles = new ArrayList<>();
        for (int i = 0; i < Globals.getMOVIESListSize(); i++)
            titles.add(Globals.getMOVIE(i).getListItemRepresentation());

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
        Intent intent = new Intent(this, CinemaListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MOVIE_DETAIL_REQUEST) {
                String action = data.getStringExtra("action");
                if (action.equals(ACTION_UPDATE)) {
                    int id = data.getIntExtra("id", -1);
                    int position = -1;
                    Movie movie = null;
                    for (int i = 0; i < Globals.getMOVIESListSize(); i++)
                        if (Globals.getMOVIE(i).getId() == id) {
                            movie = Globals.getMOVIE(i);
                            position = i;
                            break;
                        }
                    movie.setTitle(data.getStringExtra("title"));
                    movie.setYear(data.getStringExtra("year"));
                    movie.setDuration(data.getIntExtra("duration", -1));

                    MovieListActivity.titles.set(position, movie.getListItemRepresentation());
                    MovieListActivity.adapter.notifyDataSetChanged();
                } else if (action.equals(ACTION_DELETE)) {
                    int id = data.getIntExtra("id", -1);
                    int position = -1;
                    for (int i = 0; i < Globals.getMOVIESListSize(); i++)
                        if (Globals.getMOVIE(i).getId() == id) {
                            position = i;
                            break;
                        }
                    Globals.removeMovie(position);
                    titles.remove(position);
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == CREATE_MOVIE_REQUEST) {
                Movie movie = new Movie(
                        data.getStringExtra("year"),
                        data.getStringExtra("title"),
                        data.getIntExtra("duration", -1),
                        data.getStringExtra("description")
                );
                Globals.addMovie(movie);
                titles.add(movie.getListItemRepresentation());
                adapter.notifyDataSetChanged();
            }
        }
    }
}