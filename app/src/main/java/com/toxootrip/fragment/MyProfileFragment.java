package com.toxootrip.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.squareup.picasso.Picasso;
import com.toxootrip.R;
import com.toxootrip.activity.HomeActivity;
import com.toxootrip.activity.RoundTransformation;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.MyProfileModel;
import com.toxootrip.model.UpdateprofileModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by himanshu on 04-04-2018.
 */

public class MyProfileFragment extends BaseFragment {
    private String user_id;
    List<MyProfileModel> allProductModel;
    private APIService mAPIService;
    private String profile_url,email,name,phone,username;
    private ImageView imageView;
    private TextView emailEdt,nameEdt,phoneEdt,userEdt,changePhoto,update;
    private AppPreferences mAppPreferences;

    File file;
    Uri uri;
    private static final String IMAGE_DIRECTORY = "/toxotrip";
    private  String mediaPath;
    Intent CamIntent, GalIntent, CropIntent ;
    public  static final int RequestPermissionCode  = 1 ;
    DisplayMetrics displayMetrics ;
    private int GALLERY = 1, CAMERA = 2, CROP_PIC = 4;


    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;


    int width, height;



    public static final String IMAGE_URL_BASE_PATH = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";


    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_myprofile, parent, false);
        imageView =(ImageView)rootView.findViewById(R.id.img);
        emailEdt =(TextView)rootView.findViewById(R.id.email);
        nameEdt =(TextView)rootView.findViewById(R.id.name);
        phoneEdt =(TextView)rootView.findViewById(R.id.phone);
        userEdt = (TextView)rootView.findViewById(R.id.uname);
        changePhoto = (TextView)rootView.findViewById(R.id.change);
        update =(TextView)rootView.findViewById(R.id.loginBtn);

          update.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  updateProfile();
              }
          });
         changePhoto.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(isPermissionGranted()){
                     showPictureDialog();
                 }
                 update.setVisibility(View.VISIBLE);



             }
         });
        mAppPreferences = new AppPreferences(getActivity());
        user_id = mAppPreferences.getValue(getActivity());
        mAPIService = ApiUtils.getAPIService();
         getProfile();


        return rootView;
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 4);
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
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            mediaPath = saveImage(thumbnail);

            imageView.setImageBitmap(thumbnail);

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

    private void updateProfile(){
    RequestBody requestBody1;
    MultipartBody.Part fileToUpload1;


    File file1 = new File(mediaPath);
    requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
    fileToUpload1 = MultipartBody.Part.createFormData("file", file1.getName(), requestBody1);


    mAPIService.updatePost(user_id,fileToUpload1).enqueue(new Callback<UpdateprofileModel>() {

        @Override
        public void onResponse(Call<UpdateprofileModel> call, retrofit2.Response<UpdateprofileModel> response) {
            if (response.isSuccessful()) {
                if(response.body().getStatus() == 1){
                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Intent commonActivity = new Intent(getActivity(), HomeActivity.class);

                    startActivity(commonActivity);
                }else{
                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }
        @Override
        public void onFailure(Call<UpdateprofileModel> call, Throwable t) {

        }
    });
}


    private void getProfile() {
        mAPIService.getAllProfile(user_id).enqueue(new Callback<MyProfileModel>() {

            @Override
            public void onResponse(Call<MyProfileModel> call, retrofit2.Response<MyProfileModel> response) {
                MyProfileModel   hotelList = response.body();
                if (hotelList.getStatus() == 1) {
                    allProductModel= hotelList.getHotelModelList();
                    profile_url = allProductModel.get(0).getProfile_pic();

                    profile_url = IMAGE_URL_BASE_PATH +allProductModel.get(0).getProfile_pic();

                    Picasso.with(getActivity())
                            .load(profile_url)
                            .transform(new RoundTransformation(50, 4))
                            .centerCrop()
                            .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                            .into(imageView);
                    email = allProductModel.get(0).getEmail_id();
                    emailEdt.setText(email);
                    name = allProductModel.get(0).getFull_name();
                    nameEdt.setText(name);
                    phone =allProductModel.get(0).getPhone_no();
                    phoneEdt.setText(phone);
                    username = allProductModel.get(0).getUser_name();
                    userEdt.setText(username);

                } else {
                }
            }
            @Override
            public void onFailure(Call<MyProfileModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void setTAG(String TAG) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }
}
