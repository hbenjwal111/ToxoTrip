package com.toxootrip.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.extect.appbase.BaseFragment;
import com.squareup.picasso.Picasso;
import com.toxootrip.R;
import com.toxootrip.iImage;

/**
 * Created by himanshu on 08-06-2018.
 */

public class FullImageFragment extends BaseFragment  {




    private String product_url;

    /** Called when the activity is first created. */

       public FullImageFragment(){


       }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_fullimage, parent, false);
        product_url = getActivity().getIntent().getExtras().getString("product_url");
        iImage view = (iImage) rootView.findViewById(R.id.imagefull);

        Picasso.with(getActivity())
                .load(product_url)
                .into(view);
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
