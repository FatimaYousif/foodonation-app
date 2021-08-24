package com.example.foodonation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


//LOGIN DBBBB


public class Database extends SQLiteOpenHelper {

    private static final String DNAME="Login.db";

//    creates DB at runtime with this constructor
    public Database(Context context) {
        super(context,"Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users (username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
    MyDB.execSQL("drop table if exists users");
    }

    //made methods
    // REMEMBER - SQLite is an app component - CONTENT PROVIDERS
    public Boolean insertData(String username, String password)
    {
        SQLiteDatabase MyDB= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result=MyDB.insert("users", null, contentValues);

        //failure
        if(result==-1) return false;
        else
            return true;
    }

    //check username duplicate entry
    public Boolean checkUsername(String username)
    {
        SQLiteDatabase MyDB= this.getWritableDatabase();
       Cursor cursor=MyDB.rawQuery("Select * from users where username =?", new String[]{username});

        if(cursor.getCount()>0)
        return true;
        else
            return false;
    }

    //check duplicate entry for both- username, pw

    public Boolean checkUsernamepassword(String username, String password)
    {
        SQLiteDatabase MyDB= this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from users where username =? and password=?", new String[]{username,password});

        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
