package com.toxootrip.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.extect.appbase.BaseFragment;
import com.squareup.picasso.Picasso;
import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.api.APIService;
import com.toxootrip.api.ApiUtils;
import com.toxootrip.api.AppPreferences;
import com.toxootrip.model.ProductDetailModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

/**
 * Created by himanshu on 25-01-2018.
 */

public class ProductDetailFragment extends BaseFragment {

    private APIService mAPIService;
    private TextInputLayout nameTitle, emailTitle, passwordTitle, usernameTitle, phoneTitle, confirmTitle;
    private EditText nameEdt, emailEdt, passEdt, phoneEdt, usernameEdt, confirmPassEdt;
    private ProgressDialog progressDialog;
    public static final String EXTRA_TEXT_NAME="product_title";
    private String product_id;
    private String product_title;
    private String product_url;
    private String product_discription;
    private String product_location;
    private String product_price;
    private String product_date;
    private String like;
    private RecyclerView recyclerView;
    private  List<ProductDetailModel> productDetailModels;
    private AppPreferences mApppreferences;
    private String phone;
    private String user_id;
    private FloatingActionButton floatingActionButton;
    private EditText title;
    private EditText price;
    private EditText description;
    private EditText locationProduct;
    private EditText date;
    private ImageView mImageView;
    private String accessToken;
    private static final String IMAGE_URL_BASE_PATH = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";
    boolean isImageFitToScreen;

    public ProductDetailFragment(){


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_productdetail, parent, false);

        title = (EditText)rootView.findViewById(R.id.fullNameEt);
        title.setKeyListener(null);
        price = (EditText)rootView.findViewById(R.id.userNameEt);
        price.setKeyListener(null);
        description = (EditText)rootView.findViewById(R.id.emailEt);
        description.setKeyListener(null);
        locationProduct = (EditText)rootView.findViewById(R.id.passwordEt);
        locationProduct.setKeyListener(null);
        date = (EditText)rootView.findViewById(R.id.passworddEt);
        date.setKeyListener(null);
        mImageView = (ImageView)rootView.findViewById(R.id.image);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                commonActivity.putExtra("flowType",CommonBaseActivity.FULLIMAGE);
                commonActivity.putExtra("product_url",product_url);
                startActivity(commonActivity);


/*
                new PhotoFullPopupWindow(getActivity(), R.layout.popup_photo_full, view, product_url, null);
*/
            }
        });

        product_id = getActivity().getIntent().getExtras().getString("product_id");
        product_title = getActivity().getIntent().getExtras().getString("product_title");
        product_url = getActivity().getIntent().getExtras().getString("product_url");
        mApppreferences = new AppPreferences(getActivity());
        Toolbar toolbar = (Toolbar)rootView. findViewById(R.id.toolbar);
        mAPIService = ApiUtils.getAPIService();
        user_id = mApppreferences.getValue(getActivity());
        accessToken = mApppreferences.getToken(getActivity());
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!(user_id == null)){
                    callPhoneNumber(phone);



/*
                    sendEmail(USER_EMAIL);
*/
              //  USER_EMAIL = "";


                }
                else {


                    Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                    commonActivity.putExtra("flowType", CommonBaseActivity.REGISTER);
                    startActivity(commonActivity);
                }
            }
        });



        setHasOptionsMenu(true);
        productDetail();
        return rootView;
    }



    public void productDetail() {
        if (Utils.isNetworkConnected(getActivity(), true, R.style.AppCompatAlertDialogStyle)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }


        mAPIService.getProductDetail(product_id,user_id).enqueue(new Callback<ProductDetailModel>() {
            @Override
            public void onResponse(Call<ProductDetailModel> call, Response<ProductDetailModel> response) {
                ProductDetailModel hotelList = response.body();
                if (hotelList.getStatus() == 1) {
                    productDetailModels= hotelList.getProductDetailModelList();
                    phone=productDetailModels.get(0).getPhone_no();
                    product_title=productDetailModels.get(0).getProduct_title();
                     title.setText(product_title);
                    product_location = productDetailModels.get(0).getProduct_location();
                    locationProduct.setText(product_location);
                    product_discription = productDetailModels.get(0).getProduct_discription();
                    description.setText(product_discription);
                    product_price=productDetailModels.get(0).getProduct_price();
                    price.setText(product_price);
                    product_date = productDetailModels.get(0).getProduct_date();
                    date.setText(product_date);
                    product_url = IMAGE_URL_BASE_PATH +productDetailModels.get(0).getProduct_url();
                    Picasso.with(getActivity())
                            .load(product_url)
                            .into(mImageView);
                    product_id = productDetailModels.get(0).getProduct_id();
/*
                    like = String.valueOf(1);
*/



                    progressDialog.dismiss();
                } else {


                    Toast.makeText(getActivity(), "No product Found", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ProductDetailModel> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                call.cancel();
                progressDialog.dismiss();
            }
        });
    }

    public void callPhoneNumber(String phone) {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_share, menu);

        MenuItem searchh = menu.findItem(R.id.share);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share : {

                if (!(user_id == null)) {

                    Uri uri = Uri.parse("data" + product_url);
                    product_id = productDetailModels.get(0).getProduct_id();
                    product_title = productDetailModels.get(0).getProduct_title();
                    product_url = productDetailModels.get(0).getProduct_url();
                    product_discription = productDetailModels.get(0).getProduct_discription();
                    product_location = productDetailModels.get(0).getProduct_location();
                    product_price = productDetailModels.get(0).getProduct_price();
                   phone = productDetailModels.get(0).getPhone_no();


                    String all = "Find this item in toxoTrip" + "\n" + "Title - " + product_title + "\n" + "Discription - " + product_discription + "\n"
                            + "Location - " + product_location + "\n" + "Price Rs - " + product_price + "\n" + "Contact - " + phone
                            +"\n"+"http://play.google.com/store/apps/details?id=com.toxotrip";


                    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, all);
                    shareIntent.setType("text/plain");

                    try {
                        startActivity(Intent.createChooser(shareIntent, "Send"));
                        getActivity().finish();

                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(),
                                "There is no  client installed.", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }else{
                    Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);
                    commonActivity.putExtra("flowType", CommonBaseActivity.LOGIN);
                    startActivity(commonActivity);

                }



            }

          //  case R.id.like:{

               /* if(!(user_id == null)){

                    getLikes();
                }else{

                    Intent commonActivity = new Intent(getActivity(), CommonBaseActivity.class);

                    commonActivity.putExtra("flowType", CommonBaseActivity.LOGIN);

                    startActivity(commonActivity);
                }



*/


/*

*/
           // }



        }
        return super.onOptionsItemSelected(item);
    }


  /*  private void getLikes() {


            mAPIService.getAllLikes(user_id, product_id, like).enqueue(new Callback<LikesModel>() {
                @Override
                public void onResponse(Call<LikesModel> call, Response<LikesModel> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        if(response.body().getStatus() == 1){


                            showToast("item saved");
                        }else{
                            Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<LikesModel> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");
                    call.cancel();
                    progressDialog.dismiss();
                }
            });
        }





*/
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