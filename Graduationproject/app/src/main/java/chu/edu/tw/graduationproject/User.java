package chu.edu.tw.graduationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class User extends AppCompatActivity {
    DrawerLayout drawerLayout;
    DBHelper myDB;
    TextView nickname, fullname, age, gender, emergency_phone, address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        drawerLayout = findViewById(R.id.drawer_layout);
        myDB = new DBHelper(this);
        savedata();

        nickname = findViewById(R.id.Tx_1);
        fullname = findViewById(R.id.Tx_2);
        age = findViewById(R.id.Tx_3);
        gender = findViewById(R.id.Tx_4);
        emergency_phone = findViewById(R.id.Tx_5);
        address = findViewById(R.id.Tx_6);


    }

    public void ClickMenu(View view) {
        HomeActivity.openDrawer(drawerLayout); }

    public void ClickLogo(View view) {
        HomeActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        HomeActivity.redirectActivity(this, HomeActivity.class);
    }

    public void ClickDashboard(View view) {
        HomeActivity.redirectActivity(this, Dashboard.class);
    }

    public void ClickAboutUs(View view) {
        HomeActivity.redirectActivity(this, User.class);
    }

    public void ClickLogout(View view) {
        HomeActivity.redirectActivity(this, LoginActivity.class);
    }

    public void ClickExit(View view) {
        HomeActivity.exit(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        HomeActivity.closeDrawer(drawerLayout);
    }


    public void btn_edit(View view) {
        String nicknameTXT = nickname.getText().toString();
        String fullnameTXT = fullname.getText().toString();
        String ageTXT = age.getText().toString();
        String genderTXT = gender.getText().toString();
        String emergency_phoneTXT = emergency_phone.getText().toString();
        String addressTXT = address.getText().toString();

        Intent i = new Intent(this, Modify_profile.class);
        i.putExtra("nickname", nicknameTXT);
        i.putExtra("fullname", fullnameTXT);
        i.putExtra("age", ageTXT);
        i.putExtra("gender", genderTXT);
        i.putExtra("emergency", emergency_phoneTXT);
        i.putExtra("address", addressTXT);
        startActivity(i);
    }

    public void savedata(){
        Cursor c = myDB.getdata();
        if(c.moveToFirst()) {
            String str1, str2, str3, str4, str5, str6;
            str1 = c.getString(0);
            str2 = c.getString(1);
            str3 = c.getString(2);
            str4 = c.getString(3);
            str5 = c.getString(4);
            str6 = c.getString(5);
            TextView textView1 =  findViewById(R.id.Tx_1);
            TextView textView2 =  findViewById(R.id.Tx_2);
            TextView textView3 =  findViewById(R.id.Tx_3);
            TextView textView4 =  findViewById(R.id.Tx_4);
            TextView textView5 =  findViewById(R.id.Tx_5);
            TextView textView6 =  findViewById(R.id.Tx_6);
            textView1.setText(str1);
            textView2.setText(str2);
            textView3.setText(str3);
            textView4.setText(str4);
            textView5.setText(str5);
            textView6.setText(str6);
        }
    }





}