package com.example.beanandleaf;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import database.DatabaseHelper;
import model.Order;
import model.Store;

public class HistoryFragment extends SimpleFragment {

    @NonNull
    public static Fragment newInstance() {
        return new MerchantHistoryFragment();
    }

    @SuppressWarnings("FieldCanBeLocal")
    private PieChart pcdp;
    private BarChart bcdp;
    private PieChart pcrr;
    private BarChart bcms;

    final private String timePeriod1 = "Today";
    final private String timePeriod2 = "Past Week";
    final private String timePeriod3 = "All Time";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        final DatabaseHelper db = new DatabaseHelper(getActivity());

//      ***************** TOP ROW STATISTICS ****************

        final Spinner timeSpinner = v.findViewById(R.id.time_period_spinner);
        final TextView moneySpentView = v.findViewById(R.id.money_spent);
        final TextView cafIntakeView = v.findViewById(R.id.caffeine_intake);
        final TextView calIntakeView = v.findViewById(R.id.calories_consumed);

        ArrayList<String> timePeriods = new ArrayList<>(
                Arrays.asList(timePeriod1, timePeriod2, timePeriod3)
        );
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, timePeriods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adapter);

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
                String email = pref.getString("email", null);
                String userType = pref.getString("userType", null);
                int userId = db.getUserId(email, userType);

                String timePeriod = parent.getItemAtPosition(position).toString();
                ArrayList<Order> orders = db.getUserOrders(userId);
                double moneySpent = getMoneySpent(orders, timePeriod);
                int cafIntake = getCaffeineIntake(orders, timePeriod);
                int calIntake = getCaloriesIntake(orders, timePeriod);
                moneySpentView.setText("$" + String.format("%.2f", moneySpent));
                cafIntakeView.setText(cafIntake + "mg");
                calIntakeView.setText(Integer.toString(calIntake));
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // IGNORE
            }
        });


//      *********** PIE CHART OF DRINKS PURCHASED ***********

        pcdp = new PieChart(getActivity());
        pcdp = v.findViewById(R.id.pieChartDrinksPurchased);
        pcdp.getDescription().setEnabled(false);
        pcdp.getLegend().setEnabled(false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "amatic_bold.ttf");

        pcdp.setCenterTextTypeface(tf);
        pcdp.setCenterText(generateCenterTextDrinksPurchased());
        pcdp.setCenterTextSize(10f);
        pcdp.setCenterTextTypeface(tf);
        pcdp.setEntryLabelColor(Color.BLACK);
        pcdp.setEntryLabelTypeface(tf);
        pcdp.setEntryLabelTextSize(20f);

        // radius of the center hole in percent of maximum radius
        pcdp.setHoleRadius(35f);
        pcdp.setTransparentCircleRadius(40f);

//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);

        pcdp.setData(generatePieDataDrinksPurchased());


//        *********** BAR CHART STORES & NUMBER OF DRINKS***********
        // create a new chart object
        bcdp = new BarChart(getActivity());
        bcdp = v.findViewById(R.id.barChartStoresDrinks);
        bcdp.getDescription().setEnabled(false);
        //bc.setOnChartGestureListener(this);

//        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
//        mv.setChartView(chart); // For bounds control
//        chart.setMarker(mv);

        bcdp.setDrawGridBackground(false);
        bcdp.setDrawBarShadow(false);

        bcdp.setData(generateBarDataStoresDrinks(1, 20000, 12));

//        Legend l = chart.getLegend();
//        l.setTypeface(tf);

//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tf);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        bcdp.getAxisRight().setEnabled(false);

        XAxis xAxis = bcdp.getXAxis();
        xAxis.setEnabled(false);


        //        *********** BAR CHART STORES & NUMBER OF DRINKS***********
        // create a new chart object
        bcms = new BarChart(getActivity());
        bcms = v.findViewById(R.id.bar_chart_money_spent);
        bcms.getDescription().setEnabled(false);
        //bc.setOnChartGestureListener(this);

//        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
//        mv.setChartView(chart); // For bounds control
//        chart.setMarker(mv);

        bcms.setDrawGridBackground(false);
        bcms.setDrawBarShadow(false);

        bcms.setData(generateBarDataMoneySpent(1, 20000, 12));

//        Legend l = chart.getLegend();
//        l.setTypeface(tf);

//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tf);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        bcms.getAxisRight().setEnabled(false);

        XAxis xAxis1 = bcms.getXAxis();
        xAxis1.setEnabled(false);

//        // programmatically add the chart
//        FrameLayout parent = v.findViewById(R.id.parentLayout);
//        parent.addView(bc);

        return v;
    }

    private SpannableString generateCenterTextDrinksPurchased() {
        SpannableString s = new SpannableString("Drinks Purchased");
        s.setSpan(new RelativeSizeSpan(2f), 0, 16, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 16, s.length(), 0);
        return s;
    }

    private double getMoneySpent(ArrayList<Order> orders, String timePeriod) {
        double moneySpent = 0;
        for (Order order : orders) {
            if (isTimeInPeriod(order.getOrderTime(), timePeriod)) {
                moneySpent += order.getPrice();
            }
        }
        return moneySpent;
    }

    private int getCaffeineIntake(ArrayList<Order> orders, String timePeriod) {
        int cafIntake = 0;
        for (Order order : orders) {
            if (isTimeInPeriod(order.getOrderTime(), timePeriod)) {
                cafIntake += order.getCaffeine();
            }
        }
        return cafIntake;
    }

    private int getCaloriesIntake(ArrayList<Order> orders, String timePeriod) {
        int calIntake = 0;
        for (Order order : orders) {
            if (isTimeInPeriod(order.getOrderTime(), timePeriod)) {
                calIntake += order.getCalories();
            }
        }
        return calIntake;
    }

    private boolean isTimeInPeriod(int time, String timePeriod) {
        // all time automatically returns true
        if (timePeriod.contentEquals(timePeriod3)) {
            return true;
        }
        long daysAgo;
        if (timePeriod.contentEquals(timePeriod2)) {
            daysAgo = 7;
        }
        else {
            daysAgo = 1;
        }

        int timeToCheck = (int) (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(daysAgo));
        if (time - timeToCheck > 0) {
            return true;
        }
        return false;
    }

}

