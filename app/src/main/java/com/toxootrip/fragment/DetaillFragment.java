package com.toxootrip.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
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
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.IntModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import utils.Utils;

/**
 * Created by himanshu on 04-04-2018.
 */

public class DetaillFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private TextView chooseRoom;
    private List<IntModel> intModels;
    private APIService mAPIService;
    private String country_id;
    private String user_id;
    private AppPreferences mAppPreferences;
    private TextView mTextView;
    private TextView textView;
    private ImageView mImageView;
    private TextView phonee;
    private TextView detail;
    private String phone;
    private String hotel_image;
    private String hotel_name;
    private String hotel_address;
    private String hotel_detail;
    private ProgressDialog progressDialog;
    private FloatingActionButton floatingActionButton;
    private FragmentManager fragmentManager;
    private String accessToken;
    public static final String IMAGE_URL_BASE_PATH = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";


    public DetaillFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_detaill, parent, false);
        country_id = getActivity().getIntent().getExtras().getString("country_id");
        mTextView = (TextView) rootView.findViewById(R.id.tv_text);
        textView = (TextView)rootView.findViewById(R.id.tv_text1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.ALLM);

                commonActivity.putExtra("country_id", country_id);



                startActivity(commonActivity);
            }
        });
        mImageView = (ImageView)rootView.findViewById(R.id.image);
     phonee = (TextView)rootView.findViewById(R.id.tv_text2);
        detail = (TextView)rootView.findViewById(R.id.tv_text4);
        mAppPreferences = new AppPreferences(getActivity());
        user_id = mAppPreferences.getValue(getActivity());
        accessToken = mAppPreferences.getToken(getActivity());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view1);
        floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if(isPermissionGranted()){
                    callPhoneNumber(phone);
                }
                if (!(user_id == null)) {
                    callPhoneNumber(phone);

                }else if(!(accessToken == null)){

                    callPhoneNumber(phone);

                }


                else {

                    Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                    commonActivity.putExtra("flowType", CommonBaseActivity.REGISTER);
                    startActivity(commonActivity);
                }


            }
        });

        mAPIService = ApiUtils.getAPIService();
        setHasOptionsMenu(true);


        getHotelList();

        return rootView;
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
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
                    callPhoneNumber(phone);
                } else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }

            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void getHotelList() {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }



        mAPIService.getAllHotelCountry(country_id).enqueue(new Callback<IntModel>() {
            @Override
            public void onResponse(Call<IntModel> call, retrofit2.Response<IntModel> response) {
                IntModel hotelList = response.body();
                if (hotelList.getStatus() == 1) {
                    intModels = hotelList.getHotelModelList();
                    phone = intModels.get(0).getHotel_phone();

                    phonee.setText(phone);

                    hotel_name=intModels.get(0).getHotel_name();
                    mTextView.setText(hotel_name);
                    hotel_address=intModels.get(0).getHotel_address();
                    textView.setText(hotel_address);
                    hotel_detail=intModels.get(0).getHotel_detail();
                    detail.setText(hotel_detail);
                    hotel_image=IMAGE_URL_BASE_PATH+intModels.get(0).getHotel_image();


                    Picasso.with(getActivity())
                            .load(hotel_image)
                            .into(mImageView);

                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "No Hotel Found", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<IntModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }

    public void callPhoneNumber(String phone)
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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