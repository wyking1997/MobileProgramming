package com.cluj.cinema.marius.cinemacluj;

import android.app.ListActivity;
import android.content.Context;
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
    static {
        MOVIES = new ArrayList<>();
        MOVIES.addAll(Arrays.asList(new Movie[]{new Movie(2017, "Piratii din caraibe", 95),
                new Movie(2017, "Omul paianjen", 80),
                new Movie(2017, "Thor", 115),
                new Movie(2008, "Neinfricata", 65),
                new Movie(2010, "Minionii 1", 87),
                new Movie(2011, "Minionii 2", 71)
        }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        List<String> titles = new ArrayList<>();
        for (Movie m : MOVIES)
            titles.add(m.getTitle());

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
                intent.putExtra(MovieListActivity.EXTRA_MOVIE_POSITION_IN_LIST, position);
                startActivity(intent);
//                finish();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(),
                android.R.layout.simple_list_item_1, titles);
        getListView().setAdapter(adapter);
    }

}
