package com.cluj.cinema.marius.cinemacluj;

import android.content.Intent;
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

        Movie movie = checkFields(titleText, durationText, yearText, descriptionText);
        if (movie != null){
            MovieListActivity.MOVIES.add(movie);
            MovieListActivity.titles.add(movie.getListItemRepresentation());
            MovieListActivity.adapter.notifyDataSetChanged();
            finish();
        }
    }

    public void sendEmail(View view){
        EditText titleText = (EditText) findViewById(R.id.titleTextAdd);
        EditText durationText = (EditText) findViewById(R.id.durationTextAdd);
        EditText yearText = (EditText) findViewById(R.id.yearTextAdd);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionTextAdd);

        Movie movie = checkFields(titleText, durationText, yearText, descriptionText);
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
    public Movie checkFields(EditText titleText, EditText durationText, EditText yearText, EditText descriptionText){
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
        if (flag)
            return new Movie(yearAsInt,titleAsString,durationAsInt,descriptionAsString);
        return null;
    }
}
