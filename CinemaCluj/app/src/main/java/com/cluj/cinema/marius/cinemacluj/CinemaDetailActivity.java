package com.cluj.cinema.marius.cinemacluj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        cinema = Globals.getCinema(position);

        EditText cinemaName = (EditText) findViewById(R.id.cinema_name_edit_text_id);
        EditText cinemaAddress = (EditText) findViewById(R.id.cinema_address_edit_text_id);
        EditText cinemaPhone = (EditText) findViewById(R.id.cinema_phone_edit_text_id);

        cinemaName.setText(cinema.getName());
        cinemaAddress.setText(cinema.getAddress());
        cinemaPhone.setText(cinema.getPhoneNumber());
    }

    public void showStatistics(View view){
        Intent intent = new Intent(this, CinmaChartActivity.class);
        intent.putExtra("cinema_id", cinema.getId());
        startActivity(intent);
    }
}
