package com.cluj.cinema.marius.cinemacluj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.cluj.cinema.marius.cinemacluj.model.Movie;

public class CreateNewItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_item);
    }

    public void createItem(View view){
        EditText titleText = (EditText) findViewById(R.id.titleTextAdd);
        EditText durationText = (EditText) findViewById(R.id.durationTextAdd);
        EditText yearText = (EditText) findViewById(R.id.yearTextAdd);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionTextAdd);

        String titleAsString = titleText.getText() + "";
        String durationAsString = durationText.getText() + "";
        String yearAsString = yearText.getText() + "";
        String descriptionAsString = descriptionText.getText() + "";

        int yearAsInt = -1;
        int durationAsInt = -1;
        boolean flag = true;

        if (titleAsString.equals("")) {
            titleText.setError("Supply a movie title!");
            flag = false;
        }
        if (descriptionAsString.equals("")) {
            descriptionText.setError("Supply a movie description!");
            flag = false;
        }
        if (durationAsString.equals("")) {
            durationText.setError("Supply a movie duration!");
            flag = false;
        }
        else if (durationAsString.length() > 4) {
            durationText.setError("Invalid info!");
            flag = false;
        } else {
            durationAsInt = Integer.parseInt(durationAsString);
            if (1 > durationAsInt || durationAsInt > 1000){
                durationText.setError("Invalid info!");
                flag = false;
            }
        }
        if (yearAsString.equals("")) {
            yearText.setError("Supply a movie year!");
            flag = false;
        }
        else if (yearAsString.length() > 4) {
            yearText.setError("Invalid info!");
            flag = false;
        } else {
            yearAsInt = Integer.parseInt(yearAsString);
            if (1900 > yearAsInt || yearAsInt > 2300){
                yearText.setError("Invalid info!");
                flag = false;
            }
        }

        if (flag){
            Movie movie = new Movie(yearAsInt,titleAsString,durationAsInt,descriptionAsString);
            MovieListActivity.MOVIES.add(movie);
            MovieListActivity.titles.add(movie.getListItemRepresentation());
            MovieListActivity.adapter.notifyDataSetChanged();
            finish();
        }
    }
}
