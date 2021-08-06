package com.nirogo.www;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class RequestHandle {
    @SuppressLint("StaticFieldLeak")
    private static RequestHandle mInstance;
    private RequestQueue mRequestQueue;
    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;

    private RequestHandle(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    static synchronized RequestHandle getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestHandle(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    <T> void addToRequestQueue(Request<T> req) {

        getRequestQueue().add(req);
    }

}


