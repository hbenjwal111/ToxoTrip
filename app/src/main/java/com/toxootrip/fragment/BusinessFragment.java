package com.toxootrip.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.activity.CustomPictureHolder;
import com.toxootrip.activity.HomeActivity;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.AddBusinessModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

/**
 * Created by himanshu on 11-02-2018.
 */




public class BusinessFragment extends BaseFragment {

    private APIService mAPIService;
    private TextInputLayout nameTitle, emailTitle, passwordTitle, usernameTitle, phoneTitle, confirmTitle;
    private EditText company, emailEdt, passEdt, phoneEdt, usernameEdt, country,eId,ownername;
    FloatingActionButton floatingActionButton;
   CustomPictureHolder pictureHolder;
    private TextView signup, login;
    private ProgressDialog progressDialog;
    private ImageView imageview;
    private AppPreferences mAppPreferences;
    private String user_id;
    public int imagePos = 1;
    private static final String IMAGE_DIRECTORY = "/toxotrip";
    private int GALLERY = 1, CAMERA = 2;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    public static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    private  String city = null;
    private String product_country = null;
    public  static final int RequestPermissionCode  = 1 ;
    private static final int SELECT_PICTURE = 1000;
    private Bitmap bitmap;
    private File destination = null;
    private String mediaPath;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;


    public BusinessFragment() {


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_business, parent, false);

        company = (EditText) rootView.findViewById(R.id.fullNameEt);
        emailEdt = (EditText) rootView.findViewById(R.id.emailEt);
        passEdt = (EditText) rootView.findViewById(R.id.passwordEt);
        phoneEdt = (EditText) rootView.findViewById(R.id.phoneNumberEt);
        usernameEdt = (EditText) rootView.findViewById(R.id.userNameEt);
        country = (EditText)rootView.findViewById(R.id.country);
        imageview = (ImageView)rootView.findViewById(R.id.image);
        eId = (EditText)rootView.findViewById(R.id.phoneNumber);
        nameTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_fullname);
        passwordTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_password);
        emailTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_email);
        usernameTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_username);
        phoneTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_phoneNumber);
        mAPIService = ApiUtils.getAPIService();
       floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        mAppPreferences = new AppPreferences(getActivity());
        user_id = mAppPreferences.getValue(getActivity());
        login = (TextView) rootView.findViewById(R.id.loginBtn);
        login.setOnClickListener(this);
        seekLocation();
        return rootView;

    }

    @Override
    public void onClick(View view) {
        String company_name = company.getText().toString();
        String countryy = country.getText().toString();
        String emId = eId.getText().toString();
        String mobile_no = phoneEdt.getText().toString();
        String discription= emailEdt.getText().toString();
        String city = passEdt.getText().toString();
        String owner = usernameEdt.getText().toString();

        switch (view.getId()) {
            case R.id.fab:

                if(isPermissionGranted()){
                    showPictureDialog();
                }

                break;


            case R.id.loginBtn:

                if(countryy.trim().equals("")){

                    showToast("Please enter country");

                    return;
                }

                if(owner.trim().equals("")){

                    showToast("Please enter Your Name");
                    return;
                }

                if(emId.trim().equals("")){

                    showToast("Please enter emailid");
                    return;
                }


                if (company_name.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter company name.", Toast.LENGTH_LONG).show();

                    return;

                }


                if (discription.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter discription.", Toast.LENGTH_LONG).show();

                    return;

                }

                if(discription.trim().length()<50){

                    showToast("description should have atleast 50 words");

                    return;

                }

                if(discription.trim().length()>600){

                    showToast("description should have max 600 words");

                    return;

                }

                if (city.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter city", Toast.LENGTH_LONG).show();

                    return;

                }

                if (mobile_no.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter your Mobile Number.", Toast.LENGTH_LONG).show();

                    return;

                }

                if (mobile_no.trim().length() < 10) {
                    Toast.makeText(getActivity(), "Please enter valid Mobile Number.", Toast.LENGTH_LONG).show();

                    return;


                } else {


                    sendRegister(company.getText().toString(), phoneEdt.getText().toString(),emailEdt.getText().toString(), passEdt.getText().toString(),usernameEdt
                    .getText().toString(),eId.getText().toString());


                }

                break;
        }



    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 5);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showPictureDialog();
                } else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }

            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }




    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }
    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    mediaPath = saveImage(bitmap);
                    imageview.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            mediaPath = saveImage(thumbnail);

            imageview.setImageBitmap(thumbnail);

            saveImage(thumbnail);
        }
    }



    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
// have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            // Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void sendRegister(final String company_name, final String mobile_no, String discription, final String city,String owner_name,String owner_email) {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }


        mAPIService.getBusiness(user_id, company_name, mobile_no, discription, city,owner_name,owner_email).enqueue(new Callback<AddBusinessModel>() {
            @Override
            public void onResponse(Call<AddBusinessModel> call, Response<AddBusinessModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().getStatus() == 1){
                        Toast.makeText(getActivity(), "Submit Successful" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<AddBusinessModel> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                call.cancel();
                progressDialog.dismiss();
            }
        });
    }

    private void seekLocation(){


        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)

                != PackageManager.PERMISSION_GRANTED){

            if(android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)){
                android.support.v4.app.ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);

            }else{
                android.support.v4.app.ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
        }

        else{
            try {
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if(isNetworkEnabled){

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
    }
    public String hereLocation(double lat,double lng){
        Geocoder geocoder= new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addressList ;
        try{
            addressList = geocoder.getFromLocation(lat,lng,1);
            city= addressList.get(0).getLocality();
            passEdt.setText(city);

            product_country= addressList.get(0).getCountryName();
            country.setText(product_country);

            return product_country;

        }catch (Exception e){
            e.printStackTrace();
        }
        return city;

    }

    @Override
    public void setTAG(String TAG) {

    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }
}
