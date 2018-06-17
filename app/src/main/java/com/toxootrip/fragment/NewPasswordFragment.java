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
import com.toxootrip.model.PasswordModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

/**
 * Created by himanshu on 15-12-2017.
 */

public class NewPasswordFragment extends BaseFragment {

    private APIService mAPIService;
    private TextInputLayout  passwordTitle, confirmTitle;
    private EditText  passEdt,  confirmPassEdt;

    private TextView change;
    private ProgressDialog progressDialog;


private String user_id;
    private String new_password;
    private AppPreferences mAppPreferences;



    public NewPasswordFragment(){


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_newpassword, parent, false);

        passEdt = (EditText) rootView.findViewById(R.id.passwordEt);


       mAppPreferences = new AppPreferences(getActivity());
        user_id = mAppPreferences.getValue(getActivity());





        confirmPassEdt = (EditText) rootView.findViewById(R.id.passworddEt);

        passwordTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_password);

        confirmTitle = (TextInputLayout) rootView.findViewById(R.id.input_layout_passwordd);

        mAPIService = ApiUtils.getAPIService();

        change = (TextView)rootView.findViewById(R.id.loginBtn);

           change.setOnClickListener(this);




        return rootView;
    }


    @Override
    public void onClick(View view) {

        String new_password = passEdt.getText().toString();
        String ConfirmPassword = confirmPassEdt.getText().toString();



        switch (view.getId()) {


            case R.id.loginBtn:



                if (new_password.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter new  password.", Toast.LENGTH_LONG).show();

                    return;

                }

                if (ConfirmPassword.trim().equals("")) {

                    Toast.makeText(getActivity(), "Please confirm password.", Toast.LENGTH_LONG).show();

                    return;
                }

                if (!new_password.equals(ConfirmPassword)) {

                    Toast.makeText(getActivity(), "Password should be same.", Toast.LENGTH_LONG).show();
                    return;
                }


                else {


                    // sendRegister(emailEdt.getText().toString(),passEdt.getText().toString(),nameEdt.getText().toString(),usernameEdt.getText().toString(),phoneEdt.getText().toString());

                        sendConfirm( passEdt.getText().toString(), confirmPassEdt.getText().toString());


                }

                break;
        }



    }

    public void sendConfirm(final String new_password ,String confirmPassWord) {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }


        mAPIService.confirmPost(user_id,new_password).enqueue(new Callback<PasswordModel>() {
            @Override
            public void onResponse(Call<PasswordModel> call, Response<PasswordModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().getStatus() == 1){

                        Toast.makeText(getActivity(), "password change sucessfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), CommonBaseActivity.class);
                          intent.putExtra("flowType",CommonBaseActivity.LOGIN);

                        startActivity(intent);

                    }else{
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<PasswordModel> call, Throwable t) {
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
