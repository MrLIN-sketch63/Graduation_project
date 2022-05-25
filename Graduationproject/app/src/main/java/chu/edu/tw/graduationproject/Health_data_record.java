package chu.edu.tw.graduationproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Health_data_record extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    DBHelper myDB;
    ArrayList<String> current_time, low_pressure, high_pressure, heartbeat, before_eat, after_eat;
    CustomAdapter customAdapter;
    ImageButton btn_delete_healthData, health_lineChart;

    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_data_record);

        recyclerView = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.add_button);
        btn_delete_healthData = findViewById(R.id.btn_delete_healthdata);
        health_lineChart = findViewById(R.id.health_lineChart);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Health_data_record.this, Add_health_info.class);
                startActivity(intent);
            }
        });

        myDB = new DBHelper(this);
        current_time = new ArrayList<>();
        low_pressure = new ArrayList<>();
        high_pressure = new ArrayList<>();
        heartbeat= new ArrayList<>();
        before_eat = new ArrayList<>();
        after_eat = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(Health_data_record.this, this, current_time, low_pressure, high_pressure, heartbeat, before_eat, after_eat);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Health_data_record.this));


        btn_delete_healthData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

        health_lineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Health_data_record.this, Data_lineChart.class);
                startActivity(i);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()) {
                current_time.add(cursor.getString(0));
                low_pressure.add(cursor.getString(1));
                high_pressure.add(cursor.getString(2));
                heartbeat.add(cursor.getString(3));
                before_eat.add(cursor.getString(4));
                after_eat.add(cursor.getString(5));
            }
        }
    }


    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all?");
        builder.setMessage("Are you sure you want to delete all data ?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(Health_data_record.this, Health_data_record.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}