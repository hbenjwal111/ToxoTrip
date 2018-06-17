package com.toxootrip.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.adapter.DashBoardAdapter;
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
 * Created by himanshu on 4/15/2017.
 */



public class DashBoardFragment extends BaseFragment {


    private RecyclerView recyclerView;
    private List<DashBoardModel> dashBoardModelList;
    private DashBoardAdapter mAdapter;
    private APIService mAPIService;
    private ProgressDialog progressDialog;
    private String status;
    private  FloatingActionButton floatingActionButton;
    private  String user_id;
    private AppPreferences mAppPreferences;
    private String category_id;
    private int[] mImgIds;
    private String[] xyz;
    private LinearLayout mGallery;
    private LayoutInflater mInflater;
    private String accessToken;
    public DashBoardFragment(){


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, parent, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view1);
        mAppPreferences = new AppPreferences(getActivity());


        setHasOptionsMenu(true);

        mImgIds = new int[] { R.drawable.hotel, R.drawable.desk, R.drawable.job,R.drawable.car,R.drawable.buyer
        };
        xyz = new String[]{"Hotels","Hotel Booking","Job","Taxi","Shopping"};
        mGallery = (LinearLayout) rootView.findViewById(R.id.id_gallery);

        for (int i = 0; i < mImgIds.length; i++)
        {

            View view = inflater.inflate(R.layout.content,
                    mGallery, false);
            ImageView img = (ImageView) view
                    .findViewById(R.id.id_index_gallery_item_image);
            img.setImageResource(mImgIds[i]);
            TextView txt = (TextView) view
                    .findViewById(R.id.id_index_gallery_item_text);
            txt.setText(xyz[i]);
            mGallery.addView(view);

            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(finalI == 0){

                        Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                        commonActivity.putExtra("flowType",CommonBaseActivity.COUNTRY);
                        startActivity(commonActivity);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                    }else if(finalI == 1){

                        Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                        commonActivity.putExtra("flowType",CommonBaseActivity.HOTEL);
                        startActivity(commonActivity);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }

                    else if(finalI == 2){
                        final TextView title = new TextView(getActivity());
                        final TextView message = new TextView(getActivity());
                        final SpannableString s =
                                new SpannableString(getActivity().getText(R.string.job));
                        Linkify.addLinks(s, Linkify.WEB_URLS);
                        message.setText(s);
                        message.setTextSize(17);
                        message.setPadding(5,0,7,5);
                        message.setMovementMethod(LinkMovementMethod.getInstance());
                        AlertDialog alertDialog;
                        alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setCanceledOnTouchOutside(true);
                        title.setText("You Can Find Here");

                        title.setPadding(10, 10, 10, 10);
                        title.setGravity(Gravity.CENTER);
                        title.setTextColor(Color.BLACK);
                        title.setTextSize(18);
                        alertDialog.setCustomTitle(title);


                        // Setting Dialog Message
                        alertDialog.setView(
                                message);


                        alertDialog.show();

                    }

                    else if(finalI == 3) {
                        final TextView title = new TextView(getActivity());
                        final TextView message = new TextView(getActivity());
                        final SpannableString s =
                                new SpannableString(getActivity().getText(R.string.link));
                        Linkify.addLinks(s, Linkify.WEB_URLS);
                        message.setText(s);
                        message.setTextSize(17);
                        message.setPadding(5,0,7,5);

                        message.setMovementMethod(LinkMovementMethod.getInstance());

                        AlertDialog alertDialog;
                        alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setCanceledOnTouchOutside(true);
                        title.setText("You Can Find Here");

                        title.setPadding(10, 10, 10, 10);
                        title.setGravity(Gravity.CENTER);
                        title.setTextColor(Color.BLACK);
                        title.setTextSize(18);
                        alertDialog.setCustomTitle(title);

                        // Setting Dialog Message
                        alertDialog.setView(
                              message);

                        alertDialog.show();

                    }
                    else if(finalI == 4){
                        final TextView title = new TextView(getActivity());

                        final TextView message = new TextView(getActivity());
                        final SpannableString s =
                                new SpannableString(getActivity().getText(R.string.shop));
                        Linkify.addLinks(s, Linkify.WEB_URLS);
                        message.setText(s);
                        message.setTextSize(17);
                        message.setPadding(5,0,7,5);



                        message.setMovementMethod(LinkMovementMethod.getInstance());

                        AlertDialog alertDialog;
                        alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setCanceledOnTouchOutside(true);
                        title.setText("You Can Find Here");

                        title.setPadding(10, 10, 10, 10);
                        title.setGravity(Gravity.CENTER);
                        title.setTextColor(Color.BLACK);
                        title.setTextSize(18);
                        alertDialog.setCustomTitle(title);

                        // Setting Dialog Message
                        alertDialog.setView(
                                message);


                        alertDialog.show();
                    }
                }
            });
        }








        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        user_id = mAppPreferences.getValue(getActivity());
        accessToken = mAppPreferences.getToken(getActivity());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(user_id == null)){

                    Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                    commonActivity.putExtra("flowType", CommonBaseActivity.CATEGORY);
                    startActivity(commonActivity);

                }


                else {

                    Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                    commonActivity.putExtra("flowType", CommonBaseActivity.REGISTER);
                    startActivity(commonActivity);
                }
            }
        });

        mAPIService = ApiUtils.getAPIService();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItem(getActivity(),DividerItem.VERTICAL));
        recyclerView.addItemDecoration(new DividerItem(getActivity(),DividerItem.HORIZONTAL));
        recyclerView.addOnItemTouchListener(new DashBoardFragment.RecyclerTouchListener(getActivity(), recyclerView, new DashBoardFragment.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                DashBoardModel selecteditemPosition = dashBoardModelList.get(position);
                Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                commonActivity.putExtra("flowType",CommonBaseActivity.ALLPRODUCT);
                commonActivity.putExtra("category_id",selecteditemPosition.getCategory_id());
                startActivity(commonActivity);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

        getCategory();
        return rootView;

    }



    @Override
    public void setTAG(String TAG) {

    }


    private void getCategory() {
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
                    dashBoardModelList= hotelList.getDashBoardModels();
                    mAdapter = new DashBoardAdapter(getActivity(), dashBoardModelList);
                    recyclerView.setAdapter(mAdapter);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "No item Found", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<DashBoardList> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        });


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
        private DashBoardFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final DashBoardFragment.ClickListener clickListener) {
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






