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
    TextView nickname, fullname, age, gender, email, emergency_phone1, emergency_phone2, emergency_phone3, address;


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
        email = findViewById(R.id.Tx_5);
        emergency_phone1 = findViewById(R.id.Tx_6);
        emergency_phone2 = findViewById(R.id.Tx_7);
        emergency_phone3 = findViewById(R.id.Tx_8);
        address = findViewById(R.id.Tx_9);


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

    public void ClickAbout_us(View view){ HomeActivity.redirectActivity(this, About_us.class); }

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
        String emailTXT = email.getText().toString();
        String emergency_phone1TXT = emergency_phone1.getText().toString();
        String emergency_phone2TXT = emergency_phone2.getText().toString();
        String emergency_phone3TXT = emergency_phone3.getText().toString();
        String addressTXT = address.getText().toString();

        Intent i = new Intent(this, Modify_profile.class);
        i.putExtra("nickname", nicknameTXT);
        i.putExtra("fullname", fullnameTXT);
        i.putExtra("age", ageTXT);
        i.putExtra("gender", genderTXT);
        i.putExtra("email", emailTXT);
        i.putExtra("emergency1", emergency_phone1TXT);
        i.putExtra("emergency2", emergency_phone2TXT);
        i.putExtra("emergency3", emergency_phone3TXT);
        i.putExtra("address", addressTXT);
        startActivity(i);
    }

    public void savedata(){
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
            TextView textView1 =  findViewById(R.id.Tx_1);
            TextView textView2 =  findViewById(R.id.Tx_2);
            TextView textView3 =  findViewById(R.id.Tx_3);
            TextView textView4 =  findViewById(R.id.Tx_4);
            TextView textView5 =  findViewById(R.id.Tx_5);
            TextView textView6 =  findViewById(R.id.Tx_6);
            TextView textView7 =  findViewById(R.id.Tx_7);
            TextView textView8 =  findViewById(R.id.Tx_8);
            TextView textView9 =  findViewById(R.id.Tx_9);
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
    }


}