package com.toxootrip.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.toxootrip.R;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.fragment.DashBoardFragment;
import com.toxootrip.model.MyProfileModel;
import com.toxootrip.model.SearchProductModel;
import com.toxootrip.model.UpdateprofileModel;
import com.toxootrip.permission.PermissionResultCallback;
import com.toxootrip.permission.PermissionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.toxootrip.R.id.changePhoto;
import static com.toxootrip.R.id.img_profile;
import static com.toxootrip.R.id.loginBtn;

/**
 * Created by himanshu on 29-11-2017.
 */

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {
    public static FragmentManager fragmentManager;
    CoordinatorLayout coordinatorLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private Stack<Fragment> fragmentStack;
    public DrawerLayout drawer;
    private AppPreferences mAppPreferences;
    private String user_id;
    List<MyProfileModel> allProductModel;
    private List<UpdateprofileModel> updateprofileModels;
    private APIService mAPIService;
    @SuppressWarnings("FieldCanBeLocal")
    private TextView login, nameEdt, emailEdt, change;
    private ImageView imageView;
    private String name;
    private EditText search;
    private String email;
    private Spinner spinnerDosen, spinnerDose;
    public static final int MY_PERMISSION_REQUEST_LOCATION = 100;
    private TextView locationEdt, countryy, go;
    private String current_city = null;
    private String current_country = null;
    private String city_id;
    private ImageView circleImageView;
    private String profile_url;
    private String city, city_name;
    private String country, country_name, search_namee;
    private String phonee;
    private String profile_image;
    private TextView gooo;
    private static final String IMAGE_DIRECTORY = "/toxotrip";
    private int GALLERY = 1, CAMERA = 2;
    private String accessToken;
    File file;
    Uri uri;
    Intent CamIntent, GalIntent, CropIntent;
    public static final int RequestPermissionCode = 1;


    boolean isGPSEnabled = false;
    private ProgressDialog progressDialog;

    boolean isNetworkEnabled = false;

    private String mediaPath, search_name, first_name, fb_email, profile_pic;
    private List<SearchProductModel> searchProductModels;
    private TextView coun, cit, changee;

    public static final String IMAGE_URL_BASE_PATH = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";
    ArrayList<String> permissionsArraylist = new ArrayList<>();

    PermissionUtils permissionUtils;

    public static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    public static final String WRITE_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final int SETTINGS_PERMISSIONS_REQUEST = 1;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        permissionUtils = new PermissionUtils(this);
        permissionsArraylist.add(CAMERA_PERMISSION);
        permissionsArraylist.add(WRITE_STORAGE_PERMISSION);
        permissionsArraylist.add(COARSE_LOCATION);
        permissionsArraylist.add(CALL_PHONE);
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(getString(R.string.banner_home_footer));

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        search = (EditText) findViewById(R.id.emailEt);
        enableRuntimePermission();
        search.setHint("Search By Product name");
        gooo = (TextView) findViewById(R.id.go);
        gooo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search_name = search.getText().toString();

                String city = mAppPreferences.getAllCityName(getApplicationContext());

                if (search_name.trim().equals("")) {

                    showToast("enter product name");
                } else {

                    getSearchDetail(search.getText().toString(), mAppPreferences.getAllCityName(getApplicationContext()));
                }
            }
        });
        mAPIService = ApiUtils.getAPIService();
        coun = (TextView) findViewById(R.id.coun);
        cit = (TextView) findViewById(R.id.cit);
        coun.setOnClickListener(this);
        cit.setOnClickListener(this);
        countryy = (TextView) findViewById(R.id.spinnerDosen);
/*
        countryy.setOnClickListener(this);
*/
        locationEdt = (TextView) findViewById(R.id.spinnerDose);

/*
        locationEdt.setOnClickListener(this);
*/


/*
        seekLocation();
*/
        mAppPreferences = new AppPreferences(this);
        user_id = mAppPreferences.getValue(this);
        accessToken = mAppPreferences.getToken(this);

        mAppPreferences.saveCurrent(this, current_city);
        mAppPreferences.saveCurrentCountry(this, current_country);
        city_id = mAppPreferences.getCity(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        mFragmentManager = getSupportFragmentManager();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (user_id == null) {

            navigationView.getMenu().findItem(R.id.logout).setVisible(false);
        } else {
            navigationView.getMenu().findItem(R.id.logout).setVisible(true);


        }
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        getProfile();
        login = (TextView) hView.findViewById(loginBtn);
        if (!(user_id == null)) {

            login.setVisibility(View.GONE);
        } else {

            login.setVisibility(View.VISIBLE);
        }
        nameEdt = (TextView) hView.findViewById(R.id.name);
        imageView = (ImageView) hView.findViewById(R.id.img_profile);
        changee = (TextView) hView.findViewById(R.id.changePhoto);
        changee.setOnClickListener(this);
        imageView.setOnClickListener(this);
       /*String profile_pic = mAppPreferences.getFaceBookprofile(this);


        Picasso.with(HomeActivity.this)
                .load(profile_pic)
                .transform(new RoundTransformation(50, 4))
                .centerCrop()
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .into(imageView);*/


/*
        first_name =mAppPreferences.getFaceBookFirstName(this);
*/
/*
        nameEdt.setText(first_name);
*/
/*
        fb_email = mAppPreferences.getFaceBookEmail(this);
*/
        emailEdt = (TextView) hView.findViewById(R.id.textView);
/*
        emailEdt.setText(fb_email);
*/
/*
        profile_pic = mAppPreferences.getFaceBookprofile(this);
*/

        login.setOnClickListener(this);
        DashBoardFragment homeFragment = new DashBoardFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {


            case loginBtn:


                Intent commonActivityy = new Intent(this, CommonBaseActivity.class);
                commonActivityy.putExtra("flowType", CommonBaseActivity.REGISTER);
                startActivity(commonActivityy);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);


                break;

            case img_profile:
                if (!(user_id == null)) {

                    Intent com = new Intent(this, CommonBaseActivity.class);
                    com.putExtra("flowType", CommonBaseActivity.MYPROFILE);
                    startActivity(com);
                    DrawerLayout drawerr = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawerr.closeDrawer(GravityCompat.START);

                } else {
                    Intent com = new Intent(this, CommonBaseActivity.class);
                    com.putExtra("flowType", CommonBaseActivity.REGISTER);
                    startActivity(com);
                    DrawerLayout drawerr = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawerr.closeDrawer(GravityCompat.START);


                }
                break;

            case changePhoto:
                if (!(user_id == null)) {

                    Intent com = new Intent(this, CommonBaseActivity.class);
                    com.putExtra("flowType", CommonBaseActivity.MYPROFILE);
                    startActivity(com);
                    DrawerLayout drawerr = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawerr.closeDrawer(GravityCompat.START);

                } else {
                    Intent com = new Intent(this, CommonBaseActivity.class);
                    com.putExtra("flowType", CommonBaseActivity.REGISTER);
                    startActivity(com);
                    DrawerLayout drawerr = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawerr.closeDrawer(GravityCompat.START);


                }
                break;


            case R.id.cit:
                Intent commonActivity = new Intent(this, CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.MENU);
                startActivityForResult(commonActivity, 3);

                break;


            case R.id.coun:
                Intent commonActiv = new Intent(this, CommonBaseActivity.class);
                commonActiv.putExtra("flowType", CommonBaseActivity.COUNTRYFIND);
                startActivityForResult(commonActiv, 4);

                break;


        }
    }



    private void enableRuntimePermission() {

        boolean status = permissionUtils.check_permission(permissionsArraylist, getResources().getString(R.string.permission_must_required_msg), SETTINGS_PERMISSIONS_REQUEST);

         /* if(status){

              new AlertDialog.Builder(this)
                      .setTitle("Permission Alert")
                      .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                          }
                      })
                      .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              Log.d("MainActivity", "Aborting mission...");
                              dialog.cancel();
                          }
                      })
                      .show();

*/
    }

        /*if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)

                != PackageManager.PERMISSION_GRANTED) {

            if (android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                android.support.v4.app.ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);

            } else {
                android.support.v4.app.ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
            }
        }*/


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);

       seekLocation();

    }

    private void getSearchDetail(final String search_name, String city) {


        mAPIService.getSearchName(search_name, city).enqueue(new Callback<SearchProductModel>() {
            @Override
            public void onResponse(Call<SearchProductModel> call, Response<SearchProductModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


                        Intent commonActivity = new Intent(getApplicationContext(), CommonBaseActivity.class);
                        search_namee = response.body().getSearch_name();

                        mAppPreferences.savecat(getApplicationContext(), search_name);
                        commonActivity.putExtra("flowType", CommonBaseActivity.SEARCHPRODUCT);
                        startActivity(commonActivity);
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<SearchProductModel> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                call.cancel();
            }
        });
    }


  private void seekLocation() {


        try {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isNetworkEnabled) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location locationn = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    hereLocation(locationn.getLatitude(), locationn.getLongitude());
                    hereLocation(locationn.getLatitude(), locationn.getLongitude());



                }if(isGPSEnabled){

                    Location locationn = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    hereLocation(locationn.getLatitude(), locationn.getLongitude());
                    hereLocation(locationn.getLatitude(), locationn.getLongitude());

                }
            }catch (Exception e){
                e.printStackTrace();

            }
        }

    public String hereLocation(double lat,double lng){

        Geocoder geocoder= new Geocoder(this, Locale.getDefault());
        try{
        List<Address> addressList ;

            addressList = geocoder.getFromLocation(lat, lng,1);
            current_city= addressList.get(0).getLocality();



            countryy.setText(current_country);

            current_country= addressList.get(0).getCountryName();

            locationEdt.setText(current_city);





            return current_country;

        }catch (Exception e){
            e.printStackTrace();
        }
        return current_city;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
         if (requestCode == 3 ) {
             city = mAppPreferences.getAllCityName(this);

             locationEdt.setText(city);
             showToast("You Are in"+""+city);



        }else if(requestCode == 4){

             country = mAppPreferences.getCurrentCountryName(this);
              countryy.setText(country);
             showToast("You Are in"+""+country);
         }

    }


    private void getProfile() {
        mAPIService.getAllProfile(user_id).enqueue(new Callback<MyProfileModel>() {

            @Override
            public void onResponse(Call<MyProfileModel> call, retrofit2.Response<MyProfileModel> response) {
                MyProfileModel   hotelList = response.body();
                if (hotelList.getStatus() == 1) {
                    allProductModel= hotelList.getHotelModelList();



                profile_url = allProductModel.get(0).getProfile_pic();

                        profile_url = IMAGE_URL_BASE_PATH + allProductModel.get(0).getProfile_pic();


                        Picasso.with(HomeActivity.this)
                                .load(profile_url)
                                .transform(new RoundTransformation(50, 4))
                                .centerCrop()
                                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                                .into(imageView);



                    email = allProductModel.get(0).getEmail_id();
                      emailEdt.setText(email);
                      name = allProductModel.get(0).getFull_name();
                      nameEdt.setText(name);

                }


                else {
                }
            }
            @Override
            public void onFailure(Call<MyProfileModel> call, Throwable t) {

            }
        });
    }



    @Override
    public void replaceView() {

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.share) {
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=com.toxootrip");
            shareIntent.setType("text/plain");
            startActivity(shareIntent);


            // Toast.makeText(getApplicationContext(),"home clicked",Toast.LENGTH_LONG).show();

        } else if (id == R.id.about) {

            Intent commonActivityy = new Intent(this, CommonBaseActivity.class);
            commonActivityy.putExtra("flowType", CommonBaseActivity.TERM);
            startActivity(commonActivityy);


            // Toast.makeText(getApplicationContext(),"Train Stations",Toast.LENGTH_LONG).show();
        } else if (id == R.id.feedback) {

            if (!(user_id == null)) {

                Intent commonActivity = new Intent(this, CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.FEEDBACK);
                startActivity(commonActivity);

            }
            else {

                Intent commonActivity = new Intent(this, CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.REGISTER);
                startActivity(commonActivity);
            }


            // Toast.makeText(getApplicationContext(),"Train Schedule",Toast.LENGTH_LONG).show();
        }else if(id == R.id.myad){

            if (!(user_id == null)) {

                Intent commonActivity = new Intent(this, CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.MYAD);
                startActivity(commonActivity);

            }

            else {

                Intent commonActivity = new Intent(this, CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.REGISTER);
                startActivity(commonActivity);
            }

        }

        else if (id == R.id.Rate) {

            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + this.getPackageName()));
            startActivity(intent);


            //Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_LONG).show();

        } else if (id == R.id.logout) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Confirm Logout...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure want to Logout ?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        logOut();

                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();


        }
        else if (id == R.id.business) {
            if (!(user_id == null)) {
                Intent commonActivity = new Intent(this, CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.BUSINESS);
                startActivity(commonActivity);
            }
            else {
                Intent commonActivity = new Intent(this, CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.REGISTER);
                startActivity(commonActivity);
            }
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
    }

    public void logOut() {

        mAppPreferences.removeValue(this);
        Intent logoutintent = new Intent(this,HomeActivity.class);

        startActivity(logoutintent);


    }

    public void onBackPressed() {
        FragmentManager mgr = getSupportFragmentManager();
        if (mgr.getBackStackEntryCount() == 1) {
            // No backstack to pop, so calling super
            finish();
        } else {
            mgr.popBackStack();
        }
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION","GRANTED");
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY","GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION","DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION","NEVER ASK AGAIN");
    }
}

