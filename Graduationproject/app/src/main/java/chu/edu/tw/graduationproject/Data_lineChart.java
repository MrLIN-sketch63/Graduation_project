package chu.edu.tw.graduationproject;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class Data_lineChart extends AppCompatActivity {

    LineChart lineChart, lineChart1;
    DBHelper myDB;
    SQLiteDatabase sqLiteDatabase;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    LineData lineData, lineData1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_line_chart);

        lineChart = findViewById(R.id.lineChart);
        lineChart1 = findViewById(R.id.lineChart1);

        myDB = new DBHelper(this);
        sqLiteDatabase = myDB.getWritableDatabase();

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setTouchEnabled(true);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.animateX(2000);

        lineChart1.setDragEnabled(true);
        lineChart1.setScaleEnabled(true);
        lineChart1.setTouchEnabled(true);
        lineChart1.getDescription().setEnabled(false);
        lineChart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart1.getAxisRight().setEnabled(false);
        lineChart1.animateX(2000);

 //       lineChart.setBackgroundColor(0x30000000);


        LineDataSet lineDataSet1 = new LineDataSet(getDataValues(),"收縮壓");
        LineDataSet lineDataSet2 = new LineDataSet(getDataValues1(),"舒張壓");
        LineDataSet lineDataSet3 = new LineDataSet(getDataValues2(),"心跳");
        LineDataSet lineDataSet4 = new LineDataSet(getDataValues3(),"飯前血糖");
        LineDataSet lineDataSet5 = new LineDataSet(getDataValues4(),"飯後血糖");

        lineDataSet1.setColor(Color.rgb(106,90,205));
        lineDataSet1.setValueTextSize(12f);
        lineDataSet1.setLineWidth(2);

        lineDataSet2.setColor(Color.rgb(156,102,31));
        lineDataSet2.setValueTextSize(12f);
        lineDataSet2.setLineWidth(2);

        lineDataSet3.setColor(Color.rgb(0,255,255));
        lineDataSet3.setValueTextSize(12f);
        lineDataSet3.setLineWidth(2);

        lineDataSet4.setColor(Color.rgb(0,201,87));
        lineDataSet4.setValueTextSize(12f);
        lineDataSet4.setLineWidth(2);

        lineDataSet5.setColor(Color.rgb(252,230,201));
        lineDataSet5.setValueTextSize(12f);
        lineDataSet5.setLineWidth(2);

        dataSets.clear();
        lineData = new LineData(lineDataSet1,lineDataSet2,lineDataSet3);
        lineData1 = new LineData(lineDataSet4, lineDataSet5);
        lineChart.clear();
        lineChart1.clear();
        lineChart.setData(lineData);
        lineChart1.setData(lineData1);
        lineChart.invalidate();
        lineChart1.invalidate();



        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMaximum(180f);
        yAxis.setAxisMinimum(60f);
        //yAxis.setDrawLabels(false);
        LimitLine yLimitLine = new LimitLine(160f,"臨界值");
        yAxis.addLimitLine(yLimitLine);
        yLimitLine.setLineColor(Color.rgb(178,34,34));


        XAxis xAxis1 = lineChart1.getXAxis();
        xAxis1.setTextSize(11f);
        xAxis1.setGranularity(1f);

        YAxis yAxis1 = lineChart1.getAxisLeft();
        yAxis1.setAxisMaximum(120f);
        yAxis1.setAxisMinimum(0f);
        //yAxis.setDrawLabels(false);
        LimitLine yLimitLine1 = new LimitLine(100f,"臨界值");
        yAxis1.addLimitLine(yLimitLine1);
        yLimitLine.setLineColor(Color.rgb(178,34,34));


  //      xAxis.setGranularity(60f);

 /*       xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("MM-dd HH:mm");
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                long timestamp = (long) (value*1000l);
                return mFormat.format(timestamp);
            }
        });*/
}

    private ArrayList<Entry> getDataValues() {
        ArrayList<Entry> dataVales = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("healthdata", null, null, null, null, null, null);

        for(int i = 0; i<cursor.getCount(); i++) {
            cursor.moveToNext();
            dataVales.add(new Entry(i, cursor.getFloat(1)));
        }
        return dataVales;
    }

    private ArrayList<Entry> getDataValues1() {
        ArrayList<Entry> dataVales1 = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("healthdata", null, null, null, null, null, null);

        for(int i = 0; i<cursor.getCount(); i++) {
            cursor.moveToNext();
            dataVales1.add(new Entry(i, cursor.getFloat(2)));
        }
        return dataVales1;
    }

    private ArrayList<Entry> getDataValues2() {
        ArrayList<Entry> dataVales2 = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("healthdata", null, null, null, null, null, null);

        for(int i = 0; i<cursor.getCount(); i++) {
            cursor.moveToNext();
            dataVales2.add(new Entry(i, cursor.getFloat(3)));
            
        }
        return dataVales2;
    }

    private ArrayList<Entry> getDataValues3() {
        ArrayList<Entry> dataVales3 = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("healthdata", null, null, null, null, null, null);

        for(int i = 0; i<cursor.getCount(); i++) {
            cursor.moveToNext();
            dataVales3.add(new Entry(i, cursor.getFloat(4)));
        }
        return dataVales3;
    }

    private ArrayList<Entry> getDataValues4() {
        ArrayList<Entry> dataVales4 = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("healthdata", null, null, null, null, null, null);

        for(int i = 0; i<cursor.getCount(); i++) {
            cursor.moveToNext();
            dataVales4.add(new Entry(i, cursor.getFloat(5)));
        }
        return dataVales4;
    }

}
