package com.example.beanandleaf;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.AxisBase;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FileUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import database.DatabaseHelper;
import model.Order;

@SuppressWarnings({"SameParameterValue", "WeakerAccess"})
public abstract class SimpleFragment extends Fragment {

    Typeface tf;
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tf = Typeface.createFromAsset(getActivity().getAssets(), "amatic_bold.ttf");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected ValueFormatter generateBarChartFormatter(ArrayList<Order> orders) {
        Map<String,Integer> data = separateOrdersByStore(orders);
        final ArrayList<String> labels = new ArrayList<>();
        for (Map.Entry entry : data.entrySet()) {
            labels.add((String)entry.getKey());
        }
        ValueFormatter formatter = new ValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels.get((int)value);
            }
        };
        return formatter;
    }

    protected BarData generateBarDataStoresDrinks(ArrayList<Order> orders) {

        ArrayList<IBarDataSet> sets = new ArrayList<>();
        final ArrayList<BarEntry> entries = new ArrayList<>();
        Map<String,Integer> data = separateOrdersByStore(orders);

        int i = 0;
        for (Map.Entry entry : data.entrySet()) {
            entries.add(new BarEntry(i, (Integer)entry.getValue(), entry.getKey()));
            i++;
        }

        BarDataSet ds = new BarDataSet(entries, getLabelsBCDP(0));
        ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return entries.get((int) value).toString();
            }
        });
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
        entries.add(new BarEntry(1, 20));
        entries.add(new BarEntry(2, 16));
        entries.add(new BarEntry(3, 6));
        entries.add(new BarEntry(4, 22));
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

    protected PieData generatePieDataDrinksPurchased(ArrayList<Order> orders) {

        Map<String,Integer> data = separateOrdersByDrink(orders);
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (Map.Entry entry : data.entrySet()) {
            entries.add(new PieEntry((Integer)entry.getValue(), (String)entry.getKey()));
        }

        PieDataSet ds1 = new PieDataSet(entries, "Drinks Purchased");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(0f);
        ds1.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        PieData d = new PieData(ds1);

        return d;
    }

    private final String[] labelsBCDP = new String[] { "Starbucks", "CB&TL", "Nature's Brew", "DRNK", "Cafe Dulce" };

    private String getLabelsBCDP(int i) {
        return labelsBCDP[i];
    }

    private final String[] labelsBCMS = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

    private String getLabelsBCMS(int i) {
        return labelsBCMS[i];
    }

    protected Map<String,Integer> separateOrdersByDrink(ArrayList<Order> orders) {
        Map<String, Integer> map = new HashMap<>();
        for (Order o : orders) {
            if (map.containsKey(o.getName())) {
                map.put(o.getName(), map.get(o.getName()) + 1);
            }
            else {
                map.put(o.getName(), 1);
            }
        }
        return map;
    }

    protected Map<Integer,Integer> separateOrdersByDrinkID(ArrayList<Order> orders) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Order o : orders) {
            if (map.containsKey(o.getItemID())) {
                map.put(o.getItemID(), map.get(o.getItemID()) + 1);
            }
            else {
                map.put(o.getItemID(), 1);
            }
        }
        return map;
    }

    protected Map<String,Integer> separateOrdersByStore(ArrayList<Order> orders) {
        Map<String, Integer> map = new HashMap<>();
        DatabaseHelper db = new DatabaseHelper(getActivity());
        for (Order o : orders) {
            String storeName = db.getStoreName(o.getItemID());
            if (map.containsKey(storeName)) {
                map.put(storeName, map.get(storeName) + 1);
            }
            else {
                map.put(storeName, 1);
            }
        }
        return map;
    }
}