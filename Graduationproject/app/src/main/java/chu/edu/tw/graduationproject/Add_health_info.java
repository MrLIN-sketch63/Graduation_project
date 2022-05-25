package chu.edu.tw.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Add_health_info extends AppCompatActivity {

    TextView current_time;
    EditText low_pressure_input, high_pressure_input, heartbeat_input, before_eat_input, after_eat_input;
    Button btn_add_healthData;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_info);

        current_time = findViewById(R.id.current_time_input);
        current_time.setText(getCurrentTime());

        low_pressure_input = findViewById(R.id.low_pressure_input);
        high_pressure_input = findViewById(R.id.high_pressure_input);
        heartbeat_input = findViewById(R.id.heartbeat_input);
        before_eat_input = findViewById(R.id.before_eat_input);
        after_eat_input = findViewById(R.id.after_eat_input);
        btn_add_healthData = findViewById(R.id.btn_add_healthData);
        myDB = new DBHelper(this);

        btn_add_healthData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkinsertdata =  myDB.addHealthInfo(current_time.getText().toString().trim(),
                        Integer.valueOf(low_pressure_input.getText().toString().trim()),
                        Integer.valueOf(high_pressure_input.getText().toString().trim()),
                        Integer.valueOf(heartbeat_input.getText().toString().trim()),
                        Integer.valueOf(before_eat_input.getText().toString().trim()),
                        Integer.valueOf(after_eat_input.getText().toString().trim()));
                if(checkinsertdata == true){
                    Toast.makeText(Add_health_info.this, "Insert Successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Add_health_info.this, "Insert Failed", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(Add_health_info.this, Health_data_record.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public String getCurrentTime() {
        return new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.getDefault()).format(new Date());
    }
}