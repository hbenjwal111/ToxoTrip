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
import com.toxootrip.activity.HomeActivity;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.AddBusinessModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

/**
 * Created by himanshu on 13-02-2018.
 */

public class FeedBackFragment extends BaseFragment {

    private APIService mAPIService;
    private TextInputLayout nameTitle, emailTitle, passwordTitle, usernameTitle, phoneTitle, confirmTitle;
    private EditText company, emailEdt, passEdt, phoneEdt, usernameEdt, confirmPassEdt;
    private TextView signup, login;
    private ProgressDialog progressDialog;
    private AppPreferences mAppPreferences;
    private String user_id;

    public FeedBackFragment() {


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_feedback, parent, false);
        company = (EditText) rootView.findViewById(R.id.fullNameEt);
        emailEdt = (EditText) rootView.findViewById(R.id.emailEt);
        passEdt = (EditText) rootView.findViewById(R.id.passwordEt);
        phoneEdt = (EditText) rootView.findViewById(R.id.phoneNumberEt);
        usernameEdt = (EditText) rootView.findViewById(R.id.userNameEt);
        nameTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_fullname);
        passwordTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_password);
        emailTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_email);
        usernameTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_username);
        phoneTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_phoneNumber);
        mAPIService = ApiUtils.getAPIService();
        mAppPreferences = new AppPreferences(getActivity());
        user_id = mAppPreferences.getValue(getActivity());
        login = (TextView) rootView.findViewById(R.id.loginBtn);
        login.setOnClickListener(this);
        return rootView;

    }

    @Override
    public void onClick(View view) {
        String company_name = company.getText().toString();
        String mobile_no = phoneEdt.getText().toString();
        String discription= emailEdt.getText().toString();
        String city = passEdt.getText().toString();

        switch (view.getId()) {

            case R.id.loginBtn:



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
                if (mobile_no.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter your Mobile Number.", Toast.LENGTH_LONG).show();

                    return;

                }
                if (mobile_no.trim().length() < 10) {
                    Toast.makeText(getActivity(), "Please enter valid Mobile Number.", Toast.LENGTH_LONG).show();

                    return;

                } else {

                    sendRegister( phoneEdt.getText().toString(),emailEdt.getText().toString());

                }

                break;
        }

    }
    public void sendRegister( final String mobile_no, String discription) {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        mAPIService.getFeedBack(user_id,  mobile_no, discription).enqueue(new Callback<AddBusinessModel>() {
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

    @Override
    public void setTAG(String TAG) {

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }
}
