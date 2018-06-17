package com.toxootrip.fragment;

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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.activity.CustomPictureHolder;
import com.toxootrip.activity.HomeActivity;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.ProductModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

/**
 * Created by himanshu on 15-01-2018.
 */

public class UploadProductFragment extends BaseFragment {

    private APIService mAPIService;
    private String type_id;
    private String type_name;
    private String category_id;
    private String category_name;
    private  String user_id;
    String mediaPath,mediaPath1;
    FloatingActionButton floatingActionButton;
    public static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    private  String current_city = null;
    private String product_country = null;
    private TextInputLayout titelLayout,desLayout,priceLayout,categoryLayout,productLayout,brandLayout,modelLayout,fuelLayout,
    regisLayout,colorLayout,youLayout;
    private EditText titleEdt, desEdt, priceEdt,  categoryEdt,productEdt,brandEdt,modelEdt,fuelEdt,regisEdt,colorEdt,youEdt;
    private EditText locationEdt=null;
    private EditText country=null;
    private TextView post,typeEdt;
    private ProgressDialog progressDialog;
    private AppPreferences mAppPreferences;
    private static final String IMAGE_DIRECTORY = "/toxotrip";
    private int GALLERY = 1, CAMERA = 2;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    public int imagePos = 1;
    private CustomPictureHolder pictureHolder;




    public UploadProductFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.postproduct_fragment, parent, false);
        pictureHolder= (CustomPictureHolder)rootView.findViewById(R.id.pictureHolder);
        youLayout =(TextInputLayout)rootView.findViewById(R.id.input_layout_you);
        titelLayout = (TextInputLayout)rootView.findViewById(R.id.input_layout_title);
        desLayout = (TextInputLayout)rootView.findViewById(R.id.input_layout_des);
        priceLayout = (TextInputLayout)rootView.findViewById(R.id.input_layout_price);
        productLayout = (TextInputLayout)rootView.findViewById(R.id.input_layout_product);
        brandLayout = (TextInputLayout)rootView.findViewById(R.id.input_layout_brand);
        modelLayout = (TextInputLayout)rootView.findViewById(R.id.input_layout_model);
        fuelLayout = (TextInputLayout)rootView.findViewById(R.id.input_layout_fuel);
        regisLayout = (TextInputLayout)rootView.findViewById(R.id.input_layout_reges);
        colorLayout = (TextInputLayout)rootView.findViewById(R.id.input_layout_color);
        youEdt =(EditText)rootView.findViewById(R.id.youare);
        productEdt = (EditText)rootView.findViewById(R.id.type);
        brandEdt = (EditText)rootView.findViewById(R.id.brand);
        modelEdt = (EditText)rootView.findViewById(R.id.model);
        fuelEdt = (EditText)rootView.findViewById(R.id.fuel);
        regisEdt = (EditText)rootView.findViewById(R.id.regestration);
        colorEdt = (EditText)rootView.findViewById(R.id.color);
        titleEdt = (EditText) rootView.findViewById(R.id.title);
        desEdt = (EditText) rootView.findViewById(R.id.desc);
        priceEdt = (EditText) rootView.findViewById(R.id.price);
        locationEdt = (EditText) rootView.findViewById(R.id.location);
        country=(EditText) rootView.findViewById(R.id.county);
        pictureHolder.setVisibility(View.GONE);
        category_id = getActivity().getIntent().getExtras().getString("category_id");
        category_name = getActivity().getIntent().getExtras().getString("category_name");
        type_id = getActivity().getIntent().getExtras().getString("type_id");
        type_name=getActivity().getIntent().getExtras().getString("type_name");

      if(type_id.equals(String.valueOf(4)) ){

          brandLayout.setVisibility(View.GONE);
          modelLayout.setVisibility(View.GONE);
          fuelLayout.setVisibility(View.GONE);
          regisLayout.setVisibility(View.GONE);
          colorLayout.setVisibility(View.GONE);

        }

        else if(type_id.equals(String.valueOf(1))){

          productLayout.setVisibility(View.GONE);
      }
      else if(type_id.equals(String.valueOf(2))){

          productLayout.setVisibility(View.GONE);
      }

      else if(type_id.equals(String.valueOf(3))){

          productLayout.setVisibility(View.GONE);
          brandLayout.setVisibility(View.GONE);
          modelLayout.setVisibility(View.GONE);
          fuelLayout.setVisibility(View.GONE);
          regisLayout.setVisibility(View.GONE);
          colorLayout.setVisibility(View.GONE);
      }


        mAPIService = ApiUtils.getAPIService();
        mAppPreferences = new AppPreferences(getActivity());
        user_id = mAppPreferences.getValue(getActivity());
        typeEdt =(TextView)rootView.findViewById(R.id.categoryi);
        typeEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.CATEGORY);
                startActivity(commonActivity);
            }
        });
        typeEdt.setText(category_name);
        categoryEdt=(EditText)rootView.findViewById(R.id.sub);
        categoryEdt.setText(type_name);
        categoryEdt.setKeyListener(null);

        seekLocation();
        setHasOptionsMenu(true);
        floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.fab);
         floatingActionButton.setOnClickListener(this);
        post = (TextView) rootView.findViewById(R.id.loginBtn);
        post.setOnClickListener(this);
        return rootView;
        }




    public void onClick(View view) {
        String product_title = titleEdt.getText().toString();
        String product_discription = desEdt.getText().toString();
        String product_price = priceEdt.getText().toString();
        String current_city= locationEdt.getText().toString();
        String product_country = country.getText().toString();
        switch (view.getId()) {

            case R.id.fab:

                    showPictureDialog();

                break;


        case R.id.loginBtn:


                if (product_title.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter title", Toast.LENGTH_LONG).show();

                    return;

                }
                if (product_discription.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter discription.", Toast.LENGTH_LONG).show();

                    return;

                }

            if(product_discription.trim().length()<5){

                showToast("description should have atleast 10 words");

                return;

            }

            if(product_discription.trim().length()>100){

                showToast("description should have max 100 words");

                return;

            }



            if (product_price.trim().equals("")) {

                    Toast.makeText(getActivity(), "Please enter price", Toast.LENGTH_LONG).show();

                    return;
                }


                else {
                    postProduct( product_title, product_discription, product_price,current_city,product_country);

                }

                break;
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

                try {
                    Uri contentURI = data.getData();

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    pictureHolder.setVisibility(View.VISIBLE);
                    mediaPath = saveImage(bitmap);
                    mediaPath1 = saveImage(bitmap);

                    pictureHolder.setImage(contentURI,imagePos);
                    imagePos++;
                    if(imagePos > 8)
                    {
                        floatingActionButton.setVisibility(View.GONE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }



            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            mediaPath = saveImage(thumbnail);
            pictureHolder.setImage(Uri.parse(String.valueOf(thumbnail)),imagePos);
            imagePos++;
            if(imagePos > 8)
            {
                floatingActionButton.setVisibility(View.GONE);
            }

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

    private void postProduct(String product_title, String product_discription,
                             String product_price,String current_city,String product_country) {

        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();


        }

        RequestBody requestBody1,requestBody2;
        MultipartBody.Part fileToUpload1,fileToUpload2;
        if (mediaPath != null) {
            File file1 = new File(mediaPath);
            File file2 = new File(mediaPath1);


            requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);


            fileToUpload1 = MultipartBody.Part.createFormData("file1", file1.getName(), requestBody1);
            fileToUpload2 = MultipartBody.Part.createFormData("file2", file2.getName(), requestBody2);


            mAPIService.getAllProduct(user_id, type_id, category_id, product_title, product_discription, product_price, current_city,product_country, fileToUpload1,fileToUpload2)
                    .enqueue(new Callback<ProductModel>() {
                        @Override
                        public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                if (response.body().getStatus() == 1) {
                                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent commonActivity = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(commonActivity);

                                } else {
                                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ProductModel> call, Throwable t) {
                            Log.e(TAG, "Unable to submit post to API.");
                            call.cancel();
                            progressDialog.dismiss();
                        }
                    });
        }

        else {

            showToast("please choose image");
        }

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
            current_city= addressList.get(0).getLocality();
            locationEdt.setText(current_city);

            product_country= addressList.get(0).getCountryName();
            country.setText(product_country);

            return product_country;

        }catch (Exception e){
            e.printStackTrace();
        }
        return current_city;

    }

    @Override
    public void setTAG(String TAG) {

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }
}
