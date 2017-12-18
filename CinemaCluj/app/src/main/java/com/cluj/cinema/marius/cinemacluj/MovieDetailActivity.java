package com.cluj.cinema.marius.cinemacluj;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cluj.cinema.marius.cinemacluj.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        position = getIntent().getIntExtra(MovieListActivity.EXTRA_MOVIE_POSITION_IN_LIST, -1);
        movie = MovieListActivity.MOVIES.get(position);

        EditText titleText = (EditText) findViewById(R.id.titleTextAdd);
        EditText yearText = (EditText) findViewById(R.id.yearTextAdd);
        EditText durationText = (EditText) findViewById(R.id.durationTextAdd);
        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);

        titleText.setText(movie.getTitle());
        durationText.setText("" + movie.getDuration());
        yearText.setText("" + movie.getYear());
        descriptionTextView.setText(movie.getDescription());
    }

    public void saveInformation(View view){
        EditText titleText = (EditText) findViewById(R.id.titleTextAdd);
        EditText yearText = (EditText) findViewById(R.id.yearTextAdd);
        EditText durationText = (EditText) findViewById(R.id.durationTextAdd);
        String yearAsString = "" + yearText.getText();
        String durationAsString = "" + durationText.getText();

        boolean flag = true;
        int year = -1;
        int duration = -1;

        if (yearAsString.length() > 4){
            yearText.setError("Invalid info!");
            flag = false;
        } else {
            year = Integer.parseInt(yearAsString);
            if (1900 > year || year > 2300){
                yearText.setError("Invalid info!");
                flag = false;
            }
        }
        if (durationAsString.length() > 4){
            durationText.setError("Invalid info!");
            flag = false;
        } else {
            duration = Integer.parseInt(durationAsString);
            if (1 > duration || duration > 1000){
                durationText.setError("Invalid info!");
                flag = false;
            }
        }
        // search for onActivityResult
        if (flag) {
            movie.setYear(year);
            movie.setDuration(duration);
            movie.setTitle(titleText.getText() + "");

            Intent returnIntent = new Intent();
            returnIntent.putExtra("year",movie.getYear());
            returnIntent.putExtra("duration",movie.getDuration());
            returnIntent.putExtra("title",movie.getTitle());
            returnIntent.putExtra("id", this.movie.getId());
            returnIntent.putExtra("action", MovieListActivity.ACTION_UPDATE);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }

    public void closeActivity(View view){
        finish();
    }

    public void deleteMovie(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Are you sure you want to delete this movie?");
        alert.setCancelable(false);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent result = new Intent();
                result.putExtra("action", MovieListActivity.ACTION_DELETE);
                result.putExtra("id", movie.getId());
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.create().show();
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
