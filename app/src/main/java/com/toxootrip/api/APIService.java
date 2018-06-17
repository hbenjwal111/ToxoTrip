package com.toxootrip.api;

import com.toxootrip.model.AddBusinessModel;
import com.toxootrip.model.AllProductList;
import com.toxootrip.model.AnotherModel;
import com.toxootrip.model.CategoryList;
import com.toxootrip.model.CustomerLoginModel;
import com.toxootrip.model.CustomerRegisterModel;
import com.toxootrip.model.DashBoardList;
import com.toxootrip.model.ForgetModel;
import com.toxootrip.model.ImageList;
import com.toxootrip.model.IntModel;
import com.toxootrip.model.InternationList;
import com.toxootrip.model.LikesModel;
import com.toxootrip.model.MyProfileModel;
import com.toxootrip.model.PasswordModel;
import com.toxootrip.model.ProductDetailList;
import com.toxootrip.model.ProductDetailModel;
import com.toxootrip.model.ProductModel;
import com.toxootrip.model.ProductSearchList;
import com.toxootrip.model.SearchList;
import com.toxootrip.model.SearchProductList;
import com.toxootrip.model.SearchProductModel;
import com.toxootrip.model.TypeList;
import com.toxootrip.model.UpdateprofileModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by gagan.mathur on 9/4/2017.
 */

public interface APIService {

    @POST("/api/toxotrip_signin.php")
    @FormUrlEncoded
    Call<CustomerLoginModel> loginPost(
            @Field("email") String email,
            @Field("password") String password
          );


    @POST("/api/toxotrip_signup.php")
    @Multipart
    Call<CustomerRegisterModel> registerPost(

            @Part("username") String username,
            @Part("password") String password,
    @Part("name") String name,
    @Part("email") String email,
    @Part("phone") String phone,
   @Part MultipartBody.Part file);

    @POST("/api/toxotrip_signup.php")
    @FormUrlEncoded
    Call<CustomerRegisterModel> registePost(

            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone);

    @POST("/api/toxotrip_forgot_pwd.php")
    @FormUrlEncoded
    Call<ForgetModel> forgetpOST(


            @Field("email") String email
           );



    @POST("/api/toxotrip_forgot_pwd.php")
    @FormUrlEncoded
    Call<ForgetModel> otpPost(

        @Field("user_id") String user_id,
        @Field("otp") String otp);


    @POST("/api/toxotrip_forgot_pwd.php")
    @FormUrlEncoded
    Call<PasswordModel> confirmPost(

            @Field("user_id") String user_id,
    @Field("new_password") String new_password );


    @POST("/api/toxotrip_city.php")
    @FormUrlEncoded
    Call<SearchList> getAllCity(

            @Field("country_id") String country_id


    );

    @POST("/api/hotel_list.php")
    @FormUrlEncoded
    Call<ImageList> getAllHotel(

            @Field("city_id") String city_id
            );




    @POST("/api/product_type.php")
    @FormUrlEncoded

    Call<TypeList> getAllType(

            @Field("category_id") String category_id




    )
;



    @POST("/api/get_category.php")
    @FormUrlEncoded

    Call<CategoryList> getAllCategory(

            @Field("status") String status




    )
            ;



    @POST("/api/add_product.php")
    @Multipart


    Call<ProductModel> getAllProduct(

            @Part("user_id") String user_id,
            @Part("type_id") String type_id,
            @Part("category_id") String category_id,
            @Part("product_title") String product_title,
            @Part("product_discription") String product_discription,
            @Part("product_price") String product_price,
            @Part("current_city")String  current_city,
            @Part("product_country")String product_country,
            @Part MultipartBody.Part file1,
                        @Part MultipartBody.Part file2


    )

            ;

    @POST("/api/all_product.php")
    @Multipart

    Call<AllProductList> getAllProduct(

            @Part("category_id") String category_id,
            @Part("country") String country,
            @Part("city")String city




    )
            ;


    @POST("/api/product.php")
    @FormUrlEncoded

    Call<ProductDetailModel> getProductDetail(

            @Field("product_id") String product_id,
            @Field("user_id") String user_id






);

    @POST("/api/toxotrip_hotel_detail.php")
    @FormUrlEncoded

    Call<AnotherModel> getHotelDetail(

            @Field("hotel_id") String hotel_id





    );

    @POST("/api/toxotrip_hotel_detail.php")
    @FormUrlEncoded

    Call<AnotherModel> getHotelDetaill(

            @Field("hotel_id") String hotel_id





    );


    @POST("/api/product.php")
    @FormUrlEncoded

    Call<ProductDetailList> getMyAd(

            @Field("user_id") String user_id





    );


    @POST("/api/product.php")
    @FormUrlEncoded

    Call<ProductSearchList> getSearch(

            @Field("product_title") String product_title





    );

    @POST("/api/toxotrip_country.php")
    @FormUrlEncoded
    Call<InternationList> getAllCountry(

            @Field("country_name") String country_name


    );


    @POST("/api/toxotrip_international_detail.php")
    @FormUrlEncoded
    Call<IntModel> getAllHotelCountry(

            @Field("country_id") String country_id
    );

    @POST("/api/toxotrip_user_detail.php")
    @FormUrlEncoded
    Call<MyProfileModel> getAllProfile(

            @Field("user_id") String user_id
    );


    @POST("/api/toxotrip_user_update.php")
    @Multipart
    Call<UpdateprofileModel> updatePost(

            @Part("user_id") String user_id,

            @Part MultipartBody.Part file

           );

    @POST("/api/get_category.php")
    @FormUrlEncoded
    Call<DashBoardList> getAllCate(

            @Field("status") String status
    );

    @POST("/api/business.php")
    @FormUrlEncoded
    Call<AddBusinessModel> getBusiness(

            @Field("user_id") String user_id,


                                @Field("company_name") String company_name,

                                @Field("mobile_no") String mobile_no,

                                @Field("discription") String discription,
             @Field("city") String city,
             @Field("owner_name") String owner_name,

            @Field("owner_email") String owner_email








    );

    @POST("/api/business.php")
    @FormUrlEncoded
    Call<AddBusinessModel> getFeedBack(

            @Field("user_id") String user_id,




            @Field("mobile_no") String mobile_no,

            @Field("discription") String discription

);


    @POST("/api/product_rating.php")
    @FormUrlEncoded
    Call<LikesModel> getAllLikes(

            @Field("user_id") String user_id,


            @Field("product_id") String product_id,

            @Field("like") String like
    );


    @POST("/api/all_product.php")
    @FormUrlEncoded

    Call<SearchProductList> getAllProductSearch(

            @Field("search_name") String search_name,



    @Field("city") String city






    )
            ;

    @POST("/api/all_product.php")
    @FormUrlEncoded

    Call<SearchProductModel> getSearchName(

            @Field("search_name") String search_name,
            @Field("city") String city

    )
            ;

}

