package com.toxootrip.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.api.AppPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.toxootrip.R.id.toDate;

/**
 * Created by himanshu on 16-02-2018.
 */

public class BookingFragment extends BaseFragment {

    private TextView fromDate,toDtate,room,adult,children,myLocation,getset,countryChoose;
    java.util.Calendar calendar;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    public static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    private String current_city,city,itemCountry,country,id;
    private AppPreferences mApp;
    int numtestt = 0;



    public BookingFragment() {


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_hotelbooking, parent, false);
        id = getActivity().getIntent().getExtras().getString("id");
        calendar = java.util.Calendar.getInstance(Locale.getDefault());
        mApp = new AppPreferences(getActivity());
        Calendar c=Calendar.getInstance();
        countryChoose = (TextView)rootView.findViewById(R.id.countryEt);
        countryChoose.setOnClickListener(this);
        myLocation = (TextView)rootView.findViewById(R.id.emailEt);
        myLocation.setOnClickListener(this);
        seekLocation();
        fromDate = (TextView)rootView.findViewById(R.id.fromDtae);
        toDtate = (TextView)rootView.findViewById(toDate);
        room = (TextView)rootView.findViewById(R.id.forroom);
        room.setText("1");
        room.setOnClickListener(this);
        adult =(TextView)rootView.findViewById(R.id.foradult);
        adult.setText("1");
        adult.setOnClickListener(this);
        getset = (TextView)rootView.findViewById(R.id.button_previous);
          getset.setOnClickListener(this);
        children =(TextView)rootView.findViewById(R.id.forchild);
        children.setText("0");
        children.setOnClickListener(this);
        dateFormatter=new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        setDateTimeField();

        return rootView;

    }

    @Override
    public void onClick(View view) {
        String currentdate = fromDate.getText().toString();
        String nextdate= toDtate.getText().toString();


        switch (view.getId()) {

            case R.id.fromDtae:

                fromDatePickerDialog.show();
                break;

            case R.id.toDate:
                toDatePickerDialog.show();
                break;

            case R.id.forroom:

                roomDilog();
                break;

            case R.id.foradult:

                roomDilog();
                break;

            case R.id.forchild:
                roomDilog();
                break;
            case R.id.emailEt:

                Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.BOOKINGCITY);

                startActivityForResult(commonActivity, 6);
                break;

            case R.id.countryEt:
                Intent activity = new Intent(getActivity(), CommonBaseActivity.class);
                activity.putExtra("flowType", CommonBaseActivity.BOOKINGCOUNTRY);

                startActivityForResult(activity, 9);
                break;


            case R.id.button_previous:

                if(currentdate.trim().equals("")){

                    showToast("please choose checkIn date");

                    return;
                }if(nextdate.trim().equals("")){
                showToast("please choose checkOut date");

                    return;

            }if(currentdate.equals(nextdate)){
                showToast("Date should not be same");



            }else{
                final TextView title = new TextView(getActivity());

                final TextView message = new TextView(getActivity());
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setCanceledOnTouchOutside(true);
                title.setText("Coming Soon");

                title.setPadding(10, 10, 10, 10);
                title.setGravity(Gravity.CENTER);
                title.setTextColor(Color.BLACK);
                title.setTextSize(18);
                alertDialog.setCustomTitle(title);


                alertDialog.show();
            }
        }

        }


        private void roomDilog(){

            final int[] numtest = {1};

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.alertdilog_layout, null);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setTitle("More Option");


           final Button add = (Button) dialogView.findViewById(R.id.increase);
            final Button sub = (Button) dialogView.findViewById(R.id.decrease);
            final Button addd = (Button)dialogView.findViewById(R.id.increas);
            final Button subb = (Button)dialogView.findViewById(R.id.decreas);
            final Button adddd = (Button)dialogView.findViewById(R.id.increa);
            final Button subbb = (Button)dialogView.findViewById(R.id.decrea);

           final TextView display = (TextView) dialogView.findViewById(R.id.integer_number);
            final TextView displayy = (TextView)dialogView.findViewById(R.id.integer_numbe);
            final TextView displayyy = (TextView)dialogView.findViewById(R.id.integer_numb);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    numtest[0] = numtest[0] + 1;
                    display.setText("" + numtest[0]);

                }
            });

            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numtest[0] < 1) {
                        numtest[0] = 1;
                        display.setText(numtest[0] + "");
                    }
                    if (numtest[0] > 1) {
                        numtest[0] = numtest[0] - 1;
                        display.setText(numtest[0] + "");
                    }
                }
            });

            addd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    numtest[0] = numtest[0] + 1;
                    displayy.setText("" + numtest[0]);

                }
            });

            subb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numtest[0] < 1) {
                        numtest[0] = 1;
                        displayy.setText(numtest[0] + "");
                    }
                    if (numtest[0] > 1) {
                        numtest[0] = numtest[0] - 1;
                        displayy.setText(numtest[0] + "");
                    }
                }
            });

            adddd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    numtest[0] = numtest[0] + 1;
                    displayyy.setText("" + numtest[0]);

                }
            });

            subbb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numtest[0] < 0) {
                        numtest[0] = 0;
                        displayyy.setText(numtest[0] + "");
                    }
                    if (numtest[0] > 0) {
                        numtest[0] = numtest[0] - 1;
                        displayyy.setText(numtest[0] + "");
                    }
                }
            });


            dialogBuilder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    room.setText(display.getText().toString());
                    adult.setText(displayy.getText().toString());
                    children.setText(displayyy.getText().toString());
                }
            });

            dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_LONG).show();
                }
            });

            dialogBuilder.show();

        }





    private void setDateTimeField() {
        fromDate.setOnClickListener(this);
        toDtate.setOnClickListener(this);
        final Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDate.setText(dateFormatter.format( newDate.getTime()));

            }

        },
                newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
        fromDatePickerDialog.setTitle("Checkin Date");

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDtate.setText(dateFormatter.format(newDate.getTime()));


            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
        toDatePickerDialog.setTitle("Checkout Date");



    }

    private void seekLocation(){


        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)

                != PackageManager.PERMISSION_GRANTED){

            if(android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)){
                android.support.v4.app.ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);

            }else{
                android.support.v4.app.ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
        }

        else{
            try {
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if(isNetworkEnabled){

                    Location locationn = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    hereLocation(locationn.getLatitude(), locationn.getLongitude());
                    hereLocation(locationn.getLatitude(), locationn.getLongitude());



                }if(isGPSEnabled){

                    Location locationn = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    hereLocation(locationn.getLatitude(), locationn.getLongitude());
                    hereLocation(locationn.getLatitude(), locationn.getLongitude());

                }
            }catch (Exception e){
                e.printStackTrace();

            }
        }
    }
    public String hereLocation(double lat,double lng){
        Geocoder geocoder= new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addressList ;
        try{
            addressList = geocoder.getFromLocation(lat,lng,1);
            current_city= addressList.get(0).getLocality();
            myLocation.setText(current_city);


            itemCountry= addressList.get(0).getCountryName();

             countryChoose.setText(itemCountry);

            return itemCountry;




        }catch (Exception e){
            e.printStackTrace();
        }
        return current_city;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 6 ) {
            city = mApp.getAllCityName(getActivity());

            myLocation.setText(city);

        }else if(requestCode == 9){

            country = mApp.getCurrentCountryName(getActivity());
            countryChoose.setText(country);
        }

    }
    @Override
    public void setTAG(String TAG) {

    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }
}
