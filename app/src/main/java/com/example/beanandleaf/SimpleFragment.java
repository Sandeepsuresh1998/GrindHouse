package com.example.beanandleaf;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FileUtils;

import java.util.ArrayList;

@SuppressWarnings({"SameParameterValue", "WeakerAccess"})
public abstract class SimpleFragment extends Fragment {

    Typeface tf;
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public SimpleFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tf = Typeface.createFromAsset(getActivity().getAssets(), "amatic_bold.ttf");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected BarData generateBarDataStoresDrinks(int dataSets, float range, int count) {

        ArrayList<IBarDataSet> sets = new ArrayList<>();

//        for(int i = 0; i < dataSets; i++) {
//
//            ArrayList<BarEntry> entries = new ArrayList<>();
//
//            for(int j = 0; j < count; j++) {
//                entries.add(new BarEntry(j, (float) (Math.random() * range) + range / 4));
//            }
//
//            BarDataSet ds = new BarDataSet(entries, getLabel(i));
//            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
//            sets.add(ds);
//        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 5));
//        BarDataSet ds0 = new BarDataSet(entries, getLabel(0));
//        ds0.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        sets.add(ds0);
        entries.add(new BarEntry(1, 2));
//        BarDataSet ds1 = new BarDataSet(entries, getLabel(1));
//        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        sets.add(ds1);
        entries.add(new BarEntry(2, 8));
//        BarDataSet ds2 = new BarDataSet(entries, getLabel(2));
//        ds2.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        sets.add(ds2);
        entries.add(new BarEntry(3, 3));
//        BarDataSet ds3 = new BarDataSet(entries, getLabel(3));
//        ds3.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        sets.add(ds3);
        entries.add(new BarEntry(4, 4));
//        BarDataSet ds4 = new BarDataSet(entries, getLabel(4));
//        ds4.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        sets.add(ds4);

        BarDataSet ds = new BarDataSet(entries, getLabelsBCDP(0));
        ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
        sets.add(ds);

        BarData d = new BarData(sets);
        d.setValueTypeface(tf);
        return d;
    }

    protected BarData generateBarDataMoneySpent(int dataSets, float range, int count) {

        ArrayList<IBarDataSet> sets = new ArrayList<>();

//        for(int i = 0; i < dataSets; i++) {
//
//            ArrayList<BarEntry> entries = new ArrayList<>();
//
//            for(int j = 0; j < count; j++) {
//                entries.add(new BarEntry(j, (float) (Math.random() * range) + range / 4));
//            }
//
//            BarDataSet ds = new BarDataSet(entries, getLabel(i));
//            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
//            sets.add(ds);
//        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 12));
//        BarDataSet ds0 = new BarDataSet(entries, getLabel(0));
//        ds0.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        sets.add(ds0);
        entries.add(new BarEntry(1, 20));
//        BarDataSet ds1 = new BarDataSet(entries, getLabel(1));
//        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        sets.add(ds1);
        entries.add(new BarEntry(2, 16));
//        BarDataSet ds2 = new BarDataSet(entries, getLabel(2));
//        ds2.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        sets.add(ds2);
        entries.add(new BarEntry(3, 6));
//        BarDataSet ds3 = new BarDataSet(entries, getLabel(3));
//        ds3.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        sets.add(ds3);
        entries.add(new BarEntry(4, 22));
//        BarDataSet ds4 = new BarDataSet(entries, getLabel(4));
//        ds4.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        sets.add(ds4);
        entries.add(new BarEntry(5, 30));
        entries.add(new BarEntry(6, 26));


        BarDataSet ds = new BarDataSet(entries, getLabelsBCMS(0));
        ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
        sets.add(ds);

        BarData d = new BarData(sets);
        d.setValueTypeface(tf);
        return d;
    }

//
//    protected ScatterData generateScatterData(int dataSets, float range, int count) {
//
//        ArrayList<IScatterDataSet> sets = new ArrayList<>();
//
//        ScatterChart.ScatterShape[] shapes = ScatterChart.ScatterShape.getAllDefaultShapes();
//
//        for(int i = 0; i < dataSets; i++) {
//
//            ArrayList<Entry> entries = new ArrayList<>();
//
//            for(int j = 0; j < count; j++) {
//                entries.add(new Entry(j, (float) (Math.random() * range) + range / 4));
//            }
//
//            ScatterDataSet ds = new ScatterDataSet(entries, getLabel(i));
//            ds.setScatterShapeSize(12f);
//            ds.setScatterShape(shapes[i % shapes.length]);
//            ds.setColors(ColorTemplate.COLORFUL_COLORS);
//            ds.setScatterShapeSize(9f);
//            sets.add(ds);
//        }
//
//        ScatterData d = new ScatterData(sets);
//        d.setValueTypeface(tf);
//        return d;
//    }

    /**
     * generates less data (1 DataSet, 4 values)
     * @return PieData
     */
    protected PieData generatePieDataReturnRates() {

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        entries1.add(new PieEntry(10, "1"));
        entries1.add(new PieEntry(6, "2"));
        entries1.add(new PieEntry(3, "3"));
        entries1.add(new PieEntry(4, "4"));
        entries1.add(new PieEntry(7, "5+"));

        PieDataSet ds1 = new PieDataSet(entries1, "Number of Times Customers Return");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(10f);
        ds1.setValueTypeface(tf);
        ds1.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        //ds1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData d = new PieData(ds1);
        //d.setValueTypeface(tf);

        return d;
    }

    protected PieData generatePieDataDrinksPurchased() {

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        entries1.add(new PieEntry(8, "Vanilla Latte"));
        entries1.add(new PieEntry(10, "Matcha Latte"));
        entries1.add(new PieEntry(3, "Iced Coffee"));
        entries1.add(new PieEntry(4, "Green Tea"));

        PieDataSet ds1 = new PieDataSet(entries1, "Drinks Purchased");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(0f);
        //ds1.setValueTypeface(tf);
        ds1.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        //ds1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData d = new PieData(ds1);
        //d.setValueTypeface(tf);

        return d;
    }

//    protected LineData generateLineData() {
//
//        ArrayList<ILineDataSet> sets = new ArrayList<>();
//        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "sine.txt"), "Sine function");
//        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "cosine.txt"), "Cosine function");
//
//        ds1.setLineWidth(2f);
//        ds2.setLineWidth(2f);
//
//        ds1.setDrawCircles(false);
//        ds2.setDrawCircles(false);
//
//        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
//        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
//
//        // load DataSets from files in assets folder
//        sets.add(ds1);
//        sets.add(ds2);
//
//        LineData d = new LineData(sets);
//        d.setValueTypeface(tf);
//        return d;
//    }
//
//    protected LineData getComplexity() {
//
//        ArrayList<ILineDataSet> sets = new ArrayList<>();
//
//        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "n.txt"), "O(n)");
//        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "nlogn.txt"), "O(nlogn)");
//        LineDataSet ds3 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "square.txt"), "O(n\u00B2)");
//        LineDataSet ds4 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "three.txt"), "O(n\u00B3)");
//
//        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
//        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
//        ds3.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
//        ds4.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);
//
//        ds1.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
//        ds2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[1]);
//        ds3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[2]);
//        ds4.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[3]);
//
//        ds1.setLineWidth(2.5f);
//        ds1.setCircleRadius(3f);
//        ds2.setLineWidth(2.5f);
//        ds2.setCircleRadius(3f);
//        ds3.setLineWidth(2.5f);
//        ds3.setCircleRadius(3f);
//        ds4.setLineWidth(2.5f);
//        ds4.setCircleRadius(3f);
//
//
//        // load DataSets from files in assets folder
//        sets.add(ds1);
//        sets.add(ds2);
//        sets.add(ds3);
//        sets.add(ds4);
//
//        LineData d = new LineData(sets);
//        d.setValueTypeface(tf);
//        return d;
//    }

    private final String[] labelsBCDP = new String[] { "Starbucks", "CB&TL", "Nature's Brew", "DRNK", "Cafe Dulce" };

    private String getLabelsBCDP(int i) {
        return labelsBCDP[i];
    }

    private final String[] labelsBCMS = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

    private String getLabelsBCMS(int i) {
        return labelsBCMS[i];
    }
}