package com.cluj.cinema.marius.cinemacluj;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cluj.cinema.marius.cinemacluj.model.Movie;

import java.util.Calendar;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;
    private int position;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        position = getIntent().getIntExtra(MovieListActivity.EXTRA_MOVIE_POSITION_IN_LIST, -1);
        movie = MovieListActivity.MOVIES.get(position);

        EditText titleText = (EditText) findViewById(R.id.titleTextAdd);
        TextView yearText = (TextView) findViewById(R.id.tvDate);
        EditText durationText = (EditText) findViewById(R.id.durationTextAdd);
        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);

        titleText.setText(movie.getTitle());
        durationText.setText("" + movie.getDuration());
        yearText.setText(movie.getYear());
        descriptionTextView.setText(movie.getDescription());

        initializeYearSelection();
    }

    public void initializeYearSelection(){
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MovieDetailActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                mDisplayDate.setText(date);
            }
        };
    }

    public void saveInformation(View view){
        EditText titleText = (EditText) findViewById(R.id.titleTextAdd);
        TextView yearText = (TextView) findViewById(R.id.tvDate);
        EditText durationText = (EditText) findViewById(R.id.durationTextAdd);
        String year = "" + yearText.getText();
        String durationAsString = "" + durationText.getText();

        boolean flag = true;
        int duration = -1;

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
            Intent returnIntent = new Intent();
            returnIntent.putExtra("year",year);
            returnIntent.putExtra("duration",duration);
            returnIntent.putExtra("title",titleText.getText() + "");
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
