package chu.edu.tw.graduationproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASE_NAME = "Login.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table users(username Text primary key, password Text)");
        myDB.execSQL("create Table userprofile(nickname TEXT primary key, " +
                "fullname TEXT not null, age TEXT not null, " +
                "gender TEXT not null, emergency_phone TEXT not null, address TEXT not null)");
        myDB.execSQL("create Table healthdata(c_time TEXT primary key, low_pressure Integer not null, high_pressure Integer not null, " +
                "heartbeat Integer not null, before_eat Integer not null, after_eat Integer not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("drop Table if exists users");
        myDB.execSQL("drop table if exists userprofile");
        myDB.execSQL("drop table if exists healthdata");
        onCreate(myDB);
    }


    public Boolean insertData(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = myDB.insert("users", null, contentValues);

        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select *from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean checkusernamePassword(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }


    public Boolean UpdateData(String nickname, String fullname, String age, String gender, String emergency_phone, String address){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", fullname);
        contentValues.put("age", age);
        contentValues.put("gender", gender);
        contentValues.put("emergency_phone", emergency_phone);
        contentValues.put("address", address);
        Cursor cursor = myDB.rawQuery("Select * from userprofile where nickname = ?", new String[] {nickname});
        if(cursor.getCount() > 0) {
            long result = myDB.update("userprofile", contentValues, "nickname=?", new String[] {nickname});
            if(result == -1) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }

    }
    public Boolean insert_userprofile_Data(String nickname, String fullname, String age, String gender, String emergency_phone, String address) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nickname", nickname);
        cv.put("fullname", fullname);
        cv.put("age", age);
        cv.put("gender", gender);
        cv.put("emergency_phone", emergency_phone);
        cv.put("address", address);
        long result = myDB.insert("userprofile", null, cv);

        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public Cursor getdata() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from userprofile", null);
        return cursor;
    }

    public Boolean addHealthInfo(String current_time, int low_pressure, int high_pressure, int heartbeat, int before_eat, int after_eat) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("c_time", current_time);
        cv.put("low_pressure", low_pressure);
        cv.put("high_pressure", high_pressure);
        cv.put("heartbeat", heartbeat);
        cv.put("before_eat", before_eat);
        cv.put("after_eat", after_eat);

        long result = myDB.insert("healthdata", null, cv);

        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }


    public Cursor readAllData() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from healthdata", null);
        return cursor;
    }


    public Boolean UpdateHealthData(String current_time, String low_pressure, String high_pressure, String heartbeat, String before_eat, String after_eat){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("low_pressure", low_pressure);
        contentValues.put("high_pressure", high_pressure);
        contentValues.put("heartbeat", heartbeat);
        contentValues.put("before_eat", before_eat);
        contentValues.put("after_eat", after_eat);
        Cursor cursor = myDB.rawQuery("Select * from healthdata where c_time = ?", new String[] {current_time});
        if(cursor.getCount() > 0) {
            long result = myDB.update("healthdata", contentValues, "c_time=?", new String[] {current_time});
            if(result == -1) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }

    }

    public Boolean deleteOneRow(String current_time) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        long result = myDB.delete("healthdata", "c_time=?", new String[]{current_time});
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    void deleteAllData(){
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("DELETE FROM " + "healthdata");
    }


   /* public Cursor query_Emergency_PhoneNumber() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from healthdata", null);
        return cursor;
    }*/
}