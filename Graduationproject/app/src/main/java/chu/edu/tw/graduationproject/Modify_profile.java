package chu.edu.tw.graduationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Modify_profile extends AppCompatActivity {
    TextView nickname;
    EditText fullname, age, gender, email, emergency_phone1, emergency_phone2, emergency_phone3, address;
    Button save;
    String nickname_edit, fullname_edit, age_edit, gender_edit, email_edit, emergency_phone1_edit, emergency_phone2_edit, emergency_phone3_edit, address_edit;
    DBHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        nickname = findViewById(R.id.nickname);
        fullname = findViewById(R.id.fullname);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        email = findViewById(R.id.email);
        emergency_phone1 = findViewById(R.id.emergency_phone1);
        emergency_phone2 = findViewById(R.id.emergency_phone2);
        emergency_phone3 = findViewById(R.id.emergency_phone3);
        address = findViewById(R.id.address);

        save = findViewById(R.id.btn_save);
        myDB = new DBHelper(this);

        getAndSetUserData();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nicknameTXT = nickname.getText().toString();
                String fullnameTXT = fullname.getText().toString();
                String ageTXT = age.getText().toString();
                String genderTXT = gender.getText().toString();
                String emailTXT = email.getText().toString();
                String emergency_phone1TXT = emergency_phone1.getText().toString();
                String emergency_phone2TXT = emergency_phone2.getText().toString();
                String emergency_phone3TXT = emergency_phone3.getText().toString();
                String addressTXT = address.getText().toString();
                Boolean checkUpdateData = myDB.UpdateData(nicknameTXT, fullnameTXT, ageTXT, genderTXT, emailTXT,
                        emergency_phone1TXT, emergency_phone2TXT, emergency_phone3TXT, addressTXT);
                if(checkUpdateData == true){
                    Toast.makeText(Modify_profile.this, "Update Successful", Toast.LENGTH_SHORT).show();

                    Cursor c = myDB.getdata();
                    if(c.moveToFirst()) {
                        String str1, str2, str3, str4, str5, str6, str7, str8, str9;
                        str1 = c.getString(0);
                        str2 = c.getString(1);
                        str3 = c.getString(2);
                        str4 = c.getString(3);
                        str5 = c.getString(4);
                        str6 = c.getString(5);
                        str7 = c.getString(6);
                        str8 = c.getString(7);
                        str9 = c.getString(8);
                        TextView textView1 = LayoutInflater.from(Modify_profile.this).inflate(R.layout.activity_user, null).findViewById(R.id.Tx_1);
                        TextView textView2 = LayoutInflater.from(Modify_profile.this).inflate(R.layout.activity_user, null).findViewById(R.id.Tx_2);
                        TextView textView3 = LayoutInflater.from(Modify_profile.this).inflate(R.layout.activity_user, null).findViewById(R.id.Tx_3);
                        TextView textView4 = LayoutInflater.from(Modify_profile.this).inflate(R.layout.activity_user, null).findViewById(R.id.Tx_4);
                        TextView textView5 = LayoutInflater.from(Modify_profile.this).inflate(R.layout.activity_user, null).findViewById(R.id.Tx_5);
                        TextView textView6 = LayoutInflater.from(Modify_profile.this).inflate(R.layout.activity_user, null).findViewById(R.id.Tx_6);
                        TextView textView7 = LayoutInflater.from(Modify_profile.this).inflate(R.layout.activity_user, null).findViewById(R.id.Tx_7);
                        TextView textView8 = LayoutInflater.from(Modify_profile.this).inflate(R.layout.activity_user, null).findViewById(R.id.Tx_8);
                        TextView textView9 = LayoutInflater.from(Modify_profile.this).inflate(R.layout.activity_user, null).findViewById(R.id.Tx_9);
                        textView1.setText(str1);
                        textView2.setText(str2);
                        textView3.setText(str3);
                        textView4.setText(str4);
                        textView5.setText(str5);
                        textView6.setText(str6);
                        textView7.setText(str7);
                        textView8.setText(str8);
                        textView9.setText(str9);
                    }
                    Intent i = new Intent(Modify_profile.this, User.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(Modify_profile.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }

        });

/*        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDB.getdata();
                if(res.getCount() == 0) {
                    Toast.makeText(Modify_profile.this, "No data", Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Modify_profile.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });*/

    }



    public void btn_edit(View view) {
        Intent i = new Intent(this, User.class);
        startActivity(i);
    }

    void getAndSetUserData(){
        if(getIntent().hasExtra("nickname") && getIntent().hasExtra("fullname") && getIntent().hasExtra("age")
                && getIntent().hasExtra("gender") && getIntent().hasExtra("email") && getIntent().hasExtra("emergency1")
                && getIntent().hasExtra("emergency2") && getIntent().hasExtra("emergency3") && getIntent().hasExtra("address")) {
            //getting data from intent
            nickname_edit = getIntent().getStringExtra("nickname");
            fullname_edit = getIntent().getStringExtra("fullname");
            age_edit = getIntent().getStringExtra("age");
            gender_edit = getIntent().getStringExtra("gender");
            email_edit = getIntent().getStringExtra("email");
            emergency_phone1_edit = getIntent().getStringExtra("emergency1");
            emergency_phone2_edit = getIntent().getStringExtra("emergency2");
            emergency_phone3_edit = getIntent().getStringExtra("emergency3");
            address_edit = getIntent().getStringExtra("address");

            //setting Intent data
            nickname.setText(nickname_edit);
            fullname.setText(fullname_edit);
            age.setText(age_edit);
            gender.setText(gender_edit);
            email.setText(email_edit);
            emergency_phone1.setText(emergency_phone1_edit);
            emergency_phone2.setText(emergency_phone2_edit);
            emergency_phone3.setText(emergency_phone3_edit);
            address.setText(address_edit);

        }
        else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

}