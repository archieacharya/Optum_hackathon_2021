package com.nirogo.www;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class newsignup extends AppCompatActivity implements View.OnClickListener {

    public static final String NAME = "name";
    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";
    public static final String OTPVER = "otp";
    String otp;

    private TextInputLayout editTextUsername, editTextPassword, editTextMobile;
    private Button buttonRegisterMain;
    // private CheckBox terms_conditions;
    // int mob,eml;
    // private ProgressDialog progressDialog;

    private TextView textViewLogin, termss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsignup);

        otp = new DecimalFormat("000000").format(new Random().nextInt(999999));

        editTextUsername = findViewById(R.id.Name);
        editTextPassword = findViewById(R.id.signuppwd);
        editTextMobile = findViewById(R.id.signupnum);
        termss = findViewById(R.id.termsss);
        termss.setText(Html.fromHtml("<a href=\"https://google.com\">Terms & Conditions.</a>"));
        termss.setMovementMethod(LinkMovementMethod.getInstance());
        //progressDialog = new ProgressDialog(this);

        textViewLogin = findViewById(R.id.textViewLogin);

        buttonRegisterMain = findViewById(R.id.signupbtn);
        // terms_conditions = findViewById(R.id.terms_conditions);


        buttonRegisterMain.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

//        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
//        try {
//            ColorStateList csl = ColorStateList.createFromXml(getResources(),
//                    xrp);
//
//           // terms_conditions.setTextColor(csl);
//        } catch (Exception ignored) {
//        }

    }

    private void verificationotp() {



        // final String email = editTextEmail.getText().toString().trim();
        final String username = editTextUsername.getEditText().getText().toString().trim();
        final String password = editTextPassword.getEditText().getText().toString().trim();
        final String mobile = editTextMobile.getEditText().getText().toString().trim();
        final String otpdb = otp;

        // progressDialog.setMessage("Registering user...");
        // progressDialog.show();



        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            if(jsonObject.getString("message").equals("OTP sent to your mobile number")){

                                otpSendToMobile();
                                Intent i = new Intent(getApplicationContext(), Verification.class);
                                i.putExtra(MOBILE, mobile);
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
                        // progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Please check your Internet and Try Again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("mobile", mobile);
                params.put("otp", otpdb);
                return params;
            }
        };


        RequestHandle.getInstance(this).addToRequestQueue(stringRequest);


    }


    private void otpSendToMobile() {

        final String sendto = editTextMobile.getEditText().getText().toString().trim();
        final String message = otp;
        final String username = "t1codegenium";
        final String password = "73930909";
        final String senderId = "MoBBSR";


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.MSG_GATEWAY_OTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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
            protected Map<String, String> getParams() {
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

    @Override
    public void onClick(View view) {

        final String name = editTextUsername.getEditText().getText().toString().trim();
        final String password = editTextPassword.getEditText().getText().toString().trim();
        final String mobile = editTextMobile.getEditText().getText().toString().trim();


        if (view == buttonRegisterMain) {
            if (name.length() == 0) {
                editTextUsername.setError("Enter your Full Name");
                editTextUsername.requestFocus();
            }else if (mobile.length() == 0) {
                editTextMobile.setError("Enter your Mobile Number");
                editTextMobile.requestFocus();
            } else if (mobile.length() != 10) {
                editTextMobile.setError("Enter Correct Mobile Number");
                editTextMobile.requestFocus();
            } else if (password.length() == 0) {
                editTextPassword.setError("Enter Password");
                editTextPassword.requestFocus();
            } else if (password.length() < 6) {
                editTextPassword.setError("Password should be at least 6 characters");
                editTextPassword.requestFocus();
            }
//            else if (!terms_conditions.isChecked()) {
//                Toast.makeText(getApplicationContext(), "Please accept Terms & Conditions", Toast.LENGTH_LONG).show();
//            }
            else {
                verificationotp();

            }
        }

        if(view == textViewLogin)
            startActivity(new Intent(this, SignIn.class));

    }

    public void onBackPressed(){

        Intent i = new Intent(this,SignIn.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();


    }
}
