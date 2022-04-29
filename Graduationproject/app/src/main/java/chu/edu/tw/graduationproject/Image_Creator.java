package chu.edu.tw.graduationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Image_Creator extends AppCompatActivity {

    ViewPager viewPager;

    public void slidepage(){

        int layouts[] = {
                R.layout.default_image1,
                R.layout.default_image2,
                R.layout.default_image3,
                R.layout.default_image4,
                R.layout.default_image5,
        };

        viewPager.setAdapter(new PagerAdapter() {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(layouts[position],container,false);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return layouts.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                View view = (View) object;
                container.removeView(view);
            }


        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_creator);
        Button gallery = findViewById(R.id.button);
        Switch switchA = findViewById(R.id.switch1);
        viewPager = findViewById(R.id.viewPager);
        slidepage();
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,3);
            }
        });
        View decorView = getWindow().getDecorView();
        int HideUI = View.SYSTEM_UI_FLAG_FULLSCREEN;
        ActionBar actionBar = getSupportActionBar();
        EditText editText = findViewById(R.id.EditText);
        Button cap = findViewById(R.id.button2);
        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cap.setVisibility(View.INVISIBLE);
                gallery.setVisibility(View.INVISIBLE);
                switchA.setVisibility(View.INVISIBLE);
                decorView.setSystemUiVisibility(HideUI);
                editText.clearFocus();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {

                        Screenshot(getWindow().getDecorView().getRootView());

                    }}, 1000);

                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        cap.setVisibility(View.VISIBLE);
                        gallery.setVisibility(View.VISIBLE);
                        switchA.setVisibility(View.VISIBLE);
                    }}, 2000);

            }
        });

        switchA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchA.isChecked()){
                    editText.setTextColor(Color.WHITE);
                }
                else{
                    editText.setTextColor(Color.BLACK);
                }
            }
        });

    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            viewPager.setCurrentItem(0);
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.defaultImageView);
            imageView.setImageURI(selectedImage);

        }
    }

    protected void Screenshot(View view){
        verrifyStoragePermission(this);
        Date date = new Date();
        CharSequence current = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss",date);

        try {
            String dirPath = Environment.getExternalStorageDirectory().toString() + "/ScreenShooter/";
            File fileDir = new File(dirPath);
            if (!fileDir.exists()){
                fileDir.mkdir();
            }
            String path = dirPath + "/" + current + ".jpeg";

            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File file = new File(path);

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(this,"screenshot success",Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE=1;
    private static String[] PERMISSION_STORAGE={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public  static void verrifyStoragePermission(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,PERMISSION_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }
    }
}