package com.vinod.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.vinod.dataobjects.LocationDO;

import java.util.ArrayList;
import java.util.HashMap;

public class MyDatabase  extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "MyLocations";
    public static final String _LATTITUDE = "LATTITUDE";
    public static final String _LONGITUDE = "LONGITUDE";
    public static final String _ADDRESS = "ADDRESS";


    String TAG = "MyDatabase";

    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        log("Database constructor is called ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "( " + _ADDRESS + " varchar, " + _LATTITUDE + "  double, " + _LONGITUDE + " double   )");
        log("table is created ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        log("table is updated ");

    }

    void log(String str) {
        Log.d(TAG, str + "");
    }

    public void insert(ContentValues val) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, val);
        log("Record is inserted ");

    }
    public ArrayList<LocationDO> getLocationsList()
    {
        synchronized ("MY_DATABASE")
        {
            ArrayList<LocationDO> list = new ArrayList<LocationDO>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = null;
            try {
                String query = "select  "+_ADDRESS+","+_LATTITUDE+","+_LONGITUDE+ " from  "+TABLE_NAME;
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        LocationDO obj = new LocationDO();
                        obj.address = cursor.getString(0);
                        obj.lan = cursor.getDouble(1);
                        obj.lan= cursor.getDouble(2);
                        list.add(obj);
                    } while (cursor.moveToNext());
                }
            }catch (Exception e) {
               e.printStackTrace();
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                    sqLiteDatabase.close();
                }

            }
            return list;
        }
    }
}