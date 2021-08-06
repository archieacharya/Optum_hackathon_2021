package com.nirogo.www;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.sucho.placepicker.Constants;
import com.sucho.placepicker.MapType;
import com.sucho.placepicker.PlacePicker;
import java.util.ArrayList;

public class Map extends AppCompatActivity{



    public FusedLocationProviderClient mFusedLocationClient;
    public LocationRequest mLocationCallback;
    public double lat,lon;
    public Location mLastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        callPermissions();




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String shortadd="";
        String actualaddress = "";
        String bothcoordinate = "";
        String[] address = new String[3];
        if (requestCode == Constants.PLACE_PICKER_REQUEST) {


            if (resultCode == Activity.RESULT_OK && data != null) {
                String addressData = String.valueOf(data.getParcelableExtra(Constants.ADDRESS_INTENT));
                address =  addressData.split("\n");

                double latitude = Double.parseDouble(address[0]);
                double longitude = Double.parseDouble(address[1]);

                LatLng latLng= new LatLng(latitude,longitude);
                bothcoordinate = String.valueOf(latLng);

                String[] fulladd= address[2].split(",");
                if (fulladd.length >= 3) {

                    shortadd= (fulladd[1] + "," + fulladd[2]).trim();
                } else if (fulladd.length == 2) {
                    shortadd= fulladd[1].trim();
                } else fulladd[0].trim();

                actualaddress= address[2];




                Toast.makeText(this, shortadd, Toast.LENGTH_LONG).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

       // SharedPrefManager.getInstance(getApplicationContext()).clearLocation();
        if (!shortadd.equals("")) {
            SharedPrefManager.getInstance(getApplicationContext())
                    .userLocation(
                            actualaddress,
                            shortadd,
                            address[0],
                            address[1],
                            bothcoordinate
                    );
        }

        Toast.makeText(this, "short" +shortadd, Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }


    public void requestLocationUpdates(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)==
                PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==
                        PermissionChecker.PERMISSION_GRANTED){
            mFusedLocationClient = new FusedLocationProviderClient(this);
            mLocationCallback = new LocationRequest();


            mLocationCallback.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


            mFusedLocationClient.requestLocationUpdates(mLocationCallback, new LocationCallback() {

                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);

                    mLastKnownLocation =locationResult.getLastLocation();

                    lat=mLastKnownLocation.getLatitude();
                    lon=mLastKnownLocation.getLongitude();

                    Intent intent = new PlacePicker.IntentBuilder()
                            .setLatLong(lat, lon)  // Initial Latitude and Longitude the Map will load into
                            // Show Coordinates in the Activity
                            .setMapZoom(19.0f)  // Map Zoom Level. Default: 14.0
                            .setAddressRequired(true) // Set If return only Coordinates if cannot fetch Address for the coordinates. Default: True
                            .hideMarkerShadow(false) // Hides the shadow under the map marker. Default: False
                            .setMarkerDrawable(R.drawable.ic_map_marker)// Change the default Marker Image
                            .setMarkerImageImageColor(R.color.red)
                            .setFabColor(R.color.colorPrimaryDark)
                            .setPrimaryTextColor(R.color.colorPrimaryDark) // Change text color of Shortened Address
                            .setSecondaryTextColor(R.color.blue) // Change text color of full Address
                            .setMapType(MapType.NORMAL)
                            .onlyCoordinates(false)  //Get only Coordinates from Place Picker
                            .build(Map.this);
                    startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST);




                }
            }, getMainLooper());

        }else callPermissions();





    }


    public void callPermissions(){



        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this/*context*/, permissions, "Location needed", null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                requestLocationUpdates();

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                callPermissions();
            }
        });
    }


}
