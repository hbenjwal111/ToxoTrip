package com.toxootrip.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.adapter.ImageAdapter;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.ImageList;
import com.toxootrip.model.ImageModel;
import com.toxootrip.model.IntModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import utils.Utils;

/**
 * Created by himanshu on 18-12-2017.
 */

public class ImageFragment  extends BaseFragment{

    private RecyclerView recyclerView;
    private ImageAdapter mAdapter;
    private TextView chooseRoom;
    List<ImageModel> commonHotelModels;
    List<IntModel> intModels;
    private AppPreferences mAppPreferences;
    private String user_id;
    private APIService mAPIService;
    Integer cluster_id;
    String  city_id, name, district, hotel_image;
    private String city_name;
    private String country_id;
    private ProgressDialog progressDialog;
    private FragmentManager fragmentManager;

    public ImageFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_image, parent, false);
        city_id = getActivity().getIntent().getExtras().getString("id");
        mAppPreferences = new AppPreferences(getActivity());
        user_id = mAppPreferences.getValue(getActivity());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view1);
        mAPIService = ApiUtils.getAPIService();
        setHasOptionsMenu(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.addOnItemTouchListener(new ImageFragment.RecyclerTouchListener(getActivity(), recyclerView, new ImageFragment.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                ImageModel selectedItemPosition = commonHotelModels.get(position);
                Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                  commonActivity.putExtra("flowType",CommonBaseActivity.HOTELDETAIL);
                commonActivity.putExtra("hotel_id", selectedItemPosition.getHotel_id() + "");
                startActivity(commonActivity);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

        getHotelList();

        return rootView;
    }

    private void getHotelList() {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }


        mAPIService.getAllHotel(city_id).enqueue(new Callback<ImageList>() {

            @Override
            public void onResponse(Call<ImageList> call, retrofit2.Response<ImageList> response) {
                ImageList hotelList = response.body();
                if (hotelList.getStatus() == 1) {
                    commonHotelModels = hotelList.getHotelModelList();
                    ImageAdapter hotelListAdapter = new ImageAdapter(getActivity(), commonHotelModels);
                    recyclerView.setAdapter(hotelListAdapter);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "No Hotel Found", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }
            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


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


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ImageFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ImageFragment.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
