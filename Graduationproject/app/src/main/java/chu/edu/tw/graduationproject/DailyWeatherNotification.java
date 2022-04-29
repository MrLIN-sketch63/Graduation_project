package chu.edu.tw.graduationproject;

import static android.content.Context.ALARM_SERVICE;
import static android.net.wifi.WifiConfiguration.Status.strings;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class DailyWeatherNotification extends Worker {
    Context mcontext;

    
    public void NotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "WEATHERCHANNEL";
            String description = "today weather";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("WeatherNotification",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = mcontext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public DailyWeatherNotification(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mcontext = context;
    }


    @NonNull
    @Override
    public Result doWork() {
        String Key = "9c23ad410738cfe1dc04b7055405eb45";
        String lat = getInputData().getString("la");
        String lon = getInputData().getString("ln");
        String link = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=hourly&units=metric&appid="+ Key;

        URL url;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        String result = "";
        int temp;
        String nameIcon = "";
        Bitmap bitmap;

        try {
            url = new URL(link);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            int data = inputStreamReader.read();

            while (data != -1) {
                result += (char) data;
                data = inputStreamReader.read();
            }
            JSONObject jsonObject = new JSONObject(result);
            temp = jsonObject.getJSONObject("current").getInt("temp");
            nameIcon = jsonObject.getJSONArray("daily").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
            String urlIcon = "https://openweathermap.org/img/wn/" + nameIcon + "@2x.png";
            url = new URL(urlIcon);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);


            NotificationChannel();
            Calendar calendar = Calendar.getInstance();

            if (Calendar.getInstance().after(calendar)) {
                calendar.add(Calendar.DAY_OF_MONTH, 0);
            }

            Intent intent = new Intent(mcontext, WeatherBroadcast.class);
            intent.putExtra("temp",temp);
            intent.putExtra("bitmap",bitmap);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return Result.success();
}
}
