package com.dehbideveloper.servicelivetrackinglocation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.IBinder;
import android.widget.Toast;

import android.Manifest;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class LocationService extends Service implements LocationListener {

    private LocationManager locationManager;
    private MyDatabaseHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
        }
        return START_STICKY;
    }

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Get the latitude and longitude of the location
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Insert the latitude and longitude into the database
        boolean isDataInserted = dbHelper.insertLocation(latitude, longitude);
        Toast.makeText(this, "Location saved: " + latitude + ", " + longitude, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Data Inserted in database:" + isDataInserted, Toast.LENGTH_SHORT).show();

        // Send a broadcast message to inform the app UI of the location update
        Intent intent = new Intent("LOCATION_UPDATE");
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
    }

}