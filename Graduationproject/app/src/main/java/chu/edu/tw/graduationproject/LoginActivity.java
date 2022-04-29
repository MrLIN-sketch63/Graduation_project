package chu.edu.tw.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    EditText username, password;
    DBHelper myDB;
    Button bt_login, bt_register;
    String t1;
    private CheckBox remember_pass;
    private CheckBox auto_login;
    private SharedPreferences loginPreference;

    private Boolean auto_isCheck = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        t1 = "25r";
        bt_register = findViewById(R.id.btnRegister);
        bt_login = findViewById(R.id.btnLogin);
        username = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passwordLogin);
        remember_pass = (CheckBox) findViewById(R.id.RememberPass);
        auto_login = (CheckBox) findViewById(R.id.Auto_login);

        myDB = new DBHelper(this);

        loginPreference = getSharedPreferences("login", MODE_PRIVATE);

        boolean checked = loginPreference.getBoolean("checked", false);
        if (checked) {
            Map<String, Object> m = readLogin();
            if (m != null) {
                username.setText((CharSequence) m.get("userName"));
                password.setText((CharSequence) m.get("password"));
                remember_pass.setChecked(true);
            }

            if (loginPreference.getBoolean("AUTO_isCHECK", false)) {
                auto_login.setChecked(true);
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
            }


        }


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter the Credentials", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean result = myDB.checkusernamePassword(user, pass);
                    if (result == true) {
                        configLoginInfo(remember_pass.isChecked());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });


        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    loginPreference.edit().putBoolean("AUTO_isCHECK", true).commit();
                } else {
                    System.out.println("自动登录没有选中");
                    loginPreference.edit().putBoolean("AUTO_isCHECK", false).commit();
                }
            }
        });

    }

    public void configLoginInfo(boolean checked) {
        SharedPreferences.Editor editor = loginPreference.edit();
        editor.putBoolean("checked", remember_pass.isChecked());

        if (checked) {
            editor.putString("userName", username.getText().toString());
            editor.putString("password", password.getText().toString());
        } else {
            editor.remove("userName").remove("password");
        }
        editor.commit();
    }


    private Map<String, Object> readLogin() {
        Map<String, Object> m = new HashMap<>();
        String userName = loginPreference.getString("userName", "");
        String password = loginPreference.getString("password", "");
        m.put("userName", userName);
        m.put("password", password);
        return m;
    }
}