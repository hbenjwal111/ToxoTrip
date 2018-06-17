package com.toxootrip.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.adapter.AllProductAdapter;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.AllProductList;
import com.toxootrip.model.AllProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import utils.Utils;

/**
 * Created by himanshu on 17-01-2018.
 */

public class AllProductFragment extends BaseFragment {

    private  String user_id,country,city,category_id;
    private AppPreferences mAppPreferences;
    private RecyclerView recyclerView;
    private AllProductAdapter mAdapter;
   private List<AllProductModel> allProductModel;
    private APIService mAPIService;
    private ProgressDialog progressDialog;

    public AllProductFragment(){


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_allproduct, parent, false);
        mAppPreferences = new AppPreferences(getActivity());
        category_id = getActivity().getIntent().getExtras().getString("category_id");
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
                AllProductModel selectedItemPosition = allProductModel.get(position);
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

        mAPIService.getAllProduct(category_id,country,city).enqueue(new Callback<AllProductList>() {

            @Override
            public void onResponse(Call<AllProductList> call, retrofit2.Response<AllProductList> response) {
                AllProductList hotelList = response.body();
                if (hotelList.getStatus() == 1) {
                    allProductModel= hotelList.getAllProductModels();
                    mAdapter = new AllProductAdapter(getActivity(), allProductModel);
                    recyclerView.setAdapter(mAdapter);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "No item Found", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }



            @Override
            public void onFailure(Call<AllProductList> call, Throwable t) {

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchh = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchh);
        search(searchView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }



            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
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
}
