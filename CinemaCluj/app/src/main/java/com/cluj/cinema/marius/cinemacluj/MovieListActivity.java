package com.cluj.cinema.marius.cinemacluj;

import android.app.Activity;
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

    public static final int MOVIE_DETAIL_REQUEST = 1;
    public static final int CREATE_MOVIE_REQUEST = 2;

    public static final String ACTION_UPDATE = "UPDATE";
    public static final String ACTION_DELETE = "DELETE";

    public static ArrayAdapter<String> adapter;
    public static List<String> titles;

    static {
        MOVIES = new ArrayList<>();
        MOVIES.addAll(Arrays.asList(new Movie[]{new Movie("2017-12-01", "Piratii din caraibe", 95, "betiv norocos"),
                new Movie("2017-12-14", "Omul paianjen", 80, "This is with benner!"),
                new Movie("2017-10-01", "Thor", 115, "big green animal"),
                new Movie("2015-02-25", "Neinfricata", 65, "roscata si trage cu arcul"),
                new Movie("2017-11-22", "Minionii 1", 87, "galbeni si multi 1"),
                new Movie("2014-12-12", "Minionii 2", 71, "galbeni si multi 2")
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
                    for (int i = 0; i < MOVIES.size(); i++)
                        if (MOVIES.get(i).getId() == id) {
                            movie = MOVIES.get(id);
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
                    for (int i = 0; i < MOVIES.size(); i++)
                        if (MOVIES.get(i).getId() == id) {
                            position = i;
                            break;
                        }
                    MOVIES.remove(position);
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
                MOVIES.add(movie);
                titles.add(movie.getListItemRepresentation());
                adapter.notifyDataSetChanged();
            }
        }
    }
}
