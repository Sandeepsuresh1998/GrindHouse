package com.example.beanandleaf;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;


public class MerchantHistoryFragment extends SimpleFragment {

    @NonNull
    public static Fragment newInstance() {
        return new MerchantHistoryFragment();
    }

    @SuppressWarnings("FieldCanBeLocal")
    private PieChart pcdp;
    private BarChart bcdp;
    private PieChart pcrr;
    private BarChart bcms;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_merchant_history, container, false);

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




        //      *********** PIE CHART OF RETURN RATES ***********

        pcrr = new PieChart(getActivity());
        pcrr = v.findViewById(R.id.pieChartReturnRates);
        pcrr.getDescription().setEnabled(false);
        pcrr.getLegend().setEnabled(false);
        pcrr.setUsePercentValues(true);

        pcrr.setCenterTextTypeface(tf);
        pcrr.setCenterText(generateCenterTextReturnRates());
        pcrr.setCenterTextSize(10f);
        pcrr.setCenterTextTypeface(tf);
        pcrr.setEntryLabelColor(Color.BLACK);
        pcrr.setEntryLabelTypeface(tf);
        pcrr.setEntryLabelTextSize(30f);

        // radius of the center hole in percent of maximum radius
        pcrr.setHoleRadius(35f);
        pcrr.setTransparentCircleRadius(40f);

//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);

        pcrr.setData(generatePieDataReturnRates());

 //        *********** BAR CHART MONEY SPENT***********
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

        //bcms.setData(generateBarDataMoneySpent(orders));

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

    private SpannableString generateCenterTextReturnRates() {
        SpannableString s = new SpannableString("Number of Times Customer Returns");
        s.setSpan(new RelativeSizeSpan(2f), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), s.length(), s.length(), 0);
        return s;
    }
}


