package com.example.foodonation;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.provider.SyncStateContract;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.facebook.appevents.codeless.internal.Constants;

import java.util.ArrayList;


public class recyclerview_db extends SQLiteOpenHelper {

    Context context;
    private static final String DNAME="Fooditems.db";


    //need
    /*
    id
    food_title
    Name
    address
    description
    image (NOT TAKEN YET)

    NAME
     */

    private static final String TNAME="food_items";
    private static final String TNEW="new_food_items";
    private static final String COL_ID="_id";
    private static final String COL_TITLE="food_title";
    private static final String COL_NAME="name";
    private static final String COL_ADDRESS="food_address";
    private static final String COL_DESCRIPTION="food_description";
    private static final String COL_IMAGE="img";

    public recyclerview_db(@Nullable Context context) {
        super(context, DNAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TNAME+
        " (" + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " TEXT, "+
                COL_NAME + " TEXT, "+
                COL_ADDRESS + " TEXT, "+
                COL_DESCRIPTION+ " TEXT, "+
                COL_IMAGE + " TEXT );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TNAME);
        onCreate(db);

        if (newVersion > oldVersion) {
            db.execSQL("INSERT INTO new_food_items SELECT id, title, name, address, description FROM food_items ;");
            db.execSQL("drop table if exists " + TNAME);
            db.execSQL("ALTER TABLE new_food_items RENAME TO food_items");
        }

    }

    //add food item
    public void addItem(String title, String name, String address, String description, String images)
    {

        SQLiteDatabase MyDB= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_TITLE,title);
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_ADDRESS,address);
        contentValues.put(COL_DESCRIPTION,description);
        contentValues.put(COL_IMAGE,images);

        long result=MyDB.insert(TNAME, null, contentValues);

        //failure
        if(result==-1) {
            Toast.makeText(context, "FAILED",    Toast.LENGTH_LONG).show();
        }
            else {

                Intent i=new Intent(context, dashboard.class);
                context.startActivity(i);
        }
    }

    //read data
    public ArrayList<Food> getAllData(String orderby) {
        ArrayList<Food> arrayList = new ArrayList<>();
        String query = "Select * from " + TNAME + " ORDER BY " + orderby;

        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = MyDB.rawQuery(query, null);

    if(cursor.moveToNext())
    {
        do {
            Food food=new Food(
                   ""+ cursor.getInt(cursor.getColumnIndex(COL_ID)),
                    ""+ cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                    ""+ cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    ""+ cursor.getString(cursor.getColumnIndex(COL_ADDRESS)),
                    ""+ cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)),
                    ""+ cursor.getString(cursor.getColumnIndex(COL_IMAGE))

                    );
            arrayList.add(food);
        }while(cursor.moveToNext());
    }
        MyDB.close();
    return arrayList;
    }

}
