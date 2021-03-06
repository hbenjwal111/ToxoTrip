package com.toxootrip.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.adapter.BookingCityAdapter;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.model.SearchList;
import com.toxootrip.model.SearchModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

/**
 * Created by himanshu on 25-02-2018.
 */

public class BookingCityFragment extends BaseFragment {

    private List<SearchModel> modelArrayList;
    private RecyclerView recyclerView;
    private BookingCityAdapter searchAdapter;
    private TextView location,locat;
    private ProgressDialog progressDialog;
    private APIService mAPIService;
    private String id;
    public static final int MY_PERMISSION_REQUEST_LOCATION = 1;

    public BookingCityFragment() {


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_search, parent, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        location = (TextView) rootView.findViewById(R.id.location);
        mAPIService = ApiUtils.getAPIService();
        setHasOptionsMenu(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.addOnItemTouchListener(new SearchFragment.RecyclerTouchListener(getActivity(), recyclerView, new SearchFragment.ClickListener() {



            @Override
            public void onClick(View view, int position) {

            }
            @Override
            public void onLongClick(View view, int position) {

            }

        }));
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        getAllCity();
        return rootView;
    }


    private void getAllCity(){

       /* try {
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(getActivity());
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }*/

        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }


        mAPIService.getAllCity(id).enqueue(new Callback<SearchList>() {

            @Override
            public void onResponse(Call<SearchList> call, Response<SearchList> response) {
                SearchList searchList = response.body();
                if(searchList.getStatus()==1){
                    modelArrayList =  searchList.getSearchModelList();
                    searchAdapter = new BookingCityAdapter(getActivity(),modelArrayList);
                    recyclerView.setAdapter(searchAdapter);
                    progressDialog.dismiss();

                }else{
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<SearchList> call, Throwable t) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }



    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private SearchFragment.ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SearchFragment.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));

                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));

            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchh = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchh);
        search(searchView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
    @Override
    public void setTAG(String TAG) {

    }

    @Override
    public void onClick(View v) {



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }




}

