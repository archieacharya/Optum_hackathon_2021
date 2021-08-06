package com.nirogo.www;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.Delayed;

public class SharedPrefManager {

    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;

    public static ArrayList<HashMap<String, String>> product_id_cnt_two = new ArrayList<>();

    private static final ArrayList<String>  P_ID = new ArrayList<>();
    public static String ttlprz = "0";
    public static String ttlitem = "0";
    private static final ArrayList<String>  P_NAME = new ArrayList<>();
    private static final ArrayList<String>  P_QUANTITY = new ArrayList<>();
    private static final ArrayList<String>  P_PRICE = new ArrayList<>();
    private static final ArrayList<String>  P_IMG = new ArrayList<>();
    //private static final ArrayList<HashMap<String, String>> ordlist = new ArrayList<>();
   private static final String SHARED_PREF_NAMEWW = "mysharedpref12ss";
    private static final String DOCTER_SHARED_PREF_NAMEWW = "DOCTER_SHARED_PREF_NAMEWW";
    private static final String SHARED_PREF_LOCATION = "mysharedpref12ww";
    private static final String SHARED_PREF_SHOP_TYPE = "mysharedpref12rr";
    private static final String DOCTER_ID = "DOCTER_ID";

    public static String SHOP_TYPECOMPLEX = "mysharedpref12";

   private static final String KEY_USERNAME = "usernameaaa";
   private static final String KEY_MOBILE = "mobileaaaa";

    private static final String CAT_ID = "usernameshbbs";
    private static final String CAT_TYPE = "mobilesdjs";
   private static final String KEY_USER_EMAIL = "useremailaaaasdd";
   private static final String KEY_USER_ID = "userideeddd";
    private static final String SHORTADDRESS = "Location";
    private static final String LONGADDRESS = "Locationshort";
    private static final String LONGITUDE = "otpe";
    private static final String LATITUDE = "otpr";
    private static final String LATLONGBOTH = "otpt";
    private static final String MERCHANT_LATITUDE = "merotpr";
    private static final String MERCHANT_LATLONGBOTH = "merchotpt";
    private static final String SHARED_PREF_ORD = "otpyedrtsd";
    private static final String SHOP_DETAIL = "mysharedpresbjsfjsff12";
    private static final String SHOP_NAME = "otpu";
    private static final String PERMANENT_SHOP_NAME = "permaotpu";
    private static final String SHOP_TYPE = "otpi";
    private static final String SHOP_ID = "otpo";
    private static final String SHOP_LICENSE = "otpp";
    private static final String SHOP_AREA = "otppsss";
    private static final String PERMANENT_SHOP_AREA = "otppssspermanent";
    private static final String SHOP_DISTANCE = "shopdistanceeee";
    private static final String PERMANENT_SHOP_DISTANCE = "shopdistanceeee";
    private static final String MERCHANT_USER_ID = "otppsssrrrrwse";
    private static final String PERMANENT_MERCHANT_USER_ID = "otppsssrrrrwseoeoodod";
    private static final String MER_LAT = "hagjjadjmerlat";
    private static final String MER_LON = "otppsssrrrrwsemerlon";
    private static final String PERMANENT_MER_LAT = "hagjjadjmerlatpermanent";
    private static final String PERMANENT_MER_LON = "otppsssrrrrwsemerlonpermanent";
    private static final String TTL_PRICE = "totalpriceeee";
    private static final String TTL_ITEM = "totalitemmmmmm";
    private static final String DELIVERY_ISTRUCTION = "totalitemmmmmm";

    private static final String DC = "totalitemmmmmmddknskndk";
    private static final String PC = "totalitemmmmmmpccc";
    private static final String TAX = "totalitemmmmmmtaxxxx";
    private static final String TOTAL_PAY = "totalitemmmmmmttttttaaaapayyyyyy";
    private static final String CAT_NAME = "cat_name";
    public static final String SUB_CAT_NAME = "subcat_name";




    private int TOTALITEMS = 0;

    // for order

    SharedPrefManager(Context context) {
        mCtx = context;

    }

    static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    void shopName(String shopname){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SHOP_NAME, shopname);

        editor.apply();

    }

    void clearShopName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(SHOP_NAME).apply();
        editor.apply();
    }

    public String getShopName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHOP_NAME, null);
    }


    void setDeliveryIstruction(String instruction){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(DELIVERY_ISTRUCTION, instruction);

        editor.apply();

    }

    void clearDeliveryInstruction(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(DELIVERY_ISTRUCTION).apply();
        editor.apply();
    }

    public String getDeliveryIstruction(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DELIVERY_ISTRUCTION, null);
    }

    void shopId(String shopid){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHOP_ID, shopid);
        editor.apply();

    }

    void merLat(String merlat){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MER_LAT, merlat);
        editor.apply();

    }

    void catName(String merlat){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CAT_NAME, merlat);
        editor.apply();

    }

    void subCatName(String shjs){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SUB_CAT_NAME, shjs);
        editor.apply();

    }



    public String getCatName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CAT_NAME, null);
    }
    public String getSubCatName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SUB_CAT_NAME, null);
    }
    void merLon(String merlon){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MER_LON, merlon);
        editor.apply();

    }

    void clearShopId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(SHOP_ID).apply();
        editor.apply();
    }

    public String getShopId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHOP_ID, null);
    }

    public String getMerLat(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MER_LAT, null);
    }
    public String getMerLon(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MER_LON, null);
    }

    void shopLicense(String shoplicense){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHOP_LICENSE, shoplicense);
        editor.apply();

    }

    void clearShopLicense(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(SHOP_LICENSE).apply();
        editor.apply();
    }

    public String getMerchantDistance(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHOP_DISTANCE, null);
    }


    void MerchantDistance(String shopdistance){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHOP_DISTANCE, shopdistance);
        editor.apply();

    }

    void clearMerchantDistance(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(SHOP_DISTANCE).apply();
        editor.apply();
    }

    public String getShopLicense(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHOP_LICENSE, null);
    }


    void catType(String shoptype){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CAT_ID, shoptype);
       // editor.putString(CAT_TYPE, shoptype);
        editor.apply();

    }

    void clearCatpType(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(CAT_ID).apply();
       // sharedPreferences.edit().remove(CAT_TYPE).apply();
        editor.apply();
    }

    public String getCatId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CAT_ID, null);
    }

    public String getCatType(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CAT_TYPE, null);
    }

    void shopArea(String shoparea){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SHOP_AREA, shoparea);

        editor.apply();

    }

    void clearShopArea(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(SHOP_AREA).apply();
        editor.apply();
    }

    public String getShopArea(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHOP_AREA, null);
    }

    void merchantUsersId(String merchant_users_is){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(MERCHANT_USER_ID, merchant_users_is);

        editor.apply();

    }



    void clearMerchantUsersId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(MERCHANT_USER_ID).apply();
        editor.apply();
    }

    public String getMerchantUsersId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MERCHANT_USER_ID, "not");
    }





    void userLogin(String id, String mobile, String email, String username){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMEWW, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, id);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USERNAME, username);

        editor.apply();

    }



    boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMEWW, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MOBILE, null) != null;
    }

    void logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMEWW, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(KEY_USER_ID).apply();
        sharedPreferences.edit().remove(KEY_MOBILE).apply();
        sharedPreferences.edit().remove(KEY_USER_EMAIL).apply();
        sharedPreferences.edit().remove(KEY_USERNAME).apply();
        editor.apply();
    }

    public String getIddd(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMEWW, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null);
    }



    public String getMobile(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMEWW, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MOBILE, null);
    }


    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMEWW, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }



    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMEWW, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }


    void userLocation(String longaddressss, String shortaddressss, String latitude, String longitude, String latlong){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_LOCATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(LONGADDRESS, longaddressss);
        editor.putString(SHORTADDRESS, shortaddressss);
        editor.putString(LATITUDE, latitude);
        editor.putString(LONGITUDE, longitude);
        editor.putString(LATLONGBOTH, latlong);


        editor.apply();

    }

    void clearLocation(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_LOCATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(LONGADDRESS).apply();
        sharedPreferences.edit().remove(SHORTADDRESS).apply();
        sharedPreferences.edit().remove(LATITUDE).apply();
        sharedPreferences.edit().remove(LONGITUDE).apply();
        sharedPreferences.edit().remove(LATLONGBOTH).apply();
        editor.apply();
    }

    public String getLongaddress(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_LOCATION, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LONGADDRESS, null);
    }

    public String getShortaddress(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_LOCATION, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHORTADDRESS, null);
    }

    public String getlatitude(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_LOCATION, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LATITUDE, "not");
    }

    public String getLongitude(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_LOCATION, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LONGITUDE, "not");
    }

    public String getLatlongboth(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_LOCATION, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LATLONGBOTH, null);
    }





    public String getShopType(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_LOCATION, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHOP_TYPECOMPLEX, null);
    }

    void docterId(String shopname){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(DOCTER_SHARED_PREF_NAMEWW, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(DOCTER_ID, shopname);

        editor.apply();

    }


    public String getDocterID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(DOCTER_SHARED_PREF_NAMEWW, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PERMANENT_SHOP_NAME, null);
    }


    void permanentmerchantUsersId(String merchant_users_is){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PERMANENT_MERCHANT_USER_ID, merchant_users_is);

        editor.apply();

    }

    void permanentclearMerchantUsersId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(PERMANENT_MERCHANT_USER_ID).apply();
        editor.apply();
    }

    public String permanentgetMerchantUsersId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PERMANENT_MERCHANT_USER_ID, "not");
    }



    void permanentshopArea(String shoparea){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PERMANENT_SHOP_AREA, shoparea);

        editor.apply();

    }

    void permanentclearShopArea(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(PERMANENT_SHOP_AREA).apply();
        editor.apply();
    }

    public String permanentgetShopArea(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PERMANENT_SHOP_AREA, null);
    }



    void permanentMerLat(String shoparea){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PERMANENT_MER_LAT, shoparea);

        editor.apply();

    }

    void permanentclearMerLat(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(PERMANENT_MER_LAT).apply();

        editor.apply();
    }

    public String permanentgetMerLat(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PERMANENT_MER_LAT, null);
    }


    void permanentMerLon(String shoparea){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PERMANENT_MER_LON, shoparea);

        editor.apply();

    }

    void permanentclearMerLon(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(PERMANENT_MER_LON).apply();
        editor.apply();
    }

    public String permanentgetMerLon(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PERMANENT_MER_LON, null);
    }



    void permanentMerDistance(String shoparea){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PERMANENT_SHOP_DISTANCE, shoparea);

        editor.apply();

    }

    void permanentclearMerDistance(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(PERMANENT_SHOP_DISTANCE).apply();
        editor.apply();
    }

    public String permanentgetMerDistance(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PERMANENT_SHOP_DISTANCE, null);
    }



    void setDc(String shoparea){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(DC, shoparea);

        editor.apply();

    }

    void clearDc(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(DC).apply();
        editor.apply();
    }

    public String getDc(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DC, null);
    }

    void setPc(String shoparea){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PC, shoparea);

        editor.apply();

    }

    void clearPc(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(PC).apply();
        editor.apply();
    }

    public String getPc(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PC, null);
    }

    void setTax(String shoparea){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TAX, shoparea);

        editor.apply();

    }

    void clearTax(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(TAX).apply();
        editor.apply();
    }

    public String getTax(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TAX, null);
    }

    void setTTL_PAY(String shoparea){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TOTAL_PAY, shoparea);

        editor.apply();

    }

    void clearTTL_PAY(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove(TOTAL_PAY).apply();
        editor.apply();
    }

    public String getTTL_PAY(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHOP_DETAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOTAL_PAY, null);
    }


}
