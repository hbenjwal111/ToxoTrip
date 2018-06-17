package com.toxootrip.fragment;

import android.Manifest;
import android.app.ProgressDialog;
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
import com.toxootrip.model.ImageModel;
import com.toxootrip.model.IntModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.extect.appbase.BaseFragment.progressDialog;
import static com.toxootrip.R.id.map;


/**
 * Created by himanshu on 11-03-2018.
 */

public class MapViewwFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

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
    private  List<IntModel> intModels;

    List<ImageModel> commonHotelModels;
private String hotel_location;
    private String country_id;

    double radiusInMeters = 100.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

private APIService mAPIService;


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_map, null, false);
    country_id = getActivity().getIntent().getExtras().getString("country_id");
        mAPIService = ApiUtils.getAPIService();

        initilizeMap();
        return root;
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
final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.show();

        mAPIService.getAllHotelCountry(country_id).enqueue(new Callback<IntModel>() {

@Override
public void onResponse(Call<IntModel> call, retrofit2.Response<IntModel> response) {
        IntModel hotelList = response.body();
        if (hotelList.getStatus() == 1) {
        if(!(mMap == null)) {
        for(int i = 0;i<hotelList.getHotelModelList().size();i++) {
        hotel_location = hotelList.getHotelModelList().get(i).getHotel_location();
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

     Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(hotelList.getHotelModelList()
        .get(i).getHotel_name()));
                marker.showInfoWindow();

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10.0f));
        }

        }else{
        Toast.makeText(getActivity(), "No Hotel Found", Toast.LENGTH_SHORT).show();


        }

        dialog.dismiss();
        } else {
        Toast.makeText(getActivity(), "No Hotel Found", Toast.LENGTH_SHORT).show();
        dialog.dismiss();

        }

        }
@Override
public void onFailure(Call<IntModel> call, Throwable t) {

        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();


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






        }


