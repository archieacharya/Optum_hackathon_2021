package com.nirogo.www;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class VenderActivity extends AppCompatActivity implements VenderAdapter.OnItemClickListener {

    List<VenderList> productList;
    RecyclerView catrecycler;
    private ProgressDialog progressDialog;
    TextView heading;


    public static final String SHOP_AREA_LOCAL = "shop_area_local";
    public static final String SHOP_NAME_LOCAL = "shop_name_local";
    public static final String MERCHANT_USERS_ID_LOCAL = "jsjnddwserdddfe";

    public static final String MER_LAT = "jsjnddmerlat";
    public static final String MER_LON = "jsjnddwserdemerlon";
    public static final String MER_DISTANCE = "jsjnddwserdddfemerdistance";
    SharedPrefManager mInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender);

        progressDialog = new ProgressDialog(this);
        productList = new ArrayList<>();

       heading = findViewById(R.id.heading);
        mInstance = new SharedPrefManager(this);
        heading.setText(mInstance.getSubCatName());
        catrecycler = findViewById(R.id.recyclercategory);

        catrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        catList();

    }


    private void catList() {

        mInstance = new SharedPrefManager(this);
        final String category_name = mInstance.getCatName();
        final String sub_category_name = mInstance.getSubCatName();
        final String lat = mInstance.getlatitude();
        final String lon = mInstance.getLongitude();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.SHOP_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject product = jsonArray.getJSONObject(i);
                                productList.add(new VenderList(
                                        product.getString("merchant_users_id"),
                                        product.getString("shop_name"),
                                        product.getString("shop_area"),
                                        product.getString("distance"),
                                        product.getString("mer_lat"),
                                        product.getString("mer_lon"),
                                        product.getString("img")
                                ));

                            }

                            VenderAdapter adapter = new VenderAdapter(VenderActivity.this, productList);
                            catrecycler.setAdapter(adapter);
                            adapter.setOnItemClickListener(VenderActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cat_name", category_name);
                params.put("sub_cat_name", sub_category_name);
                params.put("lat1", lat);
                params.put("lon1", lon);
                return params;
            }
        };
        Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext())).add(stringRequest);
        // }

    }
    @Override
    public void onItemClick(int position) {

        VenderList clickedItem = productList.get(position);
        // Toast.makeText(getApplicationContext(), "Under development"+clickedItem.getMerchant_users_id(), Toast.LENGTH_LONG).show();
        SharedPrefManager.getInstance(getApplicationContext())
                .merchantUsersId(
                        clickedItem.getMerchant_users_id()

                );


        Intent detailIntent = new Intent(getApplicationContext(), DocterDetailActivity.class);
        detailIntent.putExtra("DOCTER_ID", String.valueOf(clickedItem.getMerchant_users_id()));
        detailIntent.putExtra("DOCTER_NAME", String.valueOf(clickedItem.getShop_name()));
        detailIntent.putExtra("DOCTER_IMAGE", String.valueOf(clickedItem.getImg()));
        startActivity(detailIntent);
    }

}