package com.toxootrip.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.toxootrip.R;
import com.toxootrip.model.ImageMode;

/**
 * Created by himanshu on 4/18/2017.
 */

public class CustomPageAdapter extends PagerAdapter {
    private Context context;
    private ImageMode dataObjectList;
    private LayoutInflater layoutInflater;
    public static final String IMAGE_URL_BASE_PATH = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";


    public CustomPageAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE);
        this.dataObjectList = dataObjectList;
    }



    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = this.layoutInflater.inflate(R.layout.pager_list, container, false);
        ImageView displayImage = (ImageView) view.findViewById(R.id.large_image);
        String image_url = IMAGE_URL_BASE_PATH + dataObjectList.getImage();

        Picasso.with(context)
                .load(image_url)
                .into(displayImage);


        container.addView(view);
        return view;




    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}