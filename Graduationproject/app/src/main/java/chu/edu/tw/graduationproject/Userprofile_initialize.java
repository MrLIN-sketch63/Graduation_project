package chu.edu.tw.graduationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Userprofile_initialize extends AppCompatActivity {

    EditText nickname, fullname, age, gender, emergency_phone, address;
    Button save_data;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile_initialize);


        nickname = findViewById(R.id.nickname);
        fullname = findViewById(R.id.fullname);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        emergency_phone = findViewById(R.id.emergency_phone);
        address = findViewById(R.id.address);

        save_data = findViewById(R.id.btn_save_data);
        myDB = new DBHelper(this);

        save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nicknameTXT = nickname.getText().toString();
                String fullnameTXT = fullname.getText().toString();
                String ageTXT = age.getText().toString();
                String genderTXT = gender.getText().toString();
                String emergency_phoneTXT = emergency_phone.getText().toString();
                String addressTXT = address.getText().toString();

                Boolean checkInsertData = myDB.insert_userprofile_Data(nicknameTXT, fullnameTXT, ageTXT, genderTXT, emergency_phoneTXT, addressTXT);
                if(checkInsertData == true){
                    Toast.makeText(Userprofile_initialize.this, "Insert Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Userprofile_initialize.this, LoginActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(Userprofile_initialize.this, "Insert Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

/*        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDB.getdata();
                if(res.getCount() == 0) {
                    Toast.makeText(Userprofile_initialize.this, "No data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()) {
                    buffer.append("Nickname :"+res.getString(0) + "\n");
                    buffer.append("Fullname :" + res.getString(1) + "\n");
                    buffer.append("Age :" + res.getString(2) + "\n");
                    buffer.append("Gender :" + res.getString(3) + "\n");
                    buffer.append("Emergency_phone :" + res.getString(4) + "\n");
                    buffer.append("Address :" + res.getString(5) + "\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Userprofile_initialize.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });*/

    }
}