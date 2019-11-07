package com.example.beanandleaf;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import database.DatabaseHelper;
import model.Order;

public class CustomerHistory extends SimpleFragment {

    @NonNull
    public static Fragment newInstance() {
        return new MerchantHistory();
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
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        String email = pref.getString("email", null);
        String userType = pref.getString("userType", null);
        final DatabaseHelper db = new DatabaseHelper(getActivity());
        int userId = db.getUserId(email, userType);
        final ArrayList<Order> allOrders = db.getUserOrders(userId);
        final ArrayList<Order> dayOrders = getOrdersInPeriod(allOrders, timePeriod1);
        final ArrayList<Order> weekOrders = getOrdersInPeriod(allOrders, timePeriod2);

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

//      *********** PIE CHART OF DRINKS PURCHASED ***********

        pcdp = new PieChart(getActivity());
        pcdp = v.findViewById(R.id.pie_chart_drinks_purchased);
        pcdp.getDescription().setEnabled(false);
        pcdp.getLegend().setEnabled(false);

        final Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "amatic_bold.ttf");

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
        pcdp.setData(generatePieDataDrinksPurchased(dayOrders));


//        *********** BAR CHART NUMBER OF DRINKS BY STORE***********
        // create a new chart object
        bcdp = new BarChart(getActivity());
        bcdp = v.findViewById(R.id.barChartStoresDrinks);
        bcdp.getDescription().setEnabled(false);
        bcdp.setDrawGridBackground(false);
        bcdp.setDrawBarShadow(false);
        bcdp.setExtraOffsets(10,10,10,10);

        bcdp.setData(generateBarDataStoresDrinks(dayOrders, tf));
        bcdp.getAxisRight().setEnabled(false);
        bcdp.getAxisLeft().setGranularity(1f);
        bcdp.getAxisLeft().setGranularityEnabled(true);
        bcdp.getAxisLeft().setTextColor(Color.WHITE);
        bcdp.getAxisLeft().setTextSize(20f);
        bcdp.getAxisLeft().setGridColor(Color.GRAY);
        bcdp.getAxisLeft().setGridLineWidth(0.5f);
        bcdp.getAxisLeft().setAxisLineColor(Color.WHITE);
        bcdp.getAxisLeft().setAxisLineWidth(2f);
        bcdp.getAxisLeft().setTypeface(tf);
        bcdp.getLegend().setEnabled(false);

        XAxis xAxis = bcdp.getXAxis();
        xAxis.setGridColor(Color.GRAY);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisLineWidth(2f);
        xAxis.setGridLineWidth(0.5f);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(20f);
        xAxis.setTypeface(tf);

        Map<String,Integer> storeMap = separateOrdersByStore(dayOrders);
        ArrayList<String> labels = new ArrayList<>();
        for (Map.Entry entry : storeMap.entrySet()) {
            labels.add((String)entry.getKey());
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        //        *********** BAR CHART MONEY SPENT & DAY OF THE WEEK***********
        // create a new chart object
        bcms = new BarChart(getActivity());
        bcms = v.findViewById(R.id.bar_chart_money_spent);
        bcms.getDescription().setEnabled(false);
        bcms.setDrawGridBackground(false);
        bcms.setDrawBarShadow(false);
        bcms.setExtraOffsets(10,10,10,10);

        bcms.setData(generateBarDataMoneySpent(weekOrders,tf));
        bcms.getAxisRight().setEnabled(false);
        bcms.getAxisRight().setEnabled(false);
        bcms.getAxisLeft().setGranularity(1f);
        bcms.getAxisLeft().setGranularityEnabled(true);
        bcms.getAxisLeft().setTextColor(Color.WHITE);
        bcms.getAxisLeft().setTextSize(20f);
        bcms.getAxisLeft().setGridColor(Color.GRAY);
        bcms.getAxisLeft().setGridLineWidth(0.5f);
        bcms.getAxisLeft().setAxisLineColor(Color.WHITE);
        bcms.getAxisLeft().setAxisLineWidth(2f);
        bcms.getAxisLeft().setTypeface(tf);
        bcms.getAxisLeft().setAxisMinimum(0f);
        bcms.getLegend().setEnabled(false);

        XAxis xAxis1 = bcms.getXAxis();
        xAxis1.setGridColor(Color.GRAY);
        xAxis1.setAxisLineColor(Color.WHITE);
        xAxis1.setAxisLineWidth(2f);
        xAxis1.setGridLineWidth(0.5f);
        xAxis1.setGranularity(1f);
        xAxis1.setGranularityEnabled(true);
        xAxis1.setTextColor(Color.WHITE);
        xAxis1.setTextSize(20f);
        xAxis1.setTypeface(tf);
        xAxis1.setSpaceMax(0.4f);


        ArrayList<String> labels1 = new ArrayList<>();
        labels1.add("Mon");
        labels1.add("Tues");
        labels1.add("Wed");
        labels1.add("Thur");
        labels1.add("Fri");
        labels1.add("Sat");
        labels1.add("Sun");
        xAxis1.setValueFormatter(new IndexAxisValueFormatter(labels1));
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);

        final TextView bcmsTitle = v.findViewById(R.id.bcms_title);

        //        *********** DYNAMICALLY UPDATE CHARTS AND STATISTICS ***********

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String timePeriod = parent.getItemAtPosition(position).toString();

                ArrayList<Order> orders = allOrders;
                if (timePeriod.contentEquals(timePeriod1)) {
                    orders = dayOrders;
                }
                else if (timePeriod.contentEquals(timePeriod2)) {
                    orders = weekOrders;
                }

                double moneySpent = 0;
                int cafIntake = 0;
                int calIntake = 0;
                for (Order o : orders) {
                    moneySpent += o.getPrice();
                    cafIntake += o.getCaffeine();
                    calIntake += o.getCalories();
                }
                moneySpentView.setText("$" + String.format("%.2f", moneySpent));
                cafIntakeView.setText(cafIntake + "mg");
                calIntakeView.setText(Integer.toString(calIntake));

                pcdp.setData(generatePieDataDrinksPurchased(orders));
                pcdp.invalidate();
                bcdp.setData(generateBarDataStoresDrinks(orders, tf));
                Map<String,Integer> storeMap = separateOrdersByStore(orders);
                ArrayList<String> labels = new ArrayList<>();
                for (Map.Entry entry : storeMap.entrySet()) {
                    labels.add((String)entry.getKey());
                }
                bcdp.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                bcdp.invalidate();

                if (!timePeriod.contentEquals(timePeriod1)) {
                    bcms.setData(generateBarDataMoneySpent(orders, tf));
                    bcms.invalidate();
                }
                if (timePeriod.contentEquals(timePeriod2) || timePeriod.contentEquals(timePeriod1)) {
                    bcmsTitle.setText("Money Spent in Past Week");
                }
                else {
                    bcmsTitle.setText("Money Spent All Time");
                }


            }
            public void onNothingSelected(AdapterView<?> parent) {
                // IGNORE
            }
        });

        return v;
    }

    private SpannableString generateCenterTextDrinksPurchased() {
        SpannableString s = new SpannableString("Drinks Purchased");
        s.setSpan(new RelativeSizeSpan(2f), 0, 16, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 16, s.length(), 0);
        return s;
    }

    private ArrayList<Order> getOrdersInPeriod(ArrayList<Order> orders, String timePeriod) {
        ArrayList<Order> newOrders = new ArrayList<>();
        for (Order o : orders) {
            if (isTimeInPeriod(o.getOrderTime(), timePeriod)) {
                newOrders.add(o);
            }
        }
        return newOrders;
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

