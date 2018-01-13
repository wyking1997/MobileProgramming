package com.cluj.cinema.marius.cinemacluj;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.IntegerRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;

import com.cluj.cinema.marius.cinemacluj.model.Association;
import com.cluj.cinema.marius.cinemacluj.model.Pair;
import com.cluj.cinema.marius.cinemacluj.util.Globals;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;

public class CinmaChartActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinma_chart);

        Map<String, Integer> arr = new HashMap<>();
        String cinema_key = getIntent().getStringExtra("cinema_key");
        List<Association> ls = new ArrayList<>();
        for (Association a : Globals.getAssociations()){
            if (cinema_key == a.getCinemaKey()) {
                Association as = new Association();
                as.setDate(a.getDate());
                as.setCinemaKey(a.getCinemaKey());
                as.setMovieKey(a.getMovieKey());
                ls.add(as);
            }
        }

        boolean flag;
        do{
            flag = true;
            for (int i = 0; i < ls.size() - 1; i++)
                if (compareDates(ls.get(i).getDate(), ls.get(i + 1).getDate()) == 1){
                    Association as = new Association();
                    as.setDate(ls.get(i).getDate());ls.get(i).setDate(ls.get(i + 1).getDate());ls.get(i + 1).setDate(as.getDate());
                    as.setMovieKey(ls.get(i).getMovieKey());ls.get(i).setMovieKey(ls.get(i + 1).getMovieKey());ls.get(i + 1).setMovieKey(as.getMovieKey());
                    as.setCinemaKey(ls.get(i).getCinemaKey());ls.get(i).setCinemaKey(ls.get(i + 1).getCinemaKey());ls.get(i + 1).setCinemaKey(as.getCinemaKey());
                    flag = false;
                }
        } while (!flag);

        List<Pair<Integer, String>> pp = new ArrayList<>();
        for (int i = ls.size() - 1; i >= 0; i--){
            if (pp.size() == 0 || !pp.get(0).getSecond().equals(ls.get(i).getDate()))
                pp.add(0, new Pair<Integer,String>(1, ls.get(i).getDate()));
            else if (pp.get(0).getSecond().equals(ls.get(i).getDate()))
                pp.get(0).setFirst(pp.get(0).getFirst() + 1);
        }

        while (pp.size() > 5)
            pp.remove(0);

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i < pp.size(); i++) {
            entries.add(new BarEntry(pp.get(i).getFirst(), i));
            labels.add(pp.get(i).getSecond());
        }

        BarDataSet dataset = new BarDataSet(entries, "Sold tikets per day");
        dataset.setColors(ColorTemplate.PASTEL_COLORS);

        BarChart chart = new BarChart(getApplicationContext());
        int i = chart.getLabelFor();
        setContentView(chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
    }

    // compare 2 dates in  string interpretation: yyyy-mm-dd
    // returns
    //      0 if equal
    //      -1 if d1 < d2
    //      1 if d1 > d2
    private int compareDates(String d1, String d2){
        int x1 = Integer.parseInt(d1.substring(0, 4));
        int x2 = Integer.parseInt(d2.substring(0, 4));
        if (x1 < x2)
            return -1;
        if (x1 > x2)
            return 1;

        x1 = Integer.parseInt(d1.substring(5, 7));
        x2 = Integer.parseInt(d2.substring(5, 7));
        if (x1 < x2)
            return -1;
        if (x1 > x2)
            return 1;

        x1 = Integer.parseInt(d1.substring(8));
        x2 = Integer.parseInt(d2.substring(8));
        if (x1 < x2)
            return -1;
        if (x1 > x2)
            return 1;

        return 0;
    }
}
