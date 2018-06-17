package com.toxootrip.fragment;

import android.app.ProgressDialog;
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
import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.ForgetModel;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

/**
 * Created by himanshu on 03-12-2017.
 */

public class ForgetFragment extends BaseFragment {

    private APIService mAPIService;
    private TextInputLayout  emailTitle,   phoneTitle;
    private EditText  emailEdt,phoneEdt;
    private AppPreferences mApp;

    private TextView send;
    private ProgressDialog progressDialog;
    private String user_id;



    public ForgetFragment(){


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_forgot, parent, false);

        emailEdt = (EditText) rootView.findViewById(R.id.fullNameEt);

        emailTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_fullname);


        mAPIService = ApiUtils.getAPIService();
        mApp = new AppPreferences(getActivity());

        send = (TextView) rootView.findViewById(R.id.loginBtn);

        send.setOnClickListener(this);




        return rootView;
    }


    @Override
    public void onClick(View view) {
        String email = emailEdt.getText().toString();

        switch (view.getId()) {
            case R.id.loginBtn:

                if (email.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter emailId.", Toast.LENGTH_LONG).show();

                    return;

                }
                if (!isValidEmaillId(email)) {
                    Toast.makeText(getActivity(), "Please enter valid Email-Id", Toast.LENGTH_LONG).show();

                    return;
                }




                 else {


                    // sendRegister(emailEdt.getText().toString(),passEdt.getText().toString(),nameEdt.getText().toString(),usernameEdt.getText().toString(),phoneEdt.getText().toString());

                    sendForget( emailEdt.getText().toString());


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


    public void sendForget( String email) {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }





        mAPIService.forgetpOST(email).enqueue(new Callback<ForgetModel>() {
            @Override
            public void onResponse(Call<ForgetModel> call, Response<ForgetModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().getStatus() == 1){
/*
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


*/
                         user_id= response.body().getUser_id();
                        mApp.saveId(getActivity(),user_id);

                        Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                        commonActivity.putExtra("flowType", CommonBaseActivity.OTP);
                        commonActivity.putExtra("user_id",user_id);
                        startActivity(commonActivity);

                        getActivity().finish();
                    }else{
                        Toast.makeText(getActivity(), "invalid email " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ForgetModel> call, Throwable t) {
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
