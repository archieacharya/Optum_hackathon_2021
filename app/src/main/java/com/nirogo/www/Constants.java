package com.nirogo.www;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Constants {

    private static final String PACKAGE_NAME = "com.example.mobbsrmodules";
    static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    static final int SUCCESS_RESULT = 1;
    static final int FAILURE_RESULT = 0;



    public static boolean isOnline(Context c) {


            ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                Toast.makeText(c, "No Internet Connection.", Toast.LENGTH_SHORT).show();
            }
            return false;
        }

}
