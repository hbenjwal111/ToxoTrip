package com.toxootrip.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.adapter.MyFavAdapter;
import com.toxootrip.api.AppPreferences;

/**
 * Created by himanshu on 12-02-2018.
 */

public class MyFaviourate extends BaseFragment {

    private TextView textView;

    private AppPreferences mAppPreferences;

    private String product_title;

    private RecyclerView recyclerView;

    private MyFavAdapter mAdapter;


    public MyFaviourate(){


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_myfav, parent, false);

        mAppPreferences = new AppPreferences(getActivity());


        recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        setHasOptionsMenu(true);



       /* cartList = new ArrayList<>();*/


        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        recyclerView.addOnItemTouchListener(new AllProductFragment.RecyclerTouchListener(getActivity(), recyclerView, new AllProductFragment.ClickListener() {
            @Override
            public void onClick(View view, int position) {



            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));







        return rootView;

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
