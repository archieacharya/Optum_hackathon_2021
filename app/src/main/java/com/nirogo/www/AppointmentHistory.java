package com.nirogo.www;

import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.Objects;

public class AppointmentHistory extends AppCompatActivity implements AppointmentAdapter.OnItemClickListener {


    List<AppointmentList> productList;
    RecyclerView recyclerView;
    SharedPrefManager mInstance;
    SwipeRefreshLayout swipeLayout;
    private ProgressDialog progressDialog;

    public static final String ORDER_ID = "jsjdaajnjd";//SHOP_AREA
    public static final String TOTAL_PRICE = "jsjdajbjja";
    public static final String MERCHANT_STATUS = "jsjdajbjjarrr";
    public static final String TAX = "jsjdajbjjhjjfjjfjjfarrr";
    public static final String DLV_TM = "jsjdajbddgdhjjhjjfjjfjjfarrr";
    public static final String VLT_USR_OTP = "jsjdsfggajbddgdhjjhjjfjjfjjfarrr";
    public static final String VLT_MOB = "vlt_mob";
    public static final String MER_MOB = "mer_mob";

    public static final String FLATTT = "FLAT";
    public static final String COLONY = "COLONY";
    public static final String LANDMARK = "LANDMARK";
    public static final String PAYMENT_TYPE = "PAYMENT_TYPE";
    public static final String SHP_NME = "shp_nme";
    public static final String SHP_ARA = "shp_ara";
    public static final String USR_ACT_TM = "USR_ACT_TM";

    ImageView img_not_found;
    TextView text_not_found;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);
        swipeLayout = findViewById(R.id.swipeRefreshLayout);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent i = new Intent(getApplicationContext(), AppointmentHistory.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(i);
                finish();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeLayout.setRefreshing(false);
                    }
                }, 4000); // Delay in millis
            }
        });

        // Scheme colors for animation
        swipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorPrimaryDark));



        recyclerView = findViewById(R.id.recycler_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);

        img_not_found = findViewById(R.id.iv_not_found);
        text_not_found = findViewById(R.id.tv_not_found);
        //initializing the productlist
        productList = new ArrayList<>();




        if (isNetworkConnected()){

            loadProducts();

        }

        else {
            //  connected = false;

            img_not_found.setImageResource(R.drawable.servererror);
            text_not_found.setText("Please check your Internet and try again.");
            img_not_found.setVisibility(View.VISIBLE);
            text_not_found.setVisibility(View.VISIBLE);
        }



    }


    public void loadProducts() {
        progressDialog.setMessage("Please wait...");
        mInstance = new SharedPrefManager(this);
        final String user_id = String.valueOf(mInstance.getIddd());


        // Toast.makeText(this.getApplicationContext(), "USER_USERS_ID:- "+user_id, Toast.LENGTH_LONG).show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.USER_ORD_LIST_HISTORY,
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
                                productList.add(new AppointmentList(
                                        product.getInt("id"),
                                        product.getString("order_iddd"),
                                        product.getString("start_time"),
                                        product.getString("total_price"),
                                        product.getString("end_time"),
                                        product.getString("add"),
                                        product.getString("shop_name"),
                                        product.getString("datee"),
                                        product.getString("lat"),
                                        product.getString("lon"),
                                        product.getString("whatsapp"),
                                        product.getString("call"),
                                        product.getString("img")
                                ));



                            }

                            if(jsonArray.length()>0){
                                img_not_found.setVisibility(View.GONE);
                                text_not_found.setVisibility(View.GONE);
                            }else {

                                img_not_found.setVisibility(View.VISIBLE);
                                text_not_found.setVisibility(View.VISIBLE);
                            }
                            AppointmentAdapter adapter = new AppointmentAdapter(AppointmentHistory.this, productList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(AppointmentHistory.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.hide();
                        //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                        img_not_found.setImageResource(R.drawable.servererror);
                        text_not_found.setText("Uh oh something's not right, please try again.");
                        img_not_found.setVisibility(View.VISIBLE);
                        text_not_found.setVisibility(View.VISIBLE);



                    }
                }) {           @Override
        protected java.util.Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("user_id", user_id);
            return params;
        }
        };
        Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext())).add(stringRequest);


    }



    @Override
    public void onItemClick(int position) {

        Intent detailIntent = new Intent(getApplicationContext(), AppointmentDetailsHistory.class);
        AppointmentList clickedItem = productList.get(position);
        detailIntent.putExtra("ORD_ID", clickedItem.getOrd_id());
        detailIntent.putExtra("START_TIME", clickedItem.getStart_time());
        detailIntent.putExtra("PRICE", clickedItem.getTotal_price());
        detailIntent.putExtra("END_TIME", clickedItem.getEnd_time());
        detailIntent.putExtra("ADD", clickedItem.getShop_area());
        detailIntent.putExtra("SHOP_NAME", clickedItem.getShop_name());
        detailIntent.putExtra("DATE", clickedItem.getDate());
        detailIntent.putExtra("LAT",clickedItem.getLat());
        detailIntent.putExtra("LON", clickedItem.getLon());
        detailIntent.putExtra("WHATSAPP", clickedItem.getWhatsapp());
        detailIntent.putExtra("CALL", clickedItem.getCall_number());
        detailIntent.putExtra("IMG",clickedItem.getImg());
        startActivity(detailIntent);
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

  /*  public void onBackPressed(){

        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();


    }

   */


}
