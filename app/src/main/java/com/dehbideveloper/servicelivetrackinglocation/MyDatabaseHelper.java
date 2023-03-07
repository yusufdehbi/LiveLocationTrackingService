package com.dehbideveloper.servicelivetrackinglocation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "locations";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LATITUDE + " REAL, " +
                    COLUMN_LONGITUDE + " REAL)";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertLocation(double latitude, double longitude) {
        // Get a reference to the writable database
        SQLiteDatabase db = getWritableDatabase();

        // Create a ContentValues object to hold the data
        ContentValues values = new ContentValues();
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);

        // Insert the data into the database and return the ID of the new row
        long result = db.insert(TABLE_NAME, null, values);

        // Close the database connection
        db.close();

        // Return the ID of the new row
        return result != -1;
    }

    public ArrayList<LocationData> getAllLocations() {
        ArrayList<LocationData> locationList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                LocationData location = new LocationData();
                location.setId(Integer.parseInt(cursor.getString(0)));
                location.setLatitude(cursor.getDouble(1));
                location.setLongitude(cursor.getDouble(2));

                locationList.add(location);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return locationList;
    }

}
