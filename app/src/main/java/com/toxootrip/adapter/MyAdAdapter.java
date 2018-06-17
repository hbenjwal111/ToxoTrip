package com.toxootrip.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.toxootrip.R;
import com.toxootrip.model.ProductDetailModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by himanshu on 29-01-2018.
 */

public class MyAdAdapter  extends RecyclerView.Adapter<MyAdAdapter.MyViewHolder> {
    private Context context;
    private List<ProductDetailModel> allProductModelList;



    private  static final String IMAGE_URL_BASE_PATH = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";


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





        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdAdapter(Context context, List<ProductDetailModel> allProductModelList) {
        this.context = context;
        this.allProductModelList = allProductModelList;
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
    public void onBindViewHolder(MyAdAdapter.MyViewHolder holder, int position) {






        final ProductDetailModel constant = allProductModelList.get(position);







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




}


