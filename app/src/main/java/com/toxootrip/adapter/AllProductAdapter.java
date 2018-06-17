package com.toxootrip.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.toxootrip.R;
import com.toxootrip.activity.CustommFilter;
import com.toxootrip.model.AllProductModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by himanshu on 25-01-2018.
 */

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.MyViewHolder> implements Filterable {
    private Context context;
    public List<AllProductModel> allProductModelList,filterList;
    CustommFilter filter;




    public static final String IMAGE_URL_BASE_PATH = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and

    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public TextView textView;
        public ImageView mImageView;
        public TextView mDate;
        public TextView mPrice;
        String date, time;
        java.util.Calendar calendar;
        SimpleDateFormat timeFormat, dateFormat;








        public MyViewHolder(View v) {


            super(v);





            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.item_name);
            textView = (TextView)v.findViewById(R.id.item_price);
            mImageView = (ImageView)v.findViewById(R.id.product_thumb);
            mDate = (TextView)v.findViewById(R.id.iteam_avilable);
            mPrice = (TextView)v.findViewById(R.id.item_date);

            calendar = java.util.Calendar.getInstance(Locale.getDefault());


            timeFormat = new SimpleDateFormat("HH:mm a");
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            date = dateFormat.format(calendar.getTime());
            time = timeFormat.format(calendar.getTime());



        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AllProductAdapter(Context context, List<AllProductModel> allProductModelList) {
        this.context = context;
        this.allProductModelList = allProductModelList;
        this.filterList = allProductModelList;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_allproduct, parent, false);
        // set the view's size, margins, paddings and layout parameters
       /* ClusterAdapter.MyViewHolder vh = new ClusterAdapter.MyViewHolder(v);*/
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AllProductAdapter.MyViewHolder holder, int position) {






        final AllProductModel constant = allProductModelList.get(position);







      String image_url = IMAGE_URL_BASE_PATH + constant.getProduct_url();


        Picasso.with(context)
                .load(image_url)
                .into(holder.mImageView);
        holder.mTextView.setText(constant.getProduct_title());




        holder.textView.setText(constant.getProduct_price());

        holder.mDate.setText(constant.getProduct_discription());

        holder.mPrice.setText(constant.getProduct_date());












    }


    @Override
    public int getItemCount() {
        return allProductModelList.size();
    }



    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustommFilter(filterList,this);
        }

        return filter;
    }




}


