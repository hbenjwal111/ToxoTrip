package com.toxootrip.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.activity.HomeActivity;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.CustomerRegisterModel;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

/**
 * Created by himanshu on 03-12-2017.
 */

public class RegisterFragment extends BaseFragment {

    public static String USER_EMAIL = "";
    public static String USER_PHONE = "";
    public static String USER_NAME="";
    private APIService mAPIService;
    private TextInputLayout nameTitle, emailTitle, passwordTitle, usernameTitle, phoneTitle, confirmTitle;
    private EditText nameEdt, emailEdt, passEdt, phoneEdt, usernameEdt, confirmPassEdt;
    private TextView signup, login,changePhoto,facebookbtn;
    private ProgressDialog progressDialog;
    private AppPreferences mAppPreferences;
    private  String namee;
    private String emaill;
    private ImageView imageView;
    private String phonee;
    private String profile_image;
    File file;
    Uri uri;
    private static final String IMAGE_DIRECTORY = "/toxotrip";
    private  String mediaPath = null;
    Intent CamIntent, GalIntent, CropIntent ;
    public  static final int RequestPermissionCode  = 1 ;
    DisplayMetrics displayMetrics ;
    private int GALLERY = 1, CAMERA = 2, CROP_PIC = 4;


    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    private LoginButton loginButton;
    private Button fb;
    int width, height;
    private static final int PICK_IMAGE_ID = 234;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

    private  String id,first_name,fb_email;


    public RegisterFragment() {


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        View rootView = inflater.inflate(R.layout.activity_register, parent, false);
        nameEdt = (EditText) rootView.findViewById(R.id.fullNameEt);
        emailEdt = (EditText) rootView.findViewById(R.id.emailEt);
        passEdt = (EditText) rootView.findViewById(R.id.passwordEt);
        phoneEdt = (EditText) rootView.findViewById(R.id.phoneNumberEt);
        usernameEdt = (EditText) rootView.findViewById(R.id.userNameEt);
        confirmPassEdt = (EditText) rootView.findViewById(R.id.passworddEt);
        imageView = (ImageView) rootView.findViewById(R.id.img_profile);

        nameTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_fullname);
        passwordTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_password);
        emailTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_email);
        usernameTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_username);
        phoneTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_phoneNumber);
        confirmTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_passwordd);
        mAPIService = ApiUtils.getAPIService();
        changePhoto = (TextView) rootView.findViewById(R.id.loginBtnn);
        changePhoto.setOnClickListener(this) ;
        mAppPreferences = new AppPreferences(getActivity());
        login = (TextView) rootView.findViewById(R.id.loginBtn);
        login.setOnClickListener(this);
        signup = (TextView) rootView.findViewById(R.id.signUpTv);
        signup.setOnClickListener(this);



        loginButton = (LoginButton) rootView.findViewById(R.id.facebook_login);
        loginButton.setFragment(this);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {



                        deleteAccessToken();


                        String token = loginResult.getAccessToken().getToken();

                        // save accessToken to SharedPreference
                        mAppPreferences.saveAccessToken(getActivity(),token);

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject jsonObject,
                                                            GraphResponse response) {

                                        // Getting FB User Data
                                        Bundle facebookData = getFacebookData(jsonObject);


                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }


                    @Override
                    public void onCancel () {
                        Log.d(TAG, "Login attempt cancelled.");
                    }

                    @Override
                    public void onError (FacebookException e){
                        e.printStackTrace();
                        Log.d(TAG, "Login attempt failed.");
                    }
                }
        );


        return rootView;
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
                /*Picasso.with(getActivity())
                        .load(String.valueOf(profile_pic))
                        .transform(new RoundTransformation(50, 4))
                        .centerCrop()
                        .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                        .into(imageView);*/
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            if(object.has("facebookId"))
                bundle.putString("facebookId",object.getString("facebookId"));
            if (object.has("name"))
                first_name =  object.getString("name");
                nameEdt.setText(first_name);

            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            fb_email= object.getString("email");
            emailEdt.setText(fb_email);
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));

/*
            mAppPreferences.saveFaceBookProfile(getActivity(), String.valueOf(profile_pic));
*/
        } catch (Exception e) {
            Log.d(TAG, "BUNDLE Exception : "+e.toString());
        }

        return bundle;
    }

    private void deleteAccessToken() {

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //User logged out
                    LoginManager.getInstance().logOut();
                    mAppPreferences.clearToken(getActivity());
                    Intent intent = new Intent(getActivity(),HomeActivity.class);
                    startActivity(intent);

                }
            }
        };
    }
    @Override
    public void onClick(View view) {
        String name = nameEdt.getText().toString();
        String email = emailEdt.getText().toString();
        String phone = phoneEdt.getText().toString();
        String username = usernameEdt.getText().toString();
        String password = passEdt.getText().toString();
        String ConfirmPassword = confirmPassEdt.getText().toString();


        switch (view.getId()) {





            case R.id.loginBtnn:
                showPictureDialog();
               /* if(isPermissionGranted()){
                    showPictureDialog();
                }*/
                break;

            case R.id.signUpTv:
                Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);

                commonActivity.putExtra("flowType", CommonBaseActivity.LOGIN);

                startActivity(commonActivity);
                break;

            case R.id.loginBtn:



                if (username.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter username.", Toast.LENGTH_LONG).show();

                    return;

                }
               /* if (username.trim().length()<5) {
                    Toast.makeText(getActivity(), "User.", Toast.LENGTH_LONG).show();

                    return;

                }
*/

                if (password.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter password.", Toast.LENGTH_LONG).show();

                    return;

                }

                if (password.trim().length()<5) {
                    Toast.makeText(getActivity(), "Please enter valid password.", Toast.LENGTH_LONG).show();

                    return;

                }

                if (ConfirmPassword.trim().equals("")) {

                    Toast.makeText(getActivity(), "Please confirm password.", Toast.LENGTH_LONG).show();

                    return;
                }

                if (!password.equals(ConfirmPassword)) {

                    Toast.makeText(getActivity(), "Password should be same.", Toast.LENGTH_LONG).show();
return;
                }


                if (name.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter name.", Toast.LENGTH_LONG).show();

                    return;

                }
                if (email.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter emailId.", Toast.LENGTH_LONG).show();

                    return;

                }
                if (!isValidEmaillId(email)) {
                    Toast.makeText(getActivity(), "Please enter valid Email-Id", Toast.LENGTH_LONG).show();

                    return;
                }


                if (phone.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter your Mobile Number.", Toast.LENGTH_LONG).show();

                    return;

                }


                if (phone.trim().length() < 10) {
                    Toast.makeText(getActivity(), "Please enter valid Mobile Number.", Toast.LENGTH_LONG).show();

                    return;


                } else {

                    /*  USER_NAME = nameEdt.getText().toString();
                      USER_EMAIL = emailEdt.getText().toString();
                      USER_PHONE = phoneEdt.getText().toString();*/
                    // sendRegister(emailEdt.getText().toString(),passEdt.getText().toString(),nameEdt.getText().toString(),usernameEdt.getText().toString(),phoneEdt.getText().toString());

                    sendRegister(nameEdt.getText().toString(), emailEdt.getText().toString(), passEdt.getText().toString(), phoneEdt.getText().toString(), usernameEdt.getText().toString(), confirmPassEdt.getText().toString());


                }

                break;
        }



    }



    private boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
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
        callbackManager.onActivityResult(requestCode, resultCode, data);

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



    public void sendRegister(final String name, final String email, String password, final String phone, final String username, String ConfirmPassword) {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        if(mediaPath == null){

            mAPIService.registePost(username, password, name, email, phone).enqueue(new Callback<CustomerRegisterModel>() {
                @Override
                public void onResponse(Call<CustomerRegisterModel> call, Response<CustomerRegisterModel> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        if(response.body().getStatus() == 1){
                            Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                            commonActivity.putExtra("flowType",CommonBaseActivity.LOGIN);
                            startActivity(commonActivity);
                        }else{
                            Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<CustomerRegisterModel> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");
                    call.cancel();
                    progressDialog.dismiss();
                }
            });

        }else {
            RequestBody requestBody1;
            MultipartBody.Part fileToUpload1;


            File file1 = new File(mediaPath);
            requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            fileToUpload1 = MultipartBody.Part.createFormData("file", file1.getName(), requestBody1);


            mAPIService.registerPost(username, password, name, email, phone, fileToUpload1).enqueue(new Callback<CustomerRegisterModel>() {
                @Override
                public void onResponse(Call<CustomerRegisterModel> call, Response<CustomerRegisterModel> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                            commonActivity.putExtra("flowType", CommonBaseActivity.LOGIN);
                            startActivity(commonActivity);
                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<CustomerRegisterModel> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");
                    call.cancel();
                    progressDialog.dismiss();
                }
            });

        }
    }


    @Override
    public void setTAG(String TAG) {

    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }
}
