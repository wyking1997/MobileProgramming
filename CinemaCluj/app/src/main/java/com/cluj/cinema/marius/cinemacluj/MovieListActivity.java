package com.cluj.cinema.marius.cinemacluj;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cluj.cinema.marius.cinemacluj.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieListActivity extends ListActivity {

    public static final String EXTRA_MOVIE_POSITION_IN_LIST = "com.cluj.cinema.marius.cinemacluj.MovieListActivity.moviePositionInList";
    public static final List<Movie> MOVIES;

    public static ArrayAdapter<String> adapter;
    public static List<String> titles;

    static {
        MOVIES = new ArrayList<>();
        MOVIES.addAll(Arrays.asList(new Movie[]{new Movie(2017, "Piratii din caraibe", 95, "betiv norocos"),
                new Movie(2017, "Omul paianjen", 80, "This is with benner!"),
                new Movie(2017, "Thor", 115, "big green animal"),
                new Movie(2008, "Neinfricata", 65, "roscata si trage cu arcul"),
                new Movie(2010, "Minionii 1", 87, "galbeni si multi 1"),
                new Movie(2011, "Minionii 2", 71, "galbeni si multi 2")
        }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        titles = new ArrayList<>();
        for (Movie m : MOVIES)
            titles.add(m.getListItemRepresentation());

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
                intent.putExtra(MovieListActivity.EXTRA_MOVIE_POSITION_IN_LIST, position);
                startActivity(intent);
            }
        });

        adapter = new ArrayAdapter<String>(getListView().getContext(),
                android.R.layout.simple_list_item_1, titles);
        getListView().setAdapter(adapter);
    }

    public void goToAddNewMovie(View view){
        Intent intent = new Intent(this, CreateNewItemActivity.class);
        startActivity(intent);
    }

}
