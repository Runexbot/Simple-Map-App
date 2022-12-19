package com.example.gslcmobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gslcmobile.Location;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class DBPinpoint {

    DBStarter dbStarter;

    public DBPinpoint(Context context){
        this.dbStarter = new DBStarter(context);
    }

    public int insertPinpoint(LatLng location){
        SQLiteDatabase database = dbStarter.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStarter.FIELD_LAT, location.latitude);
        contentValues.put(DBStarter.FIELD_LONG, location.longitude);
        int id = (int)database.insert(DBStarter.TABLE_NAME, null, contentValues);
        database.close();
        return id;
    }

    public List<Location> showAllPinpoint(){
        SQLiteDatabase database = dbStarter.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBStarter.TABLE_NAME, null);
        List<Location> locationList = new ArrayList<>();
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBStarter.FIELD_ID));
                double lat = cursor.getDouble(cursor.getColumnIndexOrThrow(DBStarter.FIELD_LAT));
                double longt = cursor.getDouble(cursor.getColumnIndexOrThrow(DBStarter.FIELD_LONG));
                locationList.add(new Location(id, new LatLng(lat,longt)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return locationList;
    }

}
