package com.toxootrip.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.adapter.CategoryAdapter;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.DashBoardList;
import com.toxootrip.model.DashBoardModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import utils.Utils;

/**
 * Created by himanshu on 19-02-2018.
 */

public class CategoryFragment extends BaseFragment {

    private List<DashBoardModel> dashBoardModelList;
    private RecyclerView recyclerView;
    private CategoryAdapter mAdapter;
    private TextView location,locat;
    private MenuItem searchh;
    private LocationManager locationManager;
    private String provider;
    private ProgressDialog progressDialog;
    private APIService mAPIService;
    private AppPreferences mApp;
    private String city_name;
    private String id;
    private String status;
    private String category_id;
    private String category_name;
    public static final int MY_PERMISSION_REQUEST_LOCATION = 1;

    public CategoryFragment() {


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_category, parent, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        location = (TextView) rootView.findViewById(R.id.location);
        mApp = new AppPreferences(getActivity());
        mAPIService = ApiUtils.getAPIService();
        setHasOptionsMenu(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.addOnItemTouchListener(new SearchFragment.RecyclerTouchListener(getActivity(), recyclerView, new SearchFragment.ClickListener() {



            @Override
            public void onClick(View view, int position) {

                DashBoardModel selecteditemPosition = dashBoardModelList.get(position);
                Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                commonActivity.putExtra("flowType",CommonBaseActivity.SUB);
                commonActivity.putExtra("category_id",selecteditemPosition.getCategory_id());
                commonActivity.putExtra("category_name",selecteditemPosition.getCategory_name());

                startActivity(commonActivity);

            }
            @Override
            public void onLongClick(View view, int position) {

            }

        }));
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        getAllCity();
        return rootView;
    }


    private void getAllCity(){

       /* try {
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(getActivity());
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }*/

        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }


        mAPIService.getAllCate(status).enqueue(new Callback<DashBoardList>() {

            @Override
            public void onResponse(Call<DashBoardList> call, retrofit2.Response<DashBoardList> response) {
                DashBoardList hotelList = response.body();
                if (hotelList.getStatus() == 1) {
                    category_id = hotelList.getDashBoardModels().get(0).getCategory_id();
                    category_name = hotelList.getDashBoardModels().get(0).getCategory_name();

                    dashBoardModelList= hotelList.getDashBoardModels();
                    mAdapter = new CategoryAdapter(getActivity(), dashBoardModelList);
                    recyclerView.setAdapter(mAdapter);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "No productFound", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                }

            }
            @Override
            public void onFailure(Call<DashBoardList> call, Throwable t) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private SearchFragment.ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SearchFragment.ClickListener clickListener) {
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



   /* public String hereLocation(double lat,double lng){

        String curCity = "";

        Geocoder geocoder= new Geocoder(getActivity(), Locale.getDefault());

        List<Address> addressList ;

        try{

            addressList = geocoder.getFromLocation(lat,lng,1);


                curCity = addressList.get(0).getAddressLine(0);





        }catch (Exception e){

            e.printStackTrace();
        }

        return curCity;


    }
*/


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
