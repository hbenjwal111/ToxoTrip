package com.toxootrip.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.toxootrip.R;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.model.AnotherModel;
import com.toxootrip.model.ImageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.toxootrip.R.id.map;

/**
 * Created by himanshu on 13-04-2018.
 */

public class AllMapFragment extends BaseFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;

    private RelativeLayout rlMapLayout;

    private GoogleMap googleMap;
    private Double Latitude = 0.00;
    private Double Longitude = 0.00;
    MapView mapView;
    GoogleApiClient mGoogleApiClient;
    MapFragment mapFragment;
    GoogleMap gMap;
    private Circle mCircle;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng latLng;
    private String city_id;
    private String hotel_id,hotel_name;
    List<ImageModel> commonHotelModels;
    private String hotel_location;
    double radiusInMeters = 100.0;
    int strokeColor = 0xffff0000; //red outline
    int shadeColor = 0x44ff0000; //opaque red fill

    private APIService mAPIService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_map, null, false);
        hotel_id = getActivity().getIntent().getExtras().getString("hotel_id");
        mAPIService = ApiUtils.getAPIService();

        initilizeMap();
        return root;
    }

    @Override
    public void setTAG(String TAG) {

    }


    private void initilizeMap() {
        SupportMapFragment mSupportMapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(map);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(map, mSupportMapFragment).commit();
        }
        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(this);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

        getAllDataLocationLatLng();


    }
    LocationRequest mLocationRequest;


    private void getAllDataLocationLatLng(){


        mAPIService.getHotelDetaill(hotel_id).enqueue(new Callback<AnotherModel>() {

            @Override
            public void onResponse(Call<AnotherModel> call, retrofit2.Response<AnotherModel> response) {
                AnotherModel hotelList = response.body();
                if (hotelList.getStatus() == 1) {
                    if(!(mMap == null)) {

                        hotel_name = hotelList.getHotleDetail().get(0).getHotel_name();
                        hotel_location = hotelList.getHotleDetail().get(0).getHotel_location();
                            String geo[] = hotel_location.split(",");
                            double latitude = Double.parseDouble(geo[0]);
                            double longitude = Double.parseDouble(geo[1]);
                            LatLng location = new LatLng(latitude, longitude);
                            mCircle = mMap.addCircle(new CircleOptions()
                                    .center(location)
                                    .radius(10000)
                                    .strokeWidth(1)
                                    .strokeColor(Color.BLUE)
                                    .fillColor(Color.TRANSPARENT));



                        Marker marker =   mMap.addMarker(new MarkerOptions().position(location).title(hotelList.getHotleDetail()
                                    .get(0).getHotel_name()));
                        marker.showInfoWindow();
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10.0f));



                    }else{
                        Toast.makeText(getActivity(), "No Hotel Found", Toast.LENGTH_SHORT).show();


                    }


                }

            }
            @Override
            public void onFailure(Call<AnotherModel> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });



    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                return;
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location) {


    }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }
}



