package com.tzj.tzjcustomview;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class MpChartActivity extends AppCompatActivity {

    private CombinedChart mChart;

    private final int itemcount = 16;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpchart);

        mChart = (CombinedChart) findViewById(R.id.chart1);
        mChart.getDescription().setEnabled(false);//右下角的描述是否显示
        mChart.setBackgroundColor(Color.BLACK);//图表背景颜色

        Legend legend = mChart.getLegend();
        legend.setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴在底部
        xAxis.setDrawGridLines(false);//是否绘制竖线
        xAxis.setTextColor(Color.WHITE);//设置轴标签的颜色。
//        xAxis.setAxisLineColor(Color.GREEN);//X轴颜色
//        xAxis.setAxisLineWidth(5f);//X轴高度
        xAxis.setAxisMinimum(0f);//设置x轴最小值
        xAxis.setGranularityEnabled(false);
//        xAxis.setGranularity(0f);//x轴数据跳过几个
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "";
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);//设置轴标签的颜色。
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(100f);//设置Y轴最大值
        leftAxis.setAxisMinimum(0f);//设置Y轴最小值

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawAxisLine(false);


        //均线
        ArrayList<Entry> ma5Entries = new ArrayList<Entry>();
        for (int index = 1; index <= itemcount; index++) {
            ma5Entries.add(new Entry(index, 70 + index));
        }

        CombinedData data = new CombinedData();
        data.setData(generateCandleData());
        data.setData(generateLineData());

        xAxis.setAxisMaximum(data.getXMax() + 2f);//设置x轴最大值


        mChart.setData(data);
        mChart.invalidate();

    }

    protected CandleData generateCandleData() {

        CandleData d = new CandleData();

        ArrayList<CandleEntry> entries = new ArrayList<CandleEntry>();

        entries.add(new CandleEntry(1f, 90, 70, 85, 75f));
        entries.add(new CandleEntry(2f, 80, 55, 70, 60f));
        entries.add(new CandleEntry(3f, 80, 77, 75, 82f));
        entries.add(new CandleEntry(4f, 99, 59, 83, 86f));
        entries.add(new CandleEntry(5f, 90, 70, 85, 75f));
        entries.add(new CandleEntry(6f, 80, 55, 70, 60f));
        entries.add(new CandleEntry(7f, 80, 77, 75, 82f));
        entries.add(new CandleEntry(8f, 99, 59, 83, 86f));
        entries.add(new CandleEntry(9f, 90, 70, 85, 75f));
        entries.add(new CandleEntry(10f, 80, 55, 70, 60f));
        entries.add(new CandleEntry(11f, 80, 77, 75, 82f));
        entries.add(new CandleEntry(12f, 99, 59, 83, 86f));
        entries.add(new CandleEntry(13f, 90, 70, 85, 75f));
        entries.add(new CandleEntry(14f, 80, 55, 70, 60f));
        entries.add(new CandleEntry(15f, 80, 77, 75, 82f));
        entries.add(new CandleEntry(16f, 99, 59, 83, 86f));


//        for (int index = 0; index < itemcount; index++) {
//            entries.add(new CandleEntry(index + 1f, 90, 70, 85, 75f));
//        }

        CandleDataSet set = new CandleDataSet(entries, "Candle DataSet");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowWidth(0.7f);
        set.setDecreasingColor(Color.GREEN);
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(Color.RED);
        set.setIncreasingPaintStyle(Paint.Style.FILL);
        set.setNeutralColor(Color.RED);
        set.setShadowColorSameAsCandle(true);
        set.setHighlightLineWidth(0.5f);
        set.setHighLightColor(Color.WHITE);
        set.setDrawValues(false);

        d.addDataSet(set);

        return d;
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < itemcount; index++)
            entries.add(new Entry(index + 0.5f, 70 + index));

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
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
}
