package chu.edu.tw.graduationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class About_us extends AppCompatActivity {
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        drawerLayout = findViewById(R.id.drawer_layout);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.icon_2)
                .setDescription("感谢使用樂齡生活助手")
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("意見與反饋")
                .addEmail("linxiansen623@gmail.com")
                .addFacebook("Ziyang Lin")
                .addGitHub("MrLIN-sketch63")
                .addInstagram("Charles_lin_22")
                .create();

        setContentView(aboutPage);


    }

    public void ClickMenu(View view) {
        HomeActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        HomeActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        HomeActivity.redirectActivity(this, HomeActivity.class);
    }

    public void ClickDashboard(View view) {
        recreate();
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


}