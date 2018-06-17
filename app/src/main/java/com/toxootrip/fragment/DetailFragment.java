package com.toxootrip.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.adapter.CustomPageAdapter;
import com.toxootrip.adapter.DetailAdapter;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.AnotherModel;

import retrofit2.Call;
import retrofit2.Callback;
import utils.Utils;

/**
 * Created by himanshu on 21-12-2017.
 */

public class DetailFragment extends BaseFragment {


    private DetailAdapter mAdapter;
    private RecyclerView recyclerView;

    private AnotherModel anotherModel ;
    private APIService mAPIService;
    private ProgressDialog progressDialog;
    private FragmentManager fragmentManager;
    private String hotel_id;
    private String user_id;
    private String imag;
    private AppPreferences mAppPreferences;
    private String phone,accessToken,phonee;
    private FloatingActionButton floatingActionButton;

    private ImageView mImageView;
    public static final String IMAGE_URL_BASE_PATH = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";

    private ViewPager viewPager;

    private CustomPageAdapter myAdapter;
    private AdapterViewFlipper adapterViewFlipper;
    public DetailFragment(){


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, parent, false);

        hotel_id = getActivity().getIntent().getExtras().getString("hotel_id");
        mAppPreferences = new AppPreferences(getActivity());
        user_id = mAppPreferences.getValue(getActivity());
        accessToken =mAppPreferences.getToken(getActivity());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view1);

        mAPIService = ApiUtils.getAPIService();
        floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                       if (!(user_id == null)) {

                            callPhoneNumber(phone);

                        }
                        else if(!(accessToken == null)){
                            callPhoneNumber(phone);

                        }else {

                            Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                            commonActivity.putExtra("flowType", CommonBaseActivity.REGISTER);
                            startActivity(commonActivity);
                        }



            }
        });

        setHasOptionsMenu(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        getHotelDetail();
        return rootView;

    }



    private void getHotelDetail() {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }


        mAPIService.getHotelDetail(hotel_id).enqueue(new Callback<AnotherModel>() {

            @Override
            public void onResponse( Call<AnotherModel> call, retrofit2.Response<AnotherModel> response) {

                AnotherModel hotelList = response.body();
                if (hotelList.getStatus() == 1) {

                        phone = String.valueOf(hotelList.getHotleDetail().get(0).getHotel_phone());
                    String myContactNo[] = phone.split(",");
                    phone=myContactNo[0];
                    mAdapter = new DetailAdapter(getActivity(),hotelList);
                    recyclerView.setAdapter(mAdapter);

                    progressDialog.dismiss();
                } else {

                    Toast.makeText(getActivity(), "No Hotel Found", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }
            @Override
            public void onFailure(Call<AnotherModel> call, Throwable t) {
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
