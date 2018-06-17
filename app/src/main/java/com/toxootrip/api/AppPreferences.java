package com.toxootrip.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class AppPreferences {
        private static final String SHARED_PREFERENCE_NAME = "TEREX_APP";
        public static final String IS_LOGIN = "IS_LOGIN";
        public static final String IS_FB_LOGIN = "IS_FB_LOGIN";
        public static final String USER_ID = "user_id";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PHONE_NUMBER = "phonenumber";
        public static final String PRODUCT_TITLE = "product_title";
        public static final String PROFILE_URL="profile_image";
        public static final String CITY_ID="id";
        public static final String CURRENT_CITY="current_city";
        public static final String CATEGORY_ID="category_id";
        public static final String CATEGORY_NAME="category_name";
        public static final String CITY_NAME ="city_name";
        public static final String CURRENT_COUNTRY = "product_country";
        public static final String COUNTRY_NAME = "country_name";
    public static final String SEARCH_NAME = "search_name";

        public static final String FB_ACCESS_TOKEN = "token";
        public static final String FB_FIRST_NAME = "first_name";
        public static final String FB_EMAIL = "fb_email";
        public static final String FB_PROFILE_URL = "fb_profile";
        public static final String FB_PROFILE_ID = "id";
        public static final String LOCATION= "curcity";
        public static  final String PASSWORD = "password";
        public static final String USER_NAME = "USER_NAME";
        public static final String FIREBASE_ID = "FIREBASE_ID";
        public static final String CAB_TYPES = "CAB_TYPES";


        public SharedPreferences mPrefs;
        private Context context;

       private SharedPreferences.Editor editor;



    public AppPreferences(Context context) {
        this.context = context;

        mPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE);


    }



    public void saveallCity(Context context,String city_name){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(CITY_NAME , city_name);


        editor.apply(); //4


    }

    public String getAllCityName(Context context) {
        String city_name;

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        city_name = mPrefs.getString(CITY_NAME, null);
        return city_name;
    }

    public void saveId(Context context,String user_id){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(USER_ID , user_id);


        editor.apply(); //4
    }
    public void saveCity(Context context,String id){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(CITY_ID, id);


        editor.apply(); //4

    }
    public String getCity(Context context) {
        String id;

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        id = mPrefs.getString(CITY_ID, null);
        return id;
    }

    public void saveCurrent(Context context,String current_city){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(CURRENT_CITY, current_city);


        editor.apply(); //4

    }
    public String getCurrent(Context context) {
        String current_city;

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        current_city = mPrefs.getString(CURRENT_CITY, null);
        return current_city;
    }

    public void saveCurrentCountry(Context context,String product_country){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(CURRENT_COUNTRY, product_country);


        editor.apply(); //4

    }
    public String getCurrentCountry(Context context) {
        String product_country;

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        product_country = mPrefs.getString(CURRENT_COUNTRY, null);
        return product_country;
    }

    public void saveNameCountry(Context context,String country_name){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(COUNTRY_NAME, country_name);


        editor.apply(); //4

    }

    public String getCurrentCountryName(Context context) {
        String country_name;

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        country_name = mPrefs.getString(COUNTRY_NAME, null);
        return country_name;
    }

    public void saveProfile(Context context,String mediaPath){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(PROFILE_URL, mediaPath);


        editor.apply(); //4

    }

    public String getProfile(Context context) {
        String mediaPath;

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        mediaPath = mPrefs.getString(PROFILE_URL, null);
        return mediaPath;
    }





    public void saveEmail(Context context,String email){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(EMAIL , email);


        editor.apply(); //4
    }

    public void saveName(Context context,String name){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(NAME , name);


        editor.apply(); //4
    }

    public void savePhone(Context context,String phone){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(PHONE_NUMBER , phone);


        editor.apply(); //4
    }


    public String getValue(Context context) {
        String user_id;

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        user_id = mPrefs.getString(USER_ID, null);
        return user_id;
    }

    public String getValue1(Context context){

        String email;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
       email = mPrefs.getString(EMAIL, null);
        return email;
    }
    public String getValue2(Context context){

        String name;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        name = mPrefs.getString(NAME, null);
        return name;
    }

    public String getValue3(Context context){

        String phone;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        phone = mPrefs.getString(PHONE_NUMBER, null);
        return phone;
    }


    public void saveAccessToken(Context context,String token) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mPrefs.edit();
        editor.putString(FB_ACCESS_TOKEN, token);
        editor.apply(); // This line is IMPORTANT !!!
    }

    public String getToken(Context context) {
        String token;
        mPrefs= PreferenceManager.getDefaultSharedPreferences(context);
        token = mPrefs.getString(FB_ACCESS_TOKEN,null);
        return token;
    }

    public void clearToken(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mPrefs.edit();
        editor.remove(FB_ACCESS_TOKEN);
        editor.remove(FB_EMAIL);
        editor.remove(FB_FIRST_NAME);
        editor.apply(); // This line is IMPORTANT !!!
    }
    public void saveFaceBookFirstName(Context context ,String first_name){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mPrefs.edit();
        editor.putString(FB_FIRST_NAME ,first_name);
        editor.apply(); // This line is IMPORTANT !!!
    }

    public String getFaceBookFirstName(Context context){
        String first_name;
        mPrefs= PreferenceManager.getDefaultSharedPreferences(context);
        first_name= mPrefs.getString(FB_FIRST_NAME,null);
        return first_name;

    }

    public void saveFaceBookEmail(Context context ,String fb_email ){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mPrefs.edit();
        editor.putString(FB_EMAIL,fb_email);
        editor.apply(); // This line is IMPORTANT !!!

    }

    public String getFaceBookEmail(Context context){

        String fb_email;
        mPrefs= PreferenceManager.getDefaultSharedPreferences(context);
        fb_email= mPrefs.getString(FB_EMAIL,null);
        return fb_email;
    }

    public void saveFaceBookProfile(Context context,String fb_profile){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mPrefs.edit();
        editor.putString(FB_PROFILE_URL,fb_profile);
        editor.apply(); // This line is IMPORTANT !!!

    }

    public String getFaceBookprofile(Context context){

        String fb_profile;
        mPrefs= PreferenceManager.getDefaultSharedPreferences(context);
       fb_profile= mPrefs.getString(FB_PROFILE_URL,null);
        return fb_profile;

    }


    public void saveFacebookUserInfo( String last_name, String email, String gender, String profileURL, String facebookId){
        mPrefs= PreferenceManager.getDefaultSharedPreferences(context);
        editor= mPrefs.edit();
        editor.putString("fb_last_name", last_name);
        editor.putString("fb_email", email);
        editor.putString("fb_gender", gender);
        editor.putString("fb_profileURL", profileURL);
        editor.putString("facebook_facebookId",facebookId);
        editor.apply(); // This line is IMPORTANT !!!
    }

    public void getFacebookUserInfo(){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void removeValue(Context context) {
        mPrefs= PreferenceManager.getDefaultSharedPreferences(context);
        editor= mPrefs.edit();


        editor.remove(USER_ID);
        editor.apply();
    }

    public void removeCity(Context context){

        mPrefs =PreferenceManager.getDefaultSharedPreferences(context);

        editor= mPrefs.edit();


        editor.remove(CURRENT_CITY);
        editor.apply();

    }




    public void savecategory(Context context,String category_id){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(CATEGORY_ID, category_id);


        editor.apply(); //4
    }


    public String getCategoryy(Context context) {
        String category_id;

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        category_id = mPrefs.getString(CATEGORY_ID, null);
        return category_id;
    }
    public void savecat(Context context,String search_name){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        editor = mPrefs.edit(); //2

        editor.putString(SEARCH_NAME, search_name);


        editor.apply(); //4
    }


    public String getCategoryyy(Context context) {
        String search_name;

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        search_name = mPrefs.getString(SEARCH_NAME, null);
        return search_name;
    }

}






