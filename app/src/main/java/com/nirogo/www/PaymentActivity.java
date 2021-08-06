package com.nirogo.www;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = PaymentActivity.class.getSimpleName();

    public static final String PAYMENT_MODE = "payment_mode";
    public static final String ORDER_ID = "order_id";
    TextView dateee, amount;
    private ProgressDialog progressDialog;
    EditText patient_name, whatsapp, calling_number, email_id;
    SharedPrefManager mInstance;
    Button pay_now;
    TextView heading, doctername, doc_add;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        progressDialog = new ProgressDialog(this);
        dateee = findViewById(R.id.dateeee);
        amount = findViewById(R.id.ttl_price);
        pay_now = findViewById(R.id.pay);
        email_id = findViewById(R.id.email);
        heading = findViewById(R.id.heading);
        img = findViewById(R.id.icongrain);
        doctername = findViewById(R.id.shop_name);
        doc_add = findViewById(R.id.adresssssss);

        final Intent intent = getIntent();
        dateee.setText("DATE: "+intent.getStringExtra("DATE")+", SLOT: "+intent.getStringExtra("START_TIME")+" TO "+(intent.getStringExtra("END_TIME"))+" HRS");
//        time.setText("SLOT: "+intent.getStringExtra("START_TIME")+" TO "+(intent.getStringExtra("END_TIME")));
        amount.setText("AMOUNT: INR "+intent.getStringExtra("AMOUNT"));
        patient_name = findViewById(R.id.name);
        whatsapp = findViewById(R.id.whatsapp);
        calling_number = findViewById(R.id.mobile);
        final Intent obj_intent = getIntent();
        Glide.with(getApplicationContext())
                .load(obj_intent.getStringExtra("IMG"))
                .into(img);
        doctername.setText(obj_intent.getStringExtra("NAME"));
        doc_add.setText(obj_intent.getStringExtra("LOCATION"));

        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (patient_name.length() == 0) {
                    patient_name.setError("Enter Patient Name");
                    patient_name.requestFocus();
                } else if (whatsapp.length() == 0) {
                    whatsapp.setError("Enter Whatsapp Number");
                    whatsapp.requestFocus();
                } else if (calling_number.length() == 0) {
                    calling_number.setError("Enter Calling Number");
                    calling_number.requestFocus();
                }else if (email_id.length() == 0) {
                    email_id.setError("Enter Email ID");
                    email_id.requestFocus();
                }else {
                    startPayment();

//                    String iddddd= "djdjjdd";
//                    ordSubmit(iddddd);
                }



            }
        });


    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();


       // co.setKeyID("rzp_live_IRBUqiZRE3VbrC");

        co.setKeyID("rzp_test_bNPRZK4aFY0p3Q");


        try {
            final Intent intent = getIntent();
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            // options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", Integer.parseInt(intent.getStringExtra("AMOUNT"))*100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", email_id.getText().toString().trim());
            preFill.put("contact", calling_number.getText().toString().trim());

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
           // Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();

            ordSubmit(razorpayPaymentID);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    private void  ordSubmit(final String payment_txnid)  {


        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        mInstance = new SharedPrefManager(this);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date= new SimpleDateFormat("EEEE", Locale.getDefault());
        final String dayName= date.format(calendar.getTime()); //Monday
        date= new SimpleDateFormat("dd", Locale.getDefault());
        final String dayNumber = date.format(calendar.getTime()); //20
        date= new SimpleDateFormat("MMM", Locale.getDefault());
        final String monthName= date.format(calendar.getTime()); //Apr
        date= new SimpleDateFormat("MM", Locale.getDefault());
        final String monthNumber= date.format(calendar.getTime()); //04
        date= new SimpleDateFormat("yyyy", Locale.getDefault());
        final String year= date.format(calendar.getTime()); //2020
        String hour24hrs = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minutes = String.valueOf(calendar.get(Calendar.MINUTE));
        String seconds = String.valueOf(calendar.get(Calendar.SECOND));
        final String orderIdString = (mInstance.getIddd())+dayNumber+monthNumber+year+hour24hrs+minutes+seconds;





        final String cat_name = mInstance.getCatName();
        final String sub_cat_name = mInstance.getSubCatName();
        final String docter_id = mInstance.getMerchantUsersId();
        final String user_id = mInstance.getIddd();
        Intent intentaaa=getIntent();
        final String amt = intentaaa.getStringExtra("AMOUNT");
        final String start_timee = intentaaa.getStringExtra("START_TIME");
        final String end_timee = intentaaa.getStringExtra("END_TIME");
        final String datee = intentaaa.getStringExtra("DATE");
        final String tm_id = intentaaa.getStringExtra("TM_ID");

        final String patient = patient_name.getText().toString().trim();
        final String call = calling_number.getText().toString().trim();
        final String whats = whatsapp.getText().toString().trim();
        final String emai = email_id.getText().toString().trim();

        Toast.makeText(getApplicationContext(), "orderid"+ orderIdString, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "cat_name"+ cat_name, Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "sub_cat_name"+ sub_cat_name, Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "docter_id"+ docter_id, Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "user_id"+ user_id, Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "amt"+ amt, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "start_timee"+ start_timee, Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "end_timee"+ end_timee, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "datee"+ datee, Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "tm_id"+ tm_id, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "patient"+ patient, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "call"+ call, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "whats"+ whats, Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "emai"+ emai, Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "payment_txnid"+ payment_txnid, Toast.LENGTH_LONG).show();







        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.USER_ORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            if(jsonObject.getString("message").equals("Success")) {
                                Intent i = new Intent(getApplicationContext(), OrderSuccess.class);
                                i.putExtra(PAYMENT_MODE, payment_txnid);
                                i.putExtra(ORDER_ID, orderIdString);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Please check your Internet docs", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ORD_ORDER_ID", orderIdString);
                params.put("ORD_DATE", datee);
                params.put("ORD_START_TIME", start_timee);
                params.put("ORD_END_TIME", end_timee);
                params.put("ORD_AMT", amt);
                params.put("ORD_TXN_ID", payment_txnid);
                params.put("ORD_USR_ID",user_id);
                params.put("ORD_PATIENT_NAME", patient);
                params.put("ORD_CALL_NUMBER", call);
                params.put("ORD_WHATSAPP", whats);
                params.put("ORD_MER_ID", docter_id);
                params.put("ORD_CAT", cat_name);
                params.put("ORD_SUBCAT", sub_cat_name);
                params.put("ORD_EMAIL", emai);
                params.put("TM_ID", tm_id);
                return params;
            }
        };

        RequestHandle.getInstance(this).addToRequestQueue(stringRequest);

    }









}