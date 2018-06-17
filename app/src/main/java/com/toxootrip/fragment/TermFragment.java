package com.toxootrip.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;

/**
 * Created by himanshu on 10-02-2018.
 */

public class TermFragment extends BaseFragment {

    public TermFragment() {


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_term, parent, false);
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
