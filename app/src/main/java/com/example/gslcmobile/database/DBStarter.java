package com.example.gslcmobile.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBStarter extends SQLiteOpenHelper {

    private static final String DB_NAME = "GSLCMaps";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "pinpoint";
    public static final String FIELD_ID = "id";
    public static final String FIELD_LAT = "latitude";
    public static final String FIELD_LONG = "longtitude";

    private static final String CREATE_QUERY =
            "CREATE TABLE " + TABLE_NAME + "(" + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FIELD_LAT + " FLOAT," +
                    FIELD_LONG + " FLOAT)";

    private static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBStarter(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_QUERY);
        onCreate(db);
    }
}
