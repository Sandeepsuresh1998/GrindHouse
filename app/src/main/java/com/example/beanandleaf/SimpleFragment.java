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
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import database.DatabaseHelper;
import model.Order;
import model.Pair;

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

    protected BarData generateBarDataStoresDrinks(ArrayList<Order> orders, Typeface tf) {

        ArrayList<IBarDataSet> sets = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        Map<String,Integer> data = separateOrdersByStore(orders);

        int i = 0;
        for (Map.Entry entry : data.entrySet()) {
            entries.add(new BarEntry(i, (Integer)entry.getValue(), entry.getKey()));
            i++;
        }

        BarDataSet ds = new BarDataSet(entries, null);
        ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds.setValueTextColor(Color.WHITE);
        ds.setValueTextSize(25f);
        ds.setValueTypeface(tf);
        ds.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "" + (int) value;
            }
        });
        sets.add(ds);
        BarData d = new BarData(sets);
        d.setValueTypeface(tf);
        return d;
    }

    protected BarData generateBarDataMoneySpent(ArrayList<Order> orders, Typeface tf) {
        ArrayList<IBarDataSet> sets = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<Pair<String,Float>> data = separateOrdersByDay(orders);

        int i = 0;
        for (Pair<String,Float> entry : data) {
            entries.add(new BarEntry(i, entry.getValue(), entry.getKey()));
            i++;
        }
        BarDataSet ds = new BarDataSet(entries, null);
        ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds.setValueTextColor(Color.WHITE);
        ds.setValueTextSize(25f);
        ds.setValueTypeface(tf);
        ds.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.2f",value);
            }
        });
        sets.add(ds);
        BarData d = new BarData(sets);
        d.setValueTypeface(tf);
        d.setBarWidth(0.7f);
        return d;
    }

    /**
     * generates less data (1 DataSet, 4 values)
     * @return PieData
     */
    protected PieData generatePieDataReturnRates(ArrayList<Order> orders) {

        Map<Integer,Integer> map = separateOrdersByRepeatVisits(orders);
        ArrayList<Pair<String,Integer>> data = countRepeatVisits(map);
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (Pair<String,Integer> entry : data) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet ds1 = new PieDataSet(entries, "Number of Times Customers Return");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(20f);
        ds1.setValueTypeface(tf);
        ds1.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        ds1.setValueLinePart1OffsetPercentage(130.f);
        ds1.setValueTypeface(Typeface.createFromAsset(getActivity().getAssets(), "amatic_bold.ttf"));
        ds1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "(" + (int) value + ")";
            }
        });

        PieData d = new PieData(ds1);

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
        ds1.setValueTextSize(25f);
        ds1.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        ds1.setValueTypeface(Typeface.createFromAsset(getActivity().getAssets(), "amatic_bold.ttf"));
        ds1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "" + (int) value;
            }
        });
        PieData d = new PieData(ds1);

        return d;
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

    protected Map<Integer,Integer> separateOrdersByRepeatVisits(ArrayList<Order> orders) {
        Map<Integer, Integer> map = new HashMap<>();
        DatabaseHelper db = new DatabaseHelper(getActivity());
        for (Order o : orders) {
            int userID = db.getUserIDfromOrderID(o.getOrderID());
            if (map.containsKey(userID)) {
                map.put(userID, map.get(userID) + 1);
            }
            else {
                map.put(userID, 1);
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

    protected ArrayList<Pair<String,Float>> separateOrdersByDay(ArrayList<Order> orders) {
        ArrayList<Pair<String,Float>> map = new ArrayList<>();
        map.add(new Pair("Monday",0f));
        map.add(new Pair("Tuesday",0f));
        map.add(new Pair("Wednesday",0f));
        map.add(new Pair("Thursday",0f));
        map.add(new Pair("Friday",0f));
        map.add(new Pair("Saturday",0f));
        map.add(new Pair("Sunday",0f));
        for (Order o : orders) {
            String dow = getDayOfWeekFromMillis(o.getOrderTime());
            if (!dow.contentEquals("Unknown")) {
                for (Pair<String,Float> p : map) {
                    if (p.getKey().contentEquals(dow)) {
                        p.setValue(p.getValue() + (float) o.getPrice());
                    }
                }
            }
        }
        return map;
    }

    private ArrayList<Pair<String,Integer>> countRepeatVisits(Map<Integer,Integer> map) {
        ArrayList<Pair<String,Integer>> repeatVisits = new ArrayList<>();
        repeatVisits.add(new Pair("1",0));
        repeatVisits.add(new Pair("2",0));
        repeatVisits.add(new Pair("3",0));
        repeatVisits.add(new Pair("4",0));
        repeatVisits.add(new Pair("5+",0));
        for (Map.Entry entry : map.entrySet()) {
            if ((int)entry.getValue() == 1) {
                repeatVisits.get(0).setValue(repeatVisits.get(0).getValue() + 1);
            }
            else if ((int)entry.getValue() == 2) {
                repeatVisits.get(1).setValue(repeatVisits.get(1).getValue() + 1);
            }
            else if ((int)entry.getValue() == 3) {
                repeatVisits.get(2).setValue(repeatVisits.get(2).getValue() + 1);
            }
            else if ((int)entry.getValue() == 4) {
                repeatVisits.get(3).setValue(repeatVisits.get(3).getValue() + 1);
            }
            else if ((int)entry.getValue() >= 5) {
                repeatVisits.get(4).setValue(repeatVisits.get(4).getValue() + 1);
            }
        }
        ListIterator<Pair<String,Integer>> iter = repeatVisits.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getValue() == 0) {
               iter.remove();
            }
        }
        return repeatVisits;
    }

    private String getDayOfWeekFromMillis(long msecs) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        cal.setTimeInMillis(msecs);
        int dow = cal.get(Calendar.DAY_OF_WEEK);

        switch (dow) {
            case Calendar.MONDAY:
                return "Tuesday";
            case Calendar.TUESDAY:
                return "Wednesday";
            case Calendar.WEDNESDAY:
                return "Thursday";
            case Calendar.THURSDAY:
                return "Friday";
            case Calendar.FRIDAY:
                return "Saturday";
            case Calendar.SATURDAY:
                return "Sunday";
            case Calendar.SUNDAY:
                return "Monday";
        }
        return "Unknown";
    }
}