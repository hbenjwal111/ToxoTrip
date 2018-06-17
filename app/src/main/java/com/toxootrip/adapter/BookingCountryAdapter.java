package com.toxootrip.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.InternationalModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by himanshu on 05-03-2018.
 */

public class BookingCountryAdapter extends RecyclerView.Adapter<BookingCountryAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<InternationalModel> searchModels;
    private List<InternationalModel> filterList ;
    private String country_name;
    private AppPreferences mApp;






    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and

    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public TextView textView;
        public ImageView mImageView;



        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            textView = (TextView)v.findViewById(R.id.tv_text1);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BookingCountryAdapter(Context context,List<InternationalModel> list) {

        this.context = context;
        searchModels = list;
        filterList = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
        mApp = new AppPreferences(context);
        // set the view's size, margins, paddings and layout parameters
       /* ClusterAdapter.MyViewHolder vh = new ClusterAdapter.MyViewHolder(v);*/
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {

        final InternationalModel constant = filterList.get(position);
        holder.textView.setText(constant.getCountry_name());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  InternationalModel constant

                //InternationalModel selectedItemData = modelArrayList.get(position);
                Intent commonActivity = new Intent(context, CommonBaseActivity.class);
                commonActivity.putExtra("flowType",CommonBaseActivity.HOTEL);
                country_name = filterList.get(position).getCountry_name();
                mApp.saveNameCountry(context,country_name);
                ((Activity)context).setResult(9, commonActivity);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    filterList = searchModels;
                } else {

                    List<InternationalModel> filteredList = new ArrayList<>();


                    for (InternationalModel row : searchModels) {

                        if (row.getCountry_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }

                    }

                    filterList = filteredList;
                }


                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                filterList = (List<InternationalModel>) filterResults.values;
                notifyDataSetChanged();

            }

        };

    }




}



