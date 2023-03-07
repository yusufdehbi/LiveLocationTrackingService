package com.dehbideveloper.servicelivetrackinglocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button startServiceButton, btnUpdateCoordinate;
    TextView tvLatitude, tvLongitude;
    MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startServiceButton = (Button) findViewById(R.id.btnStartService);
        tvLatitude = (TextView) findViewById(R.id.txtLatitude);
        tvLongitude = (TextView) findViewById(R.id.txtLongitude);
        btnUpdateCoordinate = (Button) findViewById(R.id.btnUpdateDb);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start LocationService
                Intent intent = new Intent(MainActivity.this, LocationService.class);

                // Start the service
                startService(intent);



            }
        });

        btnUpdateCoordinate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<LocationData> locations = dbHelper.getAllLocations();
                int lastIndex = locations.size() - 1;
                String latitude = Double.toString(locations.get(lastIndex).getLatitude());
                String longitude = Double.toString(locations.get(lastIndex).getLongitude());

                tvLatitude.setText(latitude);
                tvLongitude.setText(longitude);

                Toast.makeText(MainActivity.this, "coordinates updated!", Toast.LENGTH_SHORT).show();

            }
        });
    }
/*
    // Create a broadcast receiver that listens for the broadcast message
    private BroadcastReceiver databaseUpdatedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.dehbideveloper.ACTION_DATABASE_UPDATED")) {

                // Extract the data from the broadcast intent
                double latitude = intent.getDoubleExtra("latitude", 0.0);
                double longitude = intent.getDoubleExtra("longitude", 0.0);

//                // Update the UI based on the data included in the broadcast message
//                String data = intent.getStringExtra("data");
                updateUI(latitude, longitude);
            }
        }
    };

    // Register the broadcast receiver in onResume()
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("com.dehbideveloper.ACTION_DATABASE_UPDATED");
        registerReceiver(databaseUpdatedReceiver, filter);
    }

    // Unregister the broadcast receiver in onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(databaseUpdatedReceiver);
    }

    // Update the UI based on the data received from the service
    private void updateUI(double latitude, double longitude) {
        tvLatitude.setText(Double.toString(latitude));
        tvLongitude.setText(Double.toString(longitude));
    }
*/
}