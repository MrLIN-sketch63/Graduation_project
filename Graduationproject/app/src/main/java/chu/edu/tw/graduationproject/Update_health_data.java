package chu.edu.tw.graduationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Update_health_data extends AppCompatActivity {

    TextView current_time;
    EditText low_pressure, high_pressure, heart_beat, before_eat, after_eat;
    Button btn_update_healthData, btn_delete;
    String time, low, high, beat, before, after;
    DBHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_health_data);

        current_time = findViewById(R.id.current_time_input2);
        low_pressure = findViewById(R.id.low_pressure_input2);
        high_pressure = findViewById(R.id.high_pressure_input2);
        heart_beat = findViewById(R.id.heartbeat_input2);
        before_eat = findViewById(R.id.before_eat_input2);
        after_eat = findViewById(R.id.after_eat_input2);
        btn_update_healthData = findViewById(R.id.btn_update_healthData);
        btn_delete = findViewById(R.id.btn_delete);
        myDB = new DBHelper(this);

        getAndSetIntentData();

        btn_update_healthData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_timeTXT = current_time.getText().toString();
                String low_pressureTXT = low_pressure.getText().toString();
                String high_pressureTXT = high_pressure.getText().toString();
                String heart_beatTXT = heart_beat.getText().toString();
                String before_eatTXT = before_eat.getText().toString();
                String after_eatTXT = after_eat.getText().toString();

                Boolean checkUpdateInfo = myDB.UpdateHealthData(current_timeTXT, low_pressureTXT, high_pressureTXT, heart_beatTXT, before_eatTXT, after_eatTXT);
                if(checkUpdateInfo == true) {
                    Toast.makeText(Update_health_data.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Update_health_data.this, Health_data_record.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Update_health_data.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

    }


    void getAndSetIntentData(){
        if(getIntent().hasExtra("time") && getIntent().hasExtra("low") && getIntent().hasExtra("high")
                && getIntent().hasExtra("beat") && getIntent().hasExtra("before") && getIntent().hasExtra("after")) {
            //getting data from intent
            time = getIntent().getStringExtra("time");
            low = getIntent().getStringExtra("low");
            high = getIntent().getStringExtra("high");
            beat = getIntent().getStringExtra("beat");
            before = getIntent().getStringExtra("before");
            after = getIntent().getStringExtra("after");

            //setting Intent data
            current_time.setText(time);
            low_pressure.setText(low);
            high_pressure.setText(high);
            heart_beat.setText(beat);
            before_eat.setText(before);
            after_eat.setText(after);

        }
        else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete it ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Boolean checkDelete = myDB.deleteOneRow(time);
                if(checkDelete == true) {
                    Toast.makeText(Update_health_data.this, "Delete Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Update_health_data.this, Health_data_record.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Update_health_data.this, "Delete Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}