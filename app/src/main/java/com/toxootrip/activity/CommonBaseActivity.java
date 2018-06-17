package com.toxootrip.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.extect.appbase.BaseActivity;
import com.extect.appbase.BaseFragment;
import com.toxootrip.R;
import com.toxootrip.fragment.AllMapFragment;
import com.toxootrip.fragment.AllMapViewFragment;
import com.toxootrip.fragment.AllProductFragment;
import com.toxootrip.fragment.BookingCityFragment;
import com.toxootrip.fragment.BookingCountryFragment;
import com.toxootrip.fragment.BookingFragment;
import com.toxootrip.fragment.BusinessFragment;
import com.toxootrip.fragment.CategoryFragment;
import com.toxootrip.fragment.ChooseFragment;
import com.toxootrip.fragment.ChooseeFragment;
import com.toxootrip.fragment.CityFragment;
import com.toxootrip.fragment.CountryFindFragment;
import com.toxootrip.fragment.DashBoardFragment;
import com.toxootrip.fragment.DetailFragment;
import com.toxootrip.fragment.DetaillFragment;
import com.toxootrip.fragment.FeedBackFragment;
import com.toxootrip.fragment.ForgetFragment;
import com.toxootrip.fragment.FullImageFragment;
import com.toxootrip.fragment.InternationalFragment;
import com.toxootrip.fragment.LoginFragment;
import com.toxootrip.fragment.MyAdFragment;
import com.toxootrip.fragment.MyProfileFragment;
import com.toxootrip.fragment.NewPasswordFragment;
import com.toxootrip.fragment.OtpFragment;
import com.toxootrip.fragment.ProductDetailFragment;
import com.toxootrip.fragment.RegisterFragment;
import com.toxootrip.fragment.SearchFragment;
import com.toxootrip.fragment.SearchProductFragment;
import com.toxootrip.fragment.SubCategoryFragment;
import com.toxootrip.fragment.TermFragment;
import com.toxootrip.fragment.UploadProductFragment;

import listeners.IBackPressedListner;
import shared.BaseFlyContext;

/**
 * Created by himanshu on 29-11-2017.
 */

public class CommonBaseActivity extends BaseActivity {


    private  int flowStatus;String eventCategory;

    public final static int MENU= 0;
    public final static int HOME =1;
    public final static int SEARCH = 2;
    public final static int REGISTER= 3;
    public final static int FORGET = 4;
    public final static int PASSWORD = 5;
    public final static int CHOOSE = 6;
    public final static int ALLPRODUCT = 7;
    public final static int UPLOADPRODUCT = 8;
    public final static int LOGIN = 9;
    public final static int CATEGORY = 10;
    public final static  int HOTELDETAIL = 11;
    public final static int PRODUCTDETAIL = 12;
    public final static int MYAD = 13;
    public final static int COUNTRY = 14;
    public final static int CHOSEE = 15;
    public final static int SUB= 16;
    public final static int TERM= 17;
    public final static int BUSINESS = 18;
    public final static int FEEDBACK = 19;
    public final static int SEARCHPRODUCT=20;
    public final static int COUNTRYFIND = 21;
    public final static int HOTEL = 22
;
    public final static  int MYPROFILE = 25;
    public final static int BOOKINGCITY = 23;
    public final static int BOOKINGCOUNTRY = 24;
    public final static int HOTELDETAILL = 26;
    public final static int ALLMAP = 27;
    public final static int ALLM=28;
    public final static int OTP =29;
    public final static int FULLIMAGE = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_base_activity_layout);

        mControlsView = findViewById(R.id.fullscreen_content_controls);

        Intent intent = getIntent();
        if (intent != null) {
            flowStatus = intent.getIntExtra("flowType", 0);
            eventCategory = intent.getStringExtra("eventCategory");

        }

        hideSoftKeyboard();
        coordinatiorLayout = (CoordinatorLayout) findViewById(R.id.containerrr);


        BaseFlyContext.getInstant().setActivity(this);

        switch (flowStatus) {

            case BOOKINGCOUNTRY:
                getmActionBar().setTitle("Search here");
                replaceView(R.id.fullscreen_content_controls, new BookingCountryFragment(), true, true);

                break;

            case MENU:

                getmActionBar().setTitle("Search City");
                replaceView(R.id.fullscreen_content_controls, new CityFragment(), true, true);

                break;
            case HOTELDETAILL:
                getmActionBar().setTitle("Hotel Detail");
                replaceView(R.id.fullscreen_content_controls, new DetaillFragment(), true, true);
                DetaillFragment se = new DetaillFragment();
                Bundle b = new Bundle();
                b.putString("country_id", getIntent().getExtras().getString("country_id"));
                se.setArguments(b);



                break;

            case FULLIMAGE:
                replaceView(R.id.fullscreen_content_controls, new FullImageFragment(), true, true);
                FullImageFragment fullImageFragment = new FullImageFragment();
                Bundle full = new Bundle();
                full.putString("product_url", getIntent().getExtras().getString("product_url"));
                fullImageFragment.setArguments(full);
                break;

            case OTP:
                replaceView(R.id.fullscreen_content_controls, new OtpFragment(), true, true);
                OtpFragment otp = new OtpFragment();
                Bundle ot = new Bundle();
                ot.putString("user_id", getIntent().getExtras().getString("user_id"));
                otp.setArguments(ot);

                break;

            case LOGIN:

                getmActionBar().setTitle("Login");
                replaceView(R.id.fullscreen_content_controls, new LoginFragment(), true, true);

                break;

           case SEARCHPRODUCT:

                getmActionBar().setTitle("Search Here");
                replaceView(R.id.fullscreen_content_controls, new SearchProductFragment(), true, true);

                break;
            case MYPROFILE:
                getmActionBar().setTitle("Profile");
                replaceView(R.id.fullscreen_content_controls, new MyProfileFragment(), true, true);

                break;

            case CATEGORY:
                getmActionBar().setTitle("Category");
                replaceView(R.id.fullscreen_content_controls, new CategoryFragment(), true, true);

                break;
            case HOTEL:
                getmActionBar().setTitle("Find Hotel");
                replaceView(R.id.fullscreen_content_controls, new BookingFragment(), true, true);
                BookingFragment selec = new BookingFragment();
                Bundle pro = new Bundle();
                pro.putString("id", getIntent().getExtras().getString("id"));


                selec.setArguments(pro);


                break;
            case SUB:
                getmActionBar().setTitle("Sub Category");

                replaceView(R.id.fullscreen_content_controls, new SubCategoryFragment(), true, true);
                SubCategoryFragment selectProducttt = new SubCategoryFragment();
                Bundle produc = new Bundle();
                produc.putString("category_id", getIntent().getExtras().getString("category_id"));
                produc.putString("category_name", getIntent().getExtras().getString("category_name"));


                selectProducttt.setArguments(produc);
break;
            case SEARCH:

                getmActionBar().setTitle("Choose City");

                replaceView(R.id.fullscreen_content_controls, new SearchFragment(), true, true);
                SearchFragment selectcity = new SearchFragment();
                Bundle city = new Bundle();
                city.putString("country_id", getIntent().getExtras().getString("country_id"));


                selectcity.setArguments(city);

                break;

            case REGISTER:

                getmActionBar().setTitle("Register");
                replaceView(R.id.fullscreen_content_controls, new RegisterFragment(), true, true);

                break;

            case FORGET:

                getmActionBar().setTitle("Forget Password");
                replaceView(R.id.fullscreen_content_controls, new ForgetFragment(), true, true);

                break;

            case PASSWORD:

                getmActionBar().setTitle("Change Password");
                replaceView(R.id.fullscreen_content_controls, new NewPasswordFragment(), true, true);

                break;

            case CHOOSE:

                getmActionBar().setTitle("Hotels");
                replaceView(R.id.fullscreen_content_controls, new ChooseFragment(), true, true);
                ChooseFragment selectAddon = new ChooseFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", getIntent().getExtras().getString("id"));
                selectAddon.setArguments(bundle);

                break;

            case HOTELDETAIL:

                getmActionBar().setTitle("Hotel Detail");
                replaceView(R.id.fullscreen_content_controls, new DetailFragment(), true, true);
                DetailFragment selectProductt = new DetailFragment();
                Bundle productt = new Bundle();
                productt.putString("hotel_id", getIntent().getExtras().getString("hotel_id"));
                selectProductt.setArguments(productt);

                break;

        case ALLMAP:
                replaceView(R.id.fullscreen_content_controls, new AllMapFragment(), true, true);
                AllMapFragment allMapFragment = new AllMapFragment();
                Bundle all= new Bundle();
                all.putString("hotel_id", getIntent().getExtras().getString("hotel_id"));
                allMapFragment.setArguments(all);

                break;
            case ALLM:
                replaceView(R.id.fullscreen_content_controls, new AllMapViewFragment(), true, true);
              AllMapViewFragment allMapviewFragment = new AllMapViewFragment();
                Bundle map= new Bundle();
                map.putString("country_id", getIntent().getExtras().getString("country_id"));
                allMapviewFragment.setArguments(map);

                break;

            case ALLPRODUCT:

                getmActionBar().setTitle("All Advertiesment");
                replaceView(R.id.fullscreen_content_controls, new AllProductFragment(), true, true);
                AllProductFragment selectProduct = new AllProductFragment();
                Bundle product = new Bundle();
                product.putString("category_id", getIntent().getExtras().getString("category_id"));
                selectProduct.setArguments(product);

                break;

            case UPLOADPRODUCT:

                getmActionBar().setTitle("Post Your Ad");
                replaceView(R.id.fullscreen_content_controls, new UploadProductFragment(), true, true);
                UploadProductFragment select = new UploadProductFragment();
                Bundle produ = new Bundle();
                produ.putString("category_id", getIntent().getExtras().getString("category_id"));
                produ.putString("category_name", getIntent().getExtras().getString("category_name"));

                produ.putString("city_name", getIntent().getExtras().getString("city_name"));

                produ.putString("type_id", getIntent().getExtras().getString("type_id"));
                produ.putString("type_name", getIntent().getExtras().getString("type_name"));



                select.setArguments(produ);

                break;

            case PRODUCTDETAIL:

                getmActionBar().setTitle("Product Detail");
                replaceView(R.id.fullscreen_content_controls, new ProductDetailFragment(), true, true);
                ProductDetailFragment selectProductDetail = new ProductDetailFragment();
                Bundle productDetail = new Bundle();
                productDetail.putString("product_id", getIntent().getExtras().getString("product_id"));
                productDetail.putString("product_title", getIntent().getExtras().getString("product_title"));
                productDetail.putString("product_url", getIntent().getExtras().getString("product_url"));
                productDetail.putString("product_discription", getIntent().getExtras().getString("product_discription"));
                selectProductDetail.setArguments(productDetail);

                break;

            case MYAD:

                getmActionBar().setTitle("My Ad");
                replaceView(R.id.fullscreen_content_controls, new MyAdFragment(), true, true);

                break;

            case COUNTRY:

                getmActionBar().setTitle("Choose Your Country");
                replaceView(R.id.fullscreen_content_controls, new InternationalFragment(), true, true);

                break;

            case CHOSEE:

                getmActionBar().setTitle("Hotels");
                replaceView(R.id.fullscreen_content_controls, new ChooseeFragment(), true, true);
                ChooseeFragment selectAddon1 = new ChooseeFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("country_id", getIntent().getExtras().getString("country_id"));
                selectAddon1.setArguments(bundle1);

                break;

            case HOME:

                replaceView(R.id.fullscreen_content_controls, new DashBoardFragment(), true, true);

                break;

            case TERM:

                getmActionBar().setTitle("About Us");
                replaceView(R.id.fullscreen_content_controls, new TermFragment(), true, true);

                break;


            case BUSINESS:

                getmActionBar().setTitle("Add Your Business With Us");
                replaceView(R.id.fullscreen_content_controls, new BusinessFragment(), true, true);

                break;



            case FEEDBACK:

                getmActionBar().setTitle("FeedBack");
                replaceView(R.id.fullscreen_content_controls, new FeedBackFragment(), true, true);

                break;
            case BOOKINGCITY:


                getmActionBar().setTitle("Search Here");
                replaceView(R.id.fullscreen_content_controls, new BookingCityFragment(), true, true);

                break;

            case COUNTRYFIND:

                getmActionBar().setTitle("Search Here");
                replaceView(R.id.fullscreen_content_controls, new CountryFindFragment(), true, true);

                break;

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    int count = 0;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Toast.makeText(getActivity(), "called " + item.getItemId(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case android.R.id.home:
                android.app.FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    //fm.popBackStack();
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                else if(count>0)
                {
                    finish();
                }
                else {
                    onBackPressed();
                    count++;
                    //finish();
                }
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                break;
           /* case R.id.miCompose:

                AddDuaFragment fragobj = new AddDuaFragment();
                Bundle bundle = new Bundle();
                bundle.putString("duaData", "");
                bundle.putInt("duaId",0);
                fragobj.setArguments(bundle);
                getBaseActivity().replaceView(R.id.fullscreen_content_controls, fragobj, true, false);



                break;*/

        }
        return super.onOptionsItemSelected(item);
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    @Override
    public void onResume() {

        // InsuredDataManager.getDataManager().sessionPulse(this, SettingServices.getInstance().getUserName(this));
        super.onResume();
    }
    @Override
    public void replaceView() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {

        hideKeyboard();
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        }
        else {
            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

            if(backStackCount > 0) {

                String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
                if (baseFragment != null && baseFragment.isAllowBackStackNotify && baseFragment instanceof IBackPressedListner) {
                    ((IBackPressedListner)baseFragment).onBackPressNotify();
                }
                else {
                    super.onBackPressed();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void showAlertMissingInfo(String message) {
    }

    /* public void onBackPressed()
     {
         super.onBackPressed();
      *//*FragmentManager fm = getSupportFragmentManager();
     for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
         fm.popBackStack();
     }*//*
        *//*Intent intent=new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("MESSAGE","");
        setResult(2,intent);
        finish();*//*
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }*/
    public void reLounchApp(String errorMsg){/*

        android.app.AlertDialog alert = new android.app.AlertDialog.Builder(BaseFlyContext.getInstant().getActivity(), R.style.AppCompatAlertDialogStyle)
                .setTitle("Message")
                .setMessage(errorMsg)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(ePosMainActivity, EPosLoginActivity.class);
                        i.addCategory(Intent.CATEGORY_LAUNCHER);
                        i.putExtra("flag","1");
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                        dialog.dismiss();
                    }
                }).show();
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(true);
    */}






}
