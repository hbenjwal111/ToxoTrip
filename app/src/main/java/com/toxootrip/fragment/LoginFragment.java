package com.toxootrip.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.activity.HomeActivity;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.CustomerLoginModel;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by himanshu on 22-01-2018.
 */

public class LoginFragment extends BaseFragment {

    public static String USER_EMAIL = "";
    private TextView register, forget, login;
    private TextInputLayout passwordTitle, emailTitle;
    private EditText emailEdt, passEdt;
    private APIService mAPIService;
    public Context context;
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager;
    private AppPreferences mAppPreference;
    private LoginButton loginButton;
    private String facebook_id, profile_image, full_name, email_id;
    private  String user_id,first_name,fb_email;
    private AccessTokenTracker accessTokenTracker;
    String name;

    public LoginFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        View rootView = inflater.inflate(R.layout.fragment_login, parent, false);
        mAppPreference = new AppPreferences(getActivity());
        mAPIService = ApiUtils.getAPIService();
        emailEdt = (EditText) rootView.findViewById(R.id.emailEt);
        passEdt = (EditText) rootView.findViewById(R.id.passwordEt);
        passwordTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_password);
        emailTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_email);

        /*loginButton = (LoginButton) rootView.findViewById(R.id.facebook_login);
        loginButton.setFragment(this);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        deleteAccessToken();



                        String token = loginResult.getAccessToken().getToken();

                        // save accessToken to SharedPreference
                        mAppPreference.saveAccessToken(getActivity(),token);

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
                        parameters.putString("fields", "id,first_name,last_name,email,gender");
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
        );*/
        register = (TextView) rootView.findViewById(R.id.signUpTv);
        register.setOnClickListener(this);
        forget = (TextView) rootView.findViewById(R.id.forget);
        forget.setOnClickListener(this);
        login = (TextView) rootView.findViewById(R.id.loginBtn);
        login.setOnClickListener(this);

        return rootView;

    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

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
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            if(object.has("facebookId"))
                bundle.putString("facebookId",object.getString("facebookId"));
            if (object.has("first_name"))
              first_name =  object.getString("first_name");
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
              fb_email= object.getString("email");
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));

               mAppPreference.saveFaceBookFirstName(getActivity(),first_name);
               mAppPreference.saveFaceBookEmail(getActivity(),fb_email);
            mAppPreference.saveFaceBookProfile(getActivity(), String.valueOf(profile_pic));

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
                    mAppPreference.clearToken(getActivity());
                    Intent intent = new Intent(getActivity(),HomeActivity.class);
                      startActivity(intent);

                }
            }
        };
    }*/

    @Override
    public void onClick(View view) {
        String email = emailEdt.getText().toString();
        String password = passEdt.getText().toString();


        switch (view.getId()) {


            case R.id.forget:

                Intent commonActivity = new Intent(getActivity(),CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.FORGET);
                startActivity(commonActivity);
                break;

            case R.id.signUpTv:
                Intent commonActivityy = new Intent(getActivity(), CommonBaseActivity.class);
                commonActivityy.putExtra("flowType", CommonBaseActivity.REGISTER);
                startActivity(commonActivityy);

                break;
            case R.id.loginBtn:
                if (email.trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter emailId.", Toast.LENGTH_LONG).show();

                    return;

                }
                if (!isValidEmaillId(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter valid Email-Id", Toast.LENGTH_LONG).show();

                    return;
                }
                if (password.trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter password.", Toast.LENGTH_LONG).show();

                    return;

                } else {


/*
                    USER_EMAIL = emailEdt.getText().toString();
*/sendLogin(emailEdt.getText().toString(),passEdt.getText().toString());

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

    public void sendLogin(final String email, final String password) {

        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        mAPIService.loginPost(email, password).enqueue(new Callback<CustomerLoginModel>() {
            @Override
            public void onResponse(Call<CustomerLoginModel> call, Response<CustomerLoginModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().getStatus() == 1){
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        user_id = response.body().getUserId();
                        mAppPreference.saveId(getActivity(),user_id);
                        Intent commonActivity = new Intent(getActivity(), HomeActivity.class);
                        startActivity(commonActivity);
                    }else{
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<CustomerLoginModel> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                call.cancel();
                progressDialog.dismiss();
            }
        });
    }




    @Override
    public void setTAG(String TAG) {

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }
}
