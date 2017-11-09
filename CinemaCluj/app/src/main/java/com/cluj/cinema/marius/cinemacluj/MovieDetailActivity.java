package com.cluj.cinema.marius.cinemacluj;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cluj.cinema.marius.cinemacluj.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        int position = getIntent().getIntExtra(MovieListActivity.EXTRA_MOVIE_POSITION_IN_LIST, -1);
        movie = MovieListActivity.MOVIES.get(position);

        EditText titleText = (EditText) findViewById(R.id.titleText);
        EditText yearText = (EditText) findViewById(R.id.yearText);
        EditText durationText = (EditText) findViewById(R.id.durationText);

        titleText.setText(movie.getTitle());
        durationText.setText("" + movie.getDuration());
        yearText.setText("" + movie.getYear());
    }

    public void saveInformation(View view){
        EditText titleText = (EditText) findViewById(R.id.titleText);
        EditText yearText = (EditText) findViewById(R.id.yearText);
        EditText durationText = (EditText) findViewById(R.id.durationText);

        boolean flag = true;
        int year = Integer.parseInt("" + yearText.getText());
        int duration = Integer.parseInt("" + durationText.getText());

        if (1900 > year || year > 2300){
            yearText.setError("Invalid info!");
            flag = false;
        }
        if (0 > duration || duration > 1000) {
            durationText.setError("Invalid info!");
            flag = false;
        }
        if (flag) {
            movie.setYear(year);
            movie.setDuration(duration);
            movie.setTitle(titleText.getText() + "");
            Intent intent = new Intent(this, MovieDetailActivity.class);
//            startActivity(intent);
//            try {
//                finish();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
            finish();
        }
    }

    public void closeActivity(View view){
        finish();
    }

    public void createDialog(String message, String title){
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getApplicationContext());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message)
                .setTitle(title);

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
    }


}
