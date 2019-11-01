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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;


public class MerchantHistoryFragment extends SimpleFragment {

    @NonNull
    public static Fragment newInstance() {
        return new MerchantHistoryFragment();
    }

    @SuppressWarnings("FieldCanBeLocal")
    private PieChart chart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_merchant_history, container, false);

        chart = v.findViewById(R.id.pieChart1);
//        chart.getDescription().setEnabled(false);

//        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
//
//        chart.setCenterTextTypeface(tf);
//        chart.setCenterText(generateCenterText());
//        chart.setCenterTextSize(10f);
//        chart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        chart.setHoleRadius(45f);
        chart.setTransparentCircleRadius(50f);

//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);

        chart.setData(generatePieData());

        return v;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Revenues\nQuarters 2015");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }


}

//import android.graphics.Typeface;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.listener.ChartTouchListener;
//import com.github.mikephil.charting.listener.OnChartGestureListener;
////import com.xxmassdeveloper.mpchartexample.R;
////import com.xxmassdeveloper.mpchartexample.custom.MyMarkerView;
//
//public class MerchantHistoryFragment extends SimpleFragment implements OnChartGestureListener {
//
//    @NonNull
//    public static Fragment newInstance() {
//        return new MerchantHistoryFragment();
//    }
//
//    private BarChart chart;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_merchant_history, container, false);
//
//        // create a new chart object
//        chart = new BarChart(getActivity());
//        chart.getDescription().setEnabled(false);
//        chart.setOnChartGestureListener(this);
//
////        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
////        mv.setChartView(chart); // For bounds control
////        chart.setMarker(mv);
//
//        chart.setDrawGridBackground(false);
//        chart.setDrawBarShadow(false);
//
//        Typeface tf = Typeface.createFromFile("/Users/mallikajain/Desktop/GrindHouse/app/src/main/res/font/amatic_bold.ttf");
//
//        chart.setData(generateBarData(1, 20000, 12));
//
//        Legend l = chart.getLegend();
//        l.setTypeface(tf);
//
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tf);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        chart.getAxisRight().setEnabled(false);
//
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setEnabled(false);
//
//        // programmatically add the chart
//        FrameLayout parent = v.findViewById(R.id.parentLayout);
//        parent.addView(chart);
//
//        return v;
//    }
//
//    @Override
//    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//        Log.i("Gesture", "START");
//    }
//
//    @Override
//    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//        Log.i("Gesture", "END");
//        chart.highlightValues(null);
//    }
//
//    @Override
//    public void onChartLongPressed(MotionEvent me) {
//        Log.i("LongPress", "Chart long pressed.");
//    }
//
//    @Override
//    public void onChartDoubleTapped(MotionEvent me) {
//        Log.i("DoubleTap", "Chart double-tapped.");
//    }
//
//    @Override
//    public void onChartSingleTapped(MotionEvent me) {
//        Log.i("SingleTap", "Chart single-tapped.");
//    }
//
//    @Override
//    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
//        Log.i("Fling", "Chart fling. VelocityX: " + velocityX + ", VelocityY: " + velocityY);
//    }
//
//    @Override
//    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
//        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
//    }
//
//    @Override
//    public void onChartTranslate(MotionEvent me, float dX, float dY) {
//        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
//    }
//
//}

