package com.example.osman.grandresturant;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    Button aa;
    double latti;
    double longi;
    String cityName;
    String stateName;
    String countryName;
    TextView aaa, aaaa, aaaaa;

    ProgressDialog blg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aa = (Button) findViewById(R.id.aaaaa);
        aaa = (TextView) findViewById(R.id.LocationtextView);
        aaaaa = (TextView) findViewById(R.id.LongtextView);
        aaaa = (TextView) findViewById(R.id.LattextView);

        blg = new ProgressDialog(MainActivity.this);
        blg.setTitle("ss");
        blg.setCanceledOnTouchOutside(false);
        blg.setMessage("ss");


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blg.show();
                getLocation();

            }
        });


    }

    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latti = location.getLatitude();
                longi = location.getLongitude();


                String total2 = Double.toString(latti);
                String total = Double.toString(longi);

                Toast.makeText(this, total2 + total, Toast.LENGTH_SHORT).show();

                filterLocation();

            } else {

            }
        }

    }

    private void filterLocation() {
        Locale mLocale = new Locale("ar");

        Geocoder geocoder = new Geocoder(MainActivity.this, mLocale);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latti, longi, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            assert addresses != null;

            int maxAddressLine = addresses.get(0).getMaxAddressLineIndex();

            String countryName = addresses.get(0).getAddressLine(maxAddressLine);
            String countryName1 = addresses.get(0).getAdminArea();
            String countryName2 = addresses.get(0).getSubAdminArea();
            String countryName3 = addresses.get(0).getSubLocality();


            String countr = "محافظة";

            String regex = "\\s*\\bمحافظة\\b\\s*";
            countryName1 = countryName1.replaceAll(regex, "");

            aaa.setText(":" + countryName1 + ":");

            Toast.makeText(MainActivity.this, countryName1, Toast.LENGTH_SHORT).show();
            blg.cancel();
        } catch (Exception e) {

            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
        }
    }
}



