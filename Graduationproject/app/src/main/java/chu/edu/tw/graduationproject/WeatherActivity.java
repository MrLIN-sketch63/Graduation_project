package chu.edu.tw.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class WeatherActivity extends AppCompatActivity {

    String Country = "";;
    String City = "";
    String Key = "9c23ad410738cfe1dc04b7055405eb45";
    String url, lat, lon;
    TextView txtCountry,txtCity, txtValueBodytemp, txtValueHumidity, txtVision, txtTemp, txtWind, txtHiTemp, txtLoTemp;
    ImageView imageView;
    int temp,humidity,feels_like,visibility,hitemp,lotemp;
    String CountryCode = "";
    double wind;
    Geocoder geocoder;


    public static class Weatherdata extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            String result = "";


            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();

                while (data != -1) {
                    result += (char) data;

                    data = inputStreamReader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    public class Weathericon extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {

            Bitmap bitmap = null;
            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }

    public void dgr_temp(){
        FindLocationName();
        switch (CountryCode){
            case "HK":
                if(temp >= 8 && temp <= 12 || temp >= 28 && temp <= 32)txtTemp.setTextColor(Color.YELLOW);
                if(temp <= 7 || temp >= 33)txtTemp.setTextColor(Color.RED);
                if(lotemp >= 8 && lotemp <= 12 || lotemp >= 28 && lotemp <= 32)txtTemp.setTextColor(Color.YELLOW);
                if(lotemp <= 7 || lotemp >= 33)txtTemp.setTextColor(Color.RED);
                if(hitemp >= 8 && hitemp <= 12 || hitemp >= 28 && hitemp <= 32)txtTemp.setTextColor(Color.YELLOW);
                if(hitemp <= 7 || hitemp >= 33)txtTemp.setTextColor(Color.RED);
                if(humidity >= 85 && humidity <= 95 || humidity >= 30 && humidity <= 70)txtValueHumidity.setTextColor(Color.YELLOW);
                if(humidity >= 95 || humidity <= 40)txtValueHumidity.setTextColor(Color.RED);
                break;
            case "PH":
                if(temp >= 33 && temp <= 41)txtTemp.setTextColor(Color.YELLOW);
                if(temp >= 42)txtTemp.setTextColor(Color.RED);
                if(lotemp >= 33  && lotemp <= 41)txtTemp.setTextColor(Color.YELLOW);
                if(lotemp >= 42)txtTemp.setTextColor(Color.RED);
                if(hitemp >= 33 && hitemp <= 41)txtTemp.setTextColor(Color.YELLOW);
                if(hitemp >= 42)txtTemp.setTextColor(Color.RED);
                break;
            case "MO":
                if(temp >= 8 && temp <= 12 || temp >= 28 && temp <= 32)txtTemp.setTextColor(Color.YELLOW);
                if(temp <= 7 || temp >= 33)txtTemp.setTextColor(Color.RED);
                if(lotemp >= 8  && lotemp <= 12 || lotemp >= 28 && lotemp <= 32)txtTemp.setTextColor(Color.YELLOW);
                if(lotemp <= 7 || lotemp >= 33)txtTemp.setTextColor(Color.RED);
                if(hitemp >= 8 && hitemp <= 12 || hitemp >=28 && hitemp <= 32)txtTemp.setTextColor(Color.YELLOW);
                if(hitemp <= 7 || hitemp >= 33)txtTemp.setTextColor(Color.RED);
                if(humidity >= 85 && humidity <= 95 || humidity >= 30 && humidity <= 50)txtValueHumidity.setTextColor(Color.YELLOW);
                if(humidity >= 96 || humidity <= 29)txtValueHumidity.setTextColor(Color.RED);
                break;
            case "TH":
                if(temp >= 35 && temp <= 39.9 || temp >= 8 && temp <= 22.9)txtTemp.setTextColor(Color.YELLOW);
                if(temp <= 7.9 || temp >= 40)txtTemp.setTextColor(Color.RED);
                if(lotemp >= 35  && lotemp <= 39.9 || lotemp >= 8 && lotemp <= 22.9)txtTemp.setTextColor(Color.YELLOW);
                if(lotemp <= 7.9 || lotemp >= 40)txtTemp.setTextColor(Color.RED);
                if(hitemp >= 35 && hitemp <= 39.9 || hitemp >=8 && hitemp <= 22.9)txtTemp.setTextColor(Color.YELLOW);
                if(hitemp <= 7.9 || hitemp >= 40)txtTemp.setTextColor(Color.RED);
                break;
            default:
                if(temp >= 6 && temp <= 10 || temp >= 36 && temp <= 38)txtTemp.setTextColor(Color.YELLOW);
                if(temp <= 5 || temp >= 39)txtTemp.setTextColor(Color.RED);
                if(lotemp >= 6 && lotemp <= 10 || lotemp >= 36 && lotemp <= 38)txtTemp.setTextColor(Color.YELLOW);
                if(lotemp <= 5 || lotemp >= 39)txtTemp.setTextColor(Color.RED);
                if(hitemp >= 6 && hitemp <= 10 || hitemp >= 36 && hitemp <= 32)txtTemp.setTextColor(Color.YELLOW);
                if(hitemp <= 5 || hitemp >= 39)txtTemp.setTextColor(Color.RED);
                if(humidity >= 85 && humidity <= 95 || humidity >= 30 && humidity <= 70)txtValueHumidity.setTextColor(Color.YELLOW);
                if(humidity >= 95 || humidity <= 40)txtValueHumidity.setTextColor(Color.RED);

        }
        if(visibility >= 3 && visibility <=5)txtVision.setTextColor(Color.YELLOW);
        if(visibility <= 2)txtVision.setTextColor(Color.RED);
        if(wind >= 10.8 && wind <= 20.7)txtWind.setTextColor(Color.YELLOW);
        if(wind >= 20.8)txtWind.setTextColor(Color.RED);
    }

    public void FindLocationName(){
        double Lat = Double.parseDouble(lat);
        double Lon = Double.parseDouble(lon);

        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> listAddr = geocoder.getFromLocation(Lat,Lon,1);
            Address addr = listAddr.get(0);
            for(int i = 0; i <= addr.getMaxAddressLineIndex();i++){
                Country += addr.getCountryName();
                City    += addr.getLocality();
                CountryCode += addr.getCountryCode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        txtCountry.setText(CountryCode);
        if(City.isEmpty())txtCity.setText("-");
        else txtCity.setText(City);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        txtCountry = findViewById(R.id.txtCountry);
        txtCity = findViewById(R.id.txtCity);
        txtValueBodytemp = findViewById(R.id.txtValueBodytemp);
        txtValueHumidity = findViewById(R.id.txtValueHumidity);
        txtVision = findViewById(R.id.txtValueVision);
        txtTemp = findViewById(R.id.txtValue);
        imageView = findViewById(R.id.imgIcon);
        txtHiTemp = findViewById(R.id.HighestTemp);
        txtLoTemp = findViewById(R.id.LowestTemp);
        txtWind = findViewById(R.id.txtValueWind);

        lat = getIntent().getStringExtra("latitude");
        lon = getIntent().getStringExtra("longitude");
        url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=hourly&units=metric&appid=" + Key;

        String nameIcon = "";
        Weatherdata weatherdata = new Weatherdata();

        try {
            String result = weatherdata.execute(url).get();
            JSONObject jsonObject = new JSONObject(result);
            temp = jsonObject.getJSONObject("current").getInt("temp");
            humidity = jsonObject.getJSONObject("current").getInt("humidity");
            feels_like = jsonObject.getJSONObject("current").getInt("feels_like");
            visibility = jsonObject.getJSONObject("current").getInt("visibility");
            visibility /= 1000;
            hitemp = jsonObject.getJSONArray("daily").getJSONObject(0).getJSONObject("temp").getInt("max");
            lotemp = jsonObject.getJSONArray("daily").getJSONObject(0).getJSONObject("temp").getInt("min");
            wind = jsonObject.getJSONArray("daily").getJSONObject(0).getDouble("wind_speed");

            nameIcon = jsonObject.getJSONArray("daily").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
            String urlIcon = "https://openweathermap.org/img/wn/" + nameIcon + "@2x.png";
            Weathericon weathericon = new Weathericon();
            Bitmap bitmap = weathericon.execute(urlIcon).get();
            imageView.setImageBitmap(bitmap);

            txtVision.setText(visibility + "km");
            txtValueHumidity.setText(humidity + "%");
            txtValueBodytemp.setText(feels_like + "°");
            txtTemp.setText(temp + "°");
            txtHiTemp.setText("最高：" + hitemp + "°");
            txtLoTemp.setText("最低：" + lotemp + "°");

            if (wind <= 0.2) txtWind.setText("0級（無風）");
            else if (wind <= 1.5) txtWind.setText("1級（軟風）");
            else if (wind <= 3.3) txtWind.setText("2級（輕風）");
            else if (wind <= 5.4) txtWind.setText("3級（微風）");
            else if (wind <= 7.9) txtWind.setText("4級（和風）");
            else if (wind <= 10.7) txtWind.setText("5級（清風）");
            else if (wind <= 13.8) txtWind.setText("6級（強風）");
            else if (wind <= 17.1) txtWind.setText("7級（疾風）");
            else if (wind <= 20.7) txtWind.setText("8級（大風）");
            else if (wind <= 24.4) txtWind.setText("9級（烈風）");
            else if (wind <= 28.4) txtWind.setText("10級（狂風）");
            else if (wind <= 32.6) txtWind.setText("11級（暴風）");
            else if (wind >= 32.7) txtWind.setText("12級（颱風）");

            dgr_temp();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

}