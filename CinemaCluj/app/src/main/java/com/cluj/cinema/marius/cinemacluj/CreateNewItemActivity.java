package com.cluj.cinema.marius.cinemacluj;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cluj.cinema.marius.cinemacluj.model.Movie;

import java.util.Calendar;

public class CreateNewItemActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_item);
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
                        CreateNewItemActivity.this,
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

    public void createItem(View view){
        EditText titleText = (EditText) findViewById(R.id.titleTextAdd);
        EditText durationText = (EditText) findViewById(R.id.durationTextAdd);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionTextAdd);

        Movie movie = checkFields(titleText, durationText, descriptionText);
        if (movie != null){

            Intent result = new Intent();
            result.putExtra("title", movie.getTitle());
            result.putExtra("duration", movie.getDuration());
            result.putExtra("year", movie.getYear());
            result.putExtra("description", movie.getDescription());
            setResult(Activity.RESULT_OK, result);
            finish();
        }
    }

    public void sendEmail(View view){
        EditText titleText = (EditText) findViewById(R.id.titleTextAdd);
        EditText durationText = (EditText) findViewById(R.id.durationTextAdd);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionTextAdd);

        Movie movie = checkFields(titleText, durationText, descriptionText);
        if (movie != null){
            String[] emails = {"marius.comiati@yahoo.com"};
            String subject = "New movie will be added!";
            String message = movie.toString();

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, emails);
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Choose an Email client :"));
        }


    }

    // if all is good then return a new Movie
    // else returns null
    public Movie checkFields(EditText titleText, EditText durationText, EditText descriptionText){
        String titleAsString = titleText.getText() + "";
        String durationAsString = durationText.getText() + "";
        String descriptionAsString = descriptionText.getText() + "";

        String year = ((TextView) findViewById(R.id.tvDate)).getText() + "";
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
        if (flag)
            return new Movie(year,titleAsString,durationAsInt,descriptionAsString);
        return null;
    }
}
