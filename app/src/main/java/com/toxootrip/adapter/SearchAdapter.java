package com.toxootrip.adapter;

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
import com.toxootrip.model.SearchModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by himanshu on 12-12-2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {
    private Context context;
     private List<SearchModel> searchModels;
   private List<SearchModel> filterList;


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
            textView = (TextView) v.findViewById(R.id.tv_text1);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchAdapter(Context context,List<SearchModel> searchModels) {

        this.context =context;
        this.filterList = searchModels;

        this.searchModels = searchModels;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_searchcity, parent, false);
        // set the view's size, margins, paddings and layout parameters
       /* ClusterAdapter.MyViewHolder vh = new ClusterAdapter.MyViewHolder(v);*/
        MyViewHolder holder=new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.MyViewHolder holder, final int position) {



      final SearchModel constant = filterList.get(position);



        holder.textView.setText(constant.getCity_name());


        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  InternationalModel constant

                //InternationalModel selectedItemData = modelArrayList.get(position);
                Intent commonActivity = new Intent(context, CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.CHOOSE);

                commonActivity.putExtra("id", filterList.get(position).getId()+"");



                context.startActivity(commonActivity);
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

                    List<SearchModel> filteredList = new ArrayList<>();


                    for (SearchModel row : searchModels) {

                        if (row.getCity_name().toLowerCase().contains(charString.toLowerCase())) {
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

                filterList = (List<SearchModel>) filterResults.values;
                notifyDataSetChanged();

            }

        };

    }



}



