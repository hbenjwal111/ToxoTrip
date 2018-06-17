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
import com.toxootrip.model.ForgetModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

/**
 * Created by himanshu on 01-06-2018.
 */

public class OtpFragment extends BaseFragment {

    private APIService mAPIService;
    private TextInputLayout emailTitle,   phoneTitle;
    private EditText emailEdt,phoneEdt;

    private TextView send;
    private ProgressDialog progressDialog;
    private String user_id,otp;



    public OtpFragment(){


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_otp, parent, false);
        user_id = getActivity().getIntent().getExtras().getString("user_id");


        emailEdt = (EditText) rootView.findViewById(R.id.fullNameEt);

        emailTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_fullname);


        mAPIService = ApiUtils.getAPIService();

        send = (TextView) rootView.findViewById(R.id.loginBtn);

        send.setOnClickListener(this);




        return rootView;
    }


    @Override
    public void onClick(View view) {
        String otp= emailEdt.getText().toString();

        switch (view.getId()) {
            case R.id.loginBtn:

                if(otp.trim().equals("")){

                    showToast("Please enter your otp");

                    return;
                }





                else {


                    // sendRegister(emailEdt.getText().toString(),passEdt.getText().toString(),nameEdt.getText().toString(),usernameEdt.getText().toString(),phoneEdt.getText().toString());

                    sendForget( emailEdt.getText().toString());


                }

                break;
        }


    }





    public void sendForget( String otp) {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }





        mAPIService.otpPost(user_id,otp).enqueue(new Callback<ForgetModel>() {
            @Override
            public void onResponse(Call<ForgetModel> call, Response<ForgetModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().getStatus() == 1){
/*
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


*/
                        Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                        commonActivity.putExtra("flowType", CommonBaseActivity.PASSWORD);
                        startActivity(commonActivity);

                        getActivity().finish();
                    }else{
                        Toast.makeText(getActivity(),"" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

    }}
