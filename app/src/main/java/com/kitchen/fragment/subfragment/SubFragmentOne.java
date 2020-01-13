package com.kitchen.fragment.subfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kitchen.activity.R;
import com.kitchen.bean.GetTempHum;
import com.kitchen.utils.GlobalData;
import com.kitchen.view.TempControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SubFragmentOne extends Fragment {

    private static final String TAG = "SubFragmentOne";
    private TempControl tempControl;
    private ArrayList<Entry> entryArrayList;
    private ArrayList<BarEntry> barEntryArrayList;
    private CombinedData combinedData;
    private CombinedChart combinedChart;
    private XAxis xAxis;

    public SubFragmentOne(){}
    private GetTempHum getTempHum;
    private GlobalData globalData;
    private OkHttpClient client = new OkHttpClient();
    private List<String> labels = new ArrayList<String>();
    private  final  int count = 10;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        View view = inflater.inflate(R.layout.fg_one,viewGroup,false);
        globalData = (GlobalData) Objects.requireNonNull(getContext()).getApplicationContext();
//        labels.add("1");
//        labels.add("2");
//        labels.add("3");
//        labels.add("4");
//        labels.add("5");
//        labels.add("6");
//        labels.add("7");
//        labels.add("8");
//        labels.add("9");
//        labels.add("10");

        drawerChart(view);
        tempControl = view.findViewById(R.id.temp_control);
        // 设置几格代表温度1度
        tempControl.setAngleRate(1);
        //设置指针是否可旋转
        tempControl.setCanRotate(true);
        return view;
    }

    private void drawerChart(View view) {
        combinedChart = view.findViewById(R.id.combine_chart);
        combinedChart.getDescription().setEnabled(false);
        combinedChart.setBackgroundColor(Color.WHITE);
        combinedChart.setDrawGridBackground(false);
        combinedChart.setDrawBarShadow(false);
        combinedChart.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE,
                CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        Legend l = combinedChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index < 0 || index >= labels.size()) {
                    return "";
                }
                return labels.get(index);
            }
        });

        combinedData = new CombinedData();
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        entryArrayList = new ArrayList<>();

        for (int index = 0; index < count; index++)
            entryArrayList.add(new Entry(index + 0.25f, (float) getTempHum.getData().get(index).getTemp()));

        LineDataSet set = new LineDataSet(entryArrayList, "温度");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    protected float getRandom(float range, float start) {
        return (float) (Math.random() * range) + start;
    }
    private BarData generateBarData() {

        barEntryArrayList = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        for (int index = 0; index < count; index++) {
            barEntryArrayList.add(new BarEntry(0, (float) getTempHum.getData().get(index).getHum()));
        }

        BarDataSet set1 = new BarDataSet(barEntryArrayList, "湿度");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "");

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1,set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }

    @Override
    public void onResume() {
        super.onResume();
        final String userID = globalData.getUserID();
        tempControl.setTemp(13);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(userID == null)
                        return ;
                    runGetHum("http://121.199.22.121:8080/kit/getHum?userID="+userID);

                    combinedData.setData(generateLineData());
                    combinedData.setData(generateBarData());
                    combinedChart.setData(combinedData);
                    for(int i = 0;i<10;i++) {
                        String string = getTempHum.getData().get(i).getHumTime();
                        String[] result = string.split(" ");
                        Log.e(TAG, "run: "+ Arrays.toString(result));
                        labels.add(result[result.length -1 ]);
                    }
                    xAxis.setAxisMaximum(combinedData.getXMax() + 0.25f);

                    combinedChart.setData(combinedData);
                    combinedChart.invalidate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void runGetHum(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String result = Objects.requireNonNull(response.body()).string();
            Log.e(TAG, "getTempHum: " + result);
            getTempHum = globalData.gson.fromJson(result, GetTempHum.class);
            if(getTempHum.getData() == null)
                return;
            tempControl.setTemp((int) getDataBean().getTemp());
            globalData.setHumidity((int) getDataBean().getHum());
        }
    }

    private GetTempHum.DataBean getDataBean() {
        return getTempHum.getData().get(0);
    }
}
