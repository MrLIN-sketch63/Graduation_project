package chu.edu.tw.graduationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import chu.edu.tw.graduationproject.ui.MainActivity;

public class HomeActivity extends AppCompatActivity {

    private final int MAX_RECORDS = 10;
    private LocationManager manager;
    private Location currentLocation;
    private int index = 0, count = 0;
    private final double[] Lats = new double[MAX_RECORDS];
    private final double[] Lngs = new double[MAX_RECORDS];
    String la,ln;
    int i = 0;
    TextView txttest;

    private ImageButton btn;

    DBHelper myDB;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        i = 0;

        // 取得系統服務的LocationManager物件
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        myDB = new DBHelper(this);

        ImageButton emergency_btn = (ImageButton) findViewById(R.id.emergency_call);
        emergency_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor =  myDB.getdata();
                if(cursor.moveToFirst()) {
                    String emergency_num = cursor.getString(5);
                    String emergency_num1 = cursor.getString(6);
                    String emergency_num2 = cursor.getString(7);
                    if(i == 0) {
                        emergency_call(emergency_num);
                        if (emergency_num1.length() != 0)i += 1;
                    }
                    else if(i == 1) {
                        emergency_call(emergency_num1);
                        if (emergency_num2.length() != 0)i += 1;
                        else i = 0;
                    }
                    else if(i == 2) {
                        emergency_call(emergency_num2);
                        i = 0;
                    }
                }
            }
        });

        ImageButton image_creator = (ImageButton) findViewById(R.id.image);
        image_creator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,Image_Creator.class);
                startActivity(intent);
            }
        });

        getCurrentLocation();

        Calendar NotificationTime = Calendar.getInstance();
        Calendar CurrentTime = Calendar.getInstance();
        NotificationTime.set(Calendar.HOUR_OF_DAY, 8);
        NotificationTime.set(Calendar.MINUTE, 0);
        NotificationTime.set(Calendar.SECOND, 0);

        if(NotificationTime.before(CurrentTime)){
            NotificationTime.add(Calendar.HOUR_OF_DAY,24);
        }

        long DelayTime = NotificationTime.getTimeInMillis() - CurrentTime.getTimeInMillis();

        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);

        Data.Builder data = new Data.Builder();
        data.putString("la", la);
        data.putString("ln", ln);


        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(DailyWeatherNotification.class,24,TimeUnit.HOURS)
                        .setInputData(data.build())
                        .setConstraints(builder.build())
                        .setInitialDelay(DelayTime, TimeUnit.MILLISECONDS)
                        .build();
        WorkManager
                .getInstance()
                .enqueue(workRequest);

        ActivityCompat.requestPermissions(HomeActivity.this, new String[] {Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
    }



    public void emergency_call(String emergency_num){
        Intent forcall = new Intent();
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
            forcall.setAction(Intent.ACTION_CALL);
            forcall.setData(Uri.parse("tel:"+emergency_num));
            startActivity(forcall);
        }else{
            ActivityCompat.requestPermissions(this,new String []{
                    Manifest.permission.CALL_PHONE},PackageManager.PERMISSION_GRANTED);
        }
    }





    private void getCurrentLocation() {
        // 取得最佳的定位提供者
        Criteria criteria = new Criteria();
        String best = manager.getBestProvider(criteria, true);
        // 更新位置頻率的條件
        int minTime = 600000; // 毫秒

        float minDistance = 200; // 公尺
        if (best != null) { // 取得快取的最後位置,如果有的話
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            currentLocation = manager.getLastKnownLocation(best);
            manager.requestLocationUpdates(best, minTime, minDistance, listener);
        } else { // 取得快取的最後位置,如果有的話
            currentLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
        }
        updatePosition(); // 更新位置
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.removeUpdates(listener);
        closeDrawer(drawerLayout);
    }

   private void updatePosition() {
//        TextView output, list;
//        String str = "最近個人行蹤的座標清單:\n";
//        output = (TextView) findViewById(R.id.lblOutput);
//        list = (TextView) findViewById(R.id.lblList);
        if (currentLocation == null) {
            Toast.makeText(HomeActivity.this, "取得定位資訊中...", Toast.LENGTH_SHORT).show();
        } else {
            // 顯示目前經緯度座標資訊
            getLocationInfo(currentLocation);
            // 顯示個人行蹤的座標清單
//            for (int i = 0; i < MAX_RECORDS; i++) {
//                if (Lats[i] != 0.0)
//                    str += Lats[i] + "/" + Lngs[i] + "\n";
            }
        //    list.setText(str);
        }


    // 建立定位服務的傾聽者物件
    private final LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;
            updatePosition();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    // 取得定位資訊
    public String getLocationInfo(Location location) {
        boolean isSave = true;
        double lat,lng;
        lat = location.getLatitude();
        lng = location.getLongitude();
        StringBuffer str = new StringBuffer();
        str.append("定位提供者(Provider): " + location.getProvider());
        str.append("\n緯度(Latitude): " + Double.toString(lat));
        str.append("\n經度(Longitude): " + Double.toString(lng));

        la = Double.toString(lat);
        ln = Double.toString(lng);

        if (count >= 1) { // 檢查是否需要儲存座標
            // 建立目地GPS座標的Location物件
            Location dest = new Location(location);
            // 前一個座標的陣列索引
            int preIndex = index - 1;
            // 檢查前一座標是否是陣列最後一個元素
            if (preIndex < 0) preIndex = Lats.length - 1;
            // 指定目的Location物件的座標
            dest.setLatitude(Lats[preIndex]);
            dest.setLongitude(Lngs[preIndex]);
            Log.d("Track Location", count + " index/preIndex: " + index + "/" + preIndex);
            Log.d("Track Location", "dlat: " + Lats[preIndex]);
            Log.d("Track Location", "dlng: " + Lngs[preIndex]);
            // 計算與目地座標的距離
            float distance = location.distanceTo(dest);
            //Toast.makeText(this, "距離: " + distance + "公尺",Toast.LENGTH_SHORT).show();
            Log.d("Track Location", "distance: " + distance);
            // 檢查距離是否小於200公尺, 小於不用存
            if (distance < 200) isSave = false;
        }
        if (isSave) { // 記錄座標
            Lats[index] = lat;
            Lngs[index] = lng;
            count++;
            if (count >= MAX_RECORDS) count = MAX_RECORDS;
            index++; // 陣列索引加一
            // 如果索引大於最大索引, 重設為0
            if (index >= MAX_RECORDS) index = 0;
        }
        return str.toString();
    }




    public void btn_map(View view) {
        Intent mapView = new Intent(HomeActivity.this, MapsActivity.class);
        mapView.putExtra("GPSLATITUDE", Lats);
        mapView.putExtra("GPSLONGITUDE", Lngs);
        mapView.putExtra("MAX_INDEX", count);
        startActivity(mapView);  // 啟動活動
    }

    public void btn_weather(View view) {
        Intent intent = new Intent(HomeActivity.this, WeatherActivity.class);
        intent.putExtra("latitude", la);
        intent.putExtra("longitude", ln);
        startActivity(intent);  // 啟動活動
    }



    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view) {
        recreate();
    }

    public void ClickDashboard(View view) {
        redirectActivity(this,Dashboard.class);
    }

    public void ClickAboutUs(View view) {
        redirectActivity(this, User.class);
    }

    public void ClickLogout(View view) {
        redirectActivity(this, LoginActivity.class);
    }

    public void ClickExit(View view) {
        exit(this);
    }

    public void ClickAbout_us(View view){ HomeActivity.redirectActivity(this, About_us.class); }

    public static void exit(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.logout_title);
        builder.setMessage(R.string.logout_content);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void btn_health_data(View view) {
        Intent intent = new Intent(this, Health_data_record.class);
        startActivity(intent);
    }

    public void set_alarm(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}