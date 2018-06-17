
package com.toxootrip.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;

import com.extect.appbase.BaseActivity;
import com.toxootrip.R;
import com.toxootrip.api.AppPreferences;

/**
 * Created by himanshu on 14-12-2017.
 */


public class SplashActivity extends BaseActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private AppPreferences mApp;
    private ImageView imageView;
    public static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    private EditText city;
    String curcity = null;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mApp = new AppPreferences(this);
        imageView =(ImageView)findViewById(R.id.logo);
        new Handler().postDelayed(new Runnable() {


/*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */


            @Override
            public void run() {
                launchHomeScreen();

            }
        }, SPLASH_TIME_OUT);

    }
    private void launchHomeScreen() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }

   /* private void seekLocation(){


        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)

                != PackageManager.PERMISSION_GRANTED){

            if(android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)){
                android.support.v4.app.ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);

            }else{
                android.support.v4.app.ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
        }

        else{
            try {
                LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

               if(isNetworkEnabled){

                  Location locationn = locationManager
                           .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                   hereLocation(locationn.getLatitude(), locationn.getLongitude());


               }if(isGPSEnabled){

                   Location locationn = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    hereLocation(locationn.getLatitude(), locationn.getLongitude());

                }
            }catch (Exception e){
                e.printStackTrace();

            }
        }
    }


    public String hereLocation(double lat,double lng){
        Geocoder geocoder= new Geocoder(this, Locale.getDefault());
        List<Address> addressList ;
        try{
            addressList = geocoder.getFromLocation(lat,lng,1);
            curcity = addressList.get(0).getLocality();
            mApp.saveCurrent(this,curcity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return curcity;
    }*/
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void replaceView() {

    }
}

