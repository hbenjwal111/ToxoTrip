package com.toxootrip.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toxootrip.R;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.ProductDetailModel;

import java.util.List;

/**
 * Created by himanshu on 13-02-2018.
 */

public class MyFavAdapter extends RecyclerView.Adapter<MyFavAdapter.MyViewHolder> {
    private Context context;
    private List<ProductDetailModel> allProductModelList;
    private AppPreferences mAppPreference;
    private String product_title;


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
        private AppPreferences mAppPreference;

        private Context context;

        private String product_title;

        public MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.item_name);
            textView = (TextView) v.findViewById(R.id.item_price);
            mImageView = (ImageView) v.findViewById(R.id.product_thumb);
            mDate = (TextView) v.findViewById(R.id.iteam_avilable);
            mPrice = (TextView) v.findViewById(R.id.item_date);



            mAppPreference = new AppPreferences(context);



        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyFavAdapter(Context context, List<ProductDetailModel> allProductModelList) {
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
    public void onBindViewHolder(MyFavAdapter.MyViewHolder holder, int position) {


        final ProductDetailModel constant = allProductModelList.get(position);



        holder.mTextView.setText(product_title);

/*

        holder.textView.setText(constant.getProduct_price());

        holder.mDate.setText(constant.getProduct_discription());

        holder.mPrice.setText(constant.getProduct_date());

*/

    }


    @Override
    public int getItemCount() {
        return allProductModelList.size();
    }


}
