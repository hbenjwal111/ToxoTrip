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
import com.toxootrip.model.DashBoardModel;

import java.util.List;

/**
 * Created by himanshu on 19-02-2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context context;
    private List<DashBoardModel> listIteem;

    public static final String IMAGE_URL_BASE_PATH = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and

    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;

        public ImageView mImageView;
        public TextView mTextView;

        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);

            mImageView = (ImageView)v.findViewById(R.id.id_index_gallery_item_image);
            mTextView = (TextView)v.findViewById(R.id.id_index_gallery_item_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CategoryAdapter(Context context, List<DashBoardModel> listIteem) {
        this.context = context;
        this.listIteem = listIteem;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        // set the view's size, margins, paddings and layout parameters
       /* ClusterAdapter.MyViewHolder vh = new ClusterAdapter.MyViewHolder(v);*/
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.MyViewHolder holder, int position) {

        final DashBoardModel constant = listIteem.get(position);


        String image_url = IMAGE_URL_BASE_PATH + constant.getIcon();


        Picasso.with(context)
                .load(image_url)
                .into(holder.mImageView);
        holder.mTextView.setText(constant.getCategory_name());











    }





    @Override
    public int getItemCount() {
        return listIteem.size();
    }




}



