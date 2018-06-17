package com.toxootrip.activity;

import android.widget.Filter;

import com.toxootrip.adapter.AllProductAdapter;
import com.toxootrip.model.AllProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by himanshu on 02-02-2018.
 */

public class CustommFilter extends Filter {

    AllProductAdapter adapter;

    List<AllProductModel> filterList;


    public CustommFilter(List<AllProductModel> filterList, AllProductAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if (constraint != null && constraint.length() > 0) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            List<AllProductModel> filteredPlayers = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                //CHECK
                if (filterList.get(i).getProduct_title().toUpperCase().contains(constraint)) {


                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = filterList.size();
            results.values = filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.allProductModelList = (List<AllProductModel>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }

}