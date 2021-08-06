package com.nirogo.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class ForgotPassword extends AppCompatActivity {

    EditText mobile_number;
    Button btn_get_otp;
    String otpforget;
    public static  String MOBILE_NUMBER = "mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        otpforget = new DecimalFormat("000000").format(new Random().nextInt(999999));

        mobile_number = findViewById(R.id.mobile_num);
        btn_get_otp = findViewById(R.id.otp_btn);
        //btn_get_otp.setOnClickListener(this);

        //final String mobile = mobile_number.toString().trim();
        btn_get_otp.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {

                        btnClick();


                    }
                }
        );

    }

    private void resendOtp() {

        final String mobile = mobile_number.getText().toString().trim();

        //final Intent aa = getIntent();
        //final String name = aa.getStringExtra(NAME);
       // final String mobile = aa.getStringExtra(MOBILE);
        final String otp = otpforget;


        //progressDialog.setMessage("Registering user...");
        //progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.UPDATE_OTP_MOBILE_SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();

                        try {
                            String mobile = mobile_number.getText().toString().trim();
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            if(jsonObject.getString("message").equals("OTP sent to your mobile number")) {
                                otpSendToMobile();
                                Intent i = new Intent(ForgotPassword.this, ForgetOtpVerifyActivity.class);
                                i.putExtra(MOBILE_NUMBER, mobile);
                                startActivity(i);
                                finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Please check your Internet and Try Again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> params = new HashMap<>();
                //params.put("username", name);
                // params.put("password", password);
                params.put("mobile", mobile);
                params.put("otp", otp);
                return params;
            }
        };


        RequestHandle.getInstance(this).addToRequestQueue(stringRequest);


    }
    private void otpSendToMobile() {

        final String sendto = mobile_number.getText().toString().trim();


        //final Intent aa = getIntent();
        //final String name = aa.getStringExtra(NAME);
        // final String mobile = aa.getStringExtra(MOBILE);
        final String message = otpforget;
        final String username = "t1codegenium";
        final String password = "73930909";
        final String senderId = "MoBBSR";
        // username=xxxx&password=xxxx&sender=senderId&sendto=919xxxx&message=hello

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.MSG_GATEWAY_OTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            // Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Please check your Internet and Try Again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("sender", senderId);
                params.put("sendto", sendto);
                params.put("message", message);
                return params;
            }
        };

        RequestHandle.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void btnClick(){

        String mobile = mobile_number.getText().toString().trim();

        if (mobile.length() == 0) {
            mobile_number.setError("Enter Mobile Number");
            mobile_number.requestFocus();
        } else if (mobile.length() != 10) {
            mobile_number.setError("Enter Correct Mobile Number");
            mobile_number.requestFocus();
        } else {
            resendOtp();
        }


        //Toast.makeText(getApplicationContext(), "forgetotpverify"+MOBILE_NUMBER, Toast.LENGTH_LONG).show();

    }


    public void onBackPressed(){

        Intent i = new Intent(ForgotPassword.this, SignIn.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

}
