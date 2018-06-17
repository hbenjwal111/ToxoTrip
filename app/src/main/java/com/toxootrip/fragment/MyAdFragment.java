package com.toxootrip.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.adapter.AllProductAdapter;
import com.toxootrip.adapter.MyAdAdapter;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.ProductDetailList;
import com.toxootrip.model.ProductDetailModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import utils.Utils;

/**
 * Created by himanshu on 29-01-2018.
 */

public class MyAdFragment extends BaseFragment {

    private  String user_id;
    private AppPreferences mAppPreferences;
    private RecyclerView recyclerView;
    private AllProductAdapter mAdapter;
    List<ProductDetailModel> allProductModel;
    private APIService mAPIService;
    private TextView editText;
    private String status;
    private ProgressDialog progressDialog;
    private FloatingActionButton floatingActionButton;
    private String date;
    BottomNavigationView bottomNavigationView;

    public MyAdFragment(){


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_myad, parent, false);
        floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView title = new TextView(getActivity());

                final TextView message = new TextView(getActivity());
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setCanceledOnTouchOutside(true);
                title.setText("Coming Soon");

                title.setPadding(10, 10, 10, 10);
                title.setGravity(Gravity.CENTER);
                title.setTextColor(Color.BLACK);
                title.setTextSize(18);
                alertDialog.setCustomTitle(title);


                alertDialog.show();
            }
        });

/*
        bottomNavigationView = (BottomNavigationView)rootView.findViewById(R.id.navigation);
*/mAppPreferences = new AppPreferences(getActivity());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        user_id = mAppPreferences.getValue(getActivity());
        mAPIService = ApiUtils.getAPIService();
        setHasOptionsMenu(true);



       /* cartList = new ArrayList<>();*/

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.addOnItemTouchListener(new AllProductFragment.RecyclerTouchListener(getActivity(), recyclerView, new AllProductFragment.ClickListener() {
            @Override
            public void onClick(View view, int position) {



            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

       /* bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        return true;
                    case R.id.navigation_profile:


                        return true;

                    case navigation_dashboard:

                        Intent commonActivity1 = new Intent(getActivity(),CommonBaseActivity.class);

                        commonActivity1.putExtra("flowType", CommonBaseActivity.FAV);


                        startActivity(commonActivity1);






                        return true;





                }
                return false;
            }
        });


*/
        getProductDetail();





        return rootView;

    }

    private void getProductDetail() {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }


        mAPIService.getMyAd(user_id).enqueue(new Callback<ProductDetailList>() {

            @Override
            public void onResponse(Call<ProductDetailList> call, retrofit2.Response<ProductDetailList> response) {
                ProductDetailList hotelList = response.body();
                if (hotelList.getStatus() == 1) {
                    allProductModel= hotelList.getProductDetailModelList();
                    MyAdAdapter hotelListAdapter = new MyAdAdapter(getActivity(), allProductModel);
                    recyclerView.setAdapter(hotelListAdapter);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "No Ad Found", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }
            @Override
            public void onFailure(Call<ProductDetailList> call, Throwable t) {
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
}
