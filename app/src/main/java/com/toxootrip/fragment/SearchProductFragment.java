package com.toxootrip.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
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
import com.toxootrip.adapter.SearchProductAdapter;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.SearchProductList;
import com.toxootrip.model.SearchProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import utils.Utils;

/**
 * Created by himanshu on 25-02-2018.
 */

public class SearchProductFragment extends BaseFragment {

    FloatingActionButton floatingActionButton;
    private  String user_id;
    private String country;
    private String city;
    private AppPreferences mAppPreferences;
    private RecyclerView recyclerView;
    private SearchProductAdapter mAdapter;
    List<SearchProductModel> allProductModel;
    private APIService mAPIService;
    private TextView editText;
    private String status;
    private String category_id;
    private ProgressDialog progressDialog;
    private String date;
    private String search_name;

    public SearchProductFragment(){


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_allproduct, parent, false);
        mAppPreferences = new AppPreferences(getActivity());
        category_id = getActivity().getIntent().getExtras().getString("category_id");
        search_name =mAppPreferences.getCategoryyy(getActivity());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        user_id = mAppPreferences.getValue(getActivity());
        city = mAppPreferences.getAllCityName(getActivity());
        country = mAppPreferences.getCurrentCountryName(getActivity());
        mAPIService = ApiUtils.getAPIService();
        setHasOptionsMenu(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.addOnItemTouchListener(new AllProductFragment.RecyclerTouchListener(getActivity(), recyclerView, new AllProductFragment.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SearchProductModel selectedItemPosition = allProductModel.get(position);
                Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                commonActivity.putExtra("flowType",CommonBaseActivity.PRODUCTDETAIL);
                commonActivity.putExtra("product_id",selectedItemPosition.getProduct_id()+"");
                commonActivity.putExtra("product_title",selectedItemPosition.getProduct_title());
                commonActivity.putExtra("product_url",selectedItemPosition.getProduct_url());
                commonActivity.putExtra("product_discription",selectedItemPosition.getProduct_discription());
                commonActivity.putExtra("product_location",selectedItemPosition.getProduct_location());
                startActivity(commonActivity);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

        getProductDetail();
        return rootView;

    }


    private void getProductDetail() {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        mAPIService.getAllProductSearch(search_name,city).enqueue(new Callback<SearchProductList>() {

            @Override
            public void onResponse(Call<SearchProductList> call, retrofit2.Response<SearchProductList> response) {
                SearchProductList hotelList = response.body();
                if (hotelList.getStatus() == 1) {
                    allProductModel= hotelList.getAllProductModels();
                    mAdapter = new SearchProductAdapter(getActivity(), allProductModel);
                    recyclerView.setAdapter(mAdapter);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "No item Found", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }



            @Override
            public void onFailure(Call<SearchProductList> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
        private AllProductFragment.ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final AllProductFragment.ClickListener clickListener) {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
