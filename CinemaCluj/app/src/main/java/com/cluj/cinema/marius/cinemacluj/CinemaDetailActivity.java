package com.cluj.cinema.marius.cinemacluj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.cluj.cinema.marius.cinemacluj.model.Cinema;
import com.cluj.cinema.marius.cinemacluj.util.Globals;

public class CinemaDetailActivity extends AppCompatActivity {

    private Cinema cinema;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detail);

        position = getIntent().getIntExtra(CinemaListActivity.EXTRA_CINEMA_POSITION_IN_LIST, -1);
        cinema = Globals.getCINEMA(position);

        EditText cinemaName = (EditText) findViewById(R.id.cinema_name_edit_text_id);
        EditText cinemaAddress = (EditText) findViewById(R.id.cinema_address_edit_text_id);
        EditText cinemaPhone = (EditText) findViewById(R.id.cinema_phone_edit_text_id);

        cinemaName.setText(cinema.getName());
        cinemaAddress.setText(cinema.getAddress());
        cinemaPhone.setText(cinema.getPhoneNumber());
    }
}
