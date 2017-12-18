package com.cluj.cinema.marius.cinemacluj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cluj.cinema.marius.cinemacluj.R;
import com.cluj.cinema.marius.cinemacluj.model.Cinema;
import com.cluj.cinema.marius.cinemacluj.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CinemaListActivity extends AppCompatActivity {

    public static final String EXTRA_CINEMA_POSITION_IN_LIST = "com.cluj.cinema.marius.cinemacluj.CinemaListActivity.cinemaPositionInList";
    public static final List<Cinema> CINEMAS;

    public static final int CINEMA_DETAIL_REQUEST = 1;
    public static final int CREATE_CINEMA_REQUEST = 2;

    public static final String ACTION_UPDATE = "UPDATE";
    public static final String ACTION_DELETE = "DELETE";

    public static ArrayAdapter<String> adapter;
    public static List<String> titles;

    static {
        CINEMAS = new ArrayList<>();
        CINEMAS.addAll(Arrays.asList(new Cinema[]{
                new Cinema("Cinema Marasti", "Strada Aurel Vlaicu 3", "0264 598 784"),
                new Cinema("Cinema Vicatoria","Bulevardul Eroilor 51","0264 450 143"),
                new Cinema("Cinema Florin Piersic","Piata Mihai Viteazu 11","0264 433 477")
        }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_list);

        titles = new ArrayList<>();
        for (Cinema c: CINEMAS)
            titles.add(c.getListItemRepresentation());

        ListView list = (ListView) findViewById(R.id.cinema_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CinemaListActivity.this, CinemaDetailActivity.class);
                intent.putExtra(CinemaListActivity.EXTRA_CINEMA_POSITION_IN_LIST, position);
                startActivity(intent);
            }
        });

        adapter = new ArrayAdapter<String>(list.getContext(),
                android.R.layout.simple_list_item_1, titles);
        list.setAdapter(adapter);
    }

    public void goToMovieList(View view){
        finish();
    }
}
