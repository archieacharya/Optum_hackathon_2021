package com.nirogo.www;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DocterDetailActivity extends AppCompatActivity  implements TimeTableAdapter.OnItemClickListener {

    CircleImageView img;
    RecyclerView recyclerTimeList;
    TextView name, experience, location, designation, tv_time;
    private ProgressDialog progressDialog;
    List<TimeTableList> timeList;
    TextView heading;
    String IMGG, NAMEE, LOCC;
    String date, starttime, endtime, amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_detail);

        name = findViewById(R.id.shop_name);
        designation = findViewById(R.id.desig);
        img = findViewById(R.id.icongrain);
        experience = findViewById(R.id.exp);
        location = findViewById(R.id.adresssssss);
        progressDialog = new ProgressDialog(this);
        recyclerTimeList = findViewById(R.id.time_list);
        recyclerTimeList.setLayoutManager(new LinearLayoutManager(this));
        Checkout.preload(getApplicationContext());
        heading = findViewById(R.id.heading);
        heading.setText("Select a time slot");

        timeList = new ArrayList<>();

        gettimeListttt();

        docterInfo();
    }


    private void docterInfo() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        final Intent intent = getIntent();
        final String mer_id = intent.getStringExtra("DOCTER_ID");
        final String nameee = intent.getStringExtra("DOCTER_NAME");
        final String imageee = intent.getStringExtra("DOCTER_IMAGE");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.DOCTER_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject jsonArray = new JSONObject(response);
                            name.setText(nameee);
                            designation.setText(jsonArray.getString("MERI_DESIGNATION"));
                            experience.setText(jsonArray.getString("MERI_EXP"));
                            location.setText(jsonArray.getString("MERI_ADD"));
                            LOCC = jsonArray.getString("MERI_ADD");

                                     Glide.with(getApplicationContext())
                                    .load(imageee)
                                    .into(img);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mer_id", mer_id);
                return params;
            }
        };
        Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext())).add(stringRequest);
        // }

    }

    private void gettimeListttt() {


        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        final Intent intent = getIntent();
        final String mer_id = intent.getStringExtra("DOCTER_ID");

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.TIME_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting product object from json array
                                JSONObject product = jsonArray.getJSONObject(i);

                                //adding the product to product list
                                timeList.add(new TimeTableList(
                                        product.getString("TM_ID"),
                                        product.getString("TM_MER_ID"),
                                        product.getString("TM_DATE"),
                                        product.getString("TM_START_TM"),
                                        product.getString("TM_END_TM"),
                                        product.getString("TM_AMT"),
                                        product.getString("TM_STS")


                                ));

                            }

                            TimeTableAdapter adapter = new TimeTableAdapter(DocterDetailActivity.this, timeList);
                            recyclerTimeList.setAdapter(adapter);
                            adapter.setOnItemClickListener(DocterDetailActivity.this);
                        }  catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Please check your Internet and Try Again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mer_id", mer_id);
                return params;
            }
        };


        Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext())).add(stringRequest);




    }




    @Override
    public void onItemClick(int position) {
        TimeTableList clickedItem = timeList.get(position);
       if(clickedItem.getTM_STS().equals("Available")){
           final Intent intent = getIntent();
           Intent detailIntent = new Intent(getApplicationContext(), PaymentActivity.class);
           detailIntent.putExtra("START_TIME", String.valueOf(clickedItem.getTM_START_TM()));
           detailIntent.putExtra("END_TIME", String.valueOf(clickedItem.getTM_END_TM()));
           detailIntent.putExtra("DATE", String.valueOf(clickedItem.getTM_DATE()));
           detailIntent.putExtra("AMOUNT", String.valueOf(clickedItem.getTM_AMT()));
           detailIntent.putExtra("TM_ID",String.valueOf(clickedItem.getTM_ID()));
           detailIntent.putExtra("IMG",intent.getStringExtra("DOCTER_IMAGE"));
           detailIntent.putExtra("NAME",intent.getStringExtra("DOCTER_NAME"));
           detailIntent.putExtra("LOCATION",LOCC);
           startActivity(detailIntent);
       }else {
           Toast.makeText(getApplicationContext(),"This slot is not available. please choose another slot!",Toast.LENGTH_LONG).show();
       }
    }


}