package com.toxootrip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.toxootrip.R;
import com.toxootrip.model.AnotherModel;

import java.util.ArrayList;

/**
 * Created by himanshu on 05-06-2018.
 */

public class FlipperAdapter extends BaseAdapter {
    private Context mCtx;
    private ArrayList<AnotherModel> heros;
    public static final String IMAGE_URL_BASE_PATH = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";


    public FlipperAdapter(Context mCtx, ArrayList<AnotherModel> heros){
        this.mCtx = mCtx;
        this.heros = heros;
    }
    @Override
    public int getCount() {
        return heros.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AnotherModel hero = heros.get(position);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.pager_list, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.large_image);
        String image_url = IMAGE_URL_BASE_PATH + heros.get(position).getImage();


        Picasso.with(mCtx)
                .load(image_url)
                .into(imageView);
        return view;
    }
}