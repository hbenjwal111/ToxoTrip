package com.toxootrip.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toxootrip.R;
import com.toxootrip.model.TypeModel;

import java.util.ArrayList;

/**
 * Created by himanshu on 20-01-2018.
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder>  {
    private Context context;
    private ArrayList<TypeModel> searchModels;




    public int id;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and

    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;

        public TextView textView;




        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            textView = (TextView)v.findViewById(R.id.tv_text1);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TypeAdapter(Context context, ArrayList<TypeModel> list) {

        this.context = context;
        this.searchModels = list;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_type, parent, false);
        // set the view's size, margins, paddings and layout parameters
       /* ClusterAdapter.MyViewHolder vh = new ClusterAdapter.MyViewHolder(v);*/
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(TypeAdapter.MyViewHolder holder, int position) {



        final TypeModel constant = searchModels.get(position);

        holder.textView.setText(constant.getType_name());

    }


    @Override
    public int getItemCount() {
        return searchModels.size();
    }


}

