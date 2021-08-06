package com.nirogo.www;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

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

import static com.nirogo.www.HomeFragment.CAT;

public class SubCategoryActivity extends AppCompatActivity implements SubCategoryAdapter.OnItemClickListener {

    List<SubCategoryList> productList;
    RecyclerView catrecycler;
    private ProgressDialog progressDialog;
    TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        progressDialog = new ProgressDialog(this);
        productList = new ArrayList<>();

        final Intent asdf = getIntent();
        //Toast.makeText(getApplicationContext(), asdf.getStringExtra(CAT), Toast.LENGTH_LONG).show();
        heading = findViewById(R.id.heading);
        heading.setText(asdf.getStringExtra(CAT));

        catrecycler = findViewById(R.id.recyclercategory);
        catrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        catList();
    }


    private void catList() {


        final Intent aa =getIntent();
        final String category_name = aa.getStringExtra(CAT);
      //  Toast.makeText(getApplicationContext(),"HELLO 2"+category_name,Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.SUB_CAT_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting product object from json array
                                JSONObject product = jsonArray.getJSONObject(i);

                                //Toast.makeText(getActivity(), "CAT"+product.getString("SHP_CAT"), Toast.LENGTH_LONG).show();
                                //Toast.makeText(getActivity(), "DESC"+product.getString("SHP_DESC"), Toast.LENGTH_LONG).show();
                                //adding the product to product list
                                productList.add(new SubCategoryList(
                                        product.getInt("id"),
                                        product.getString("product_name"),
                                        product.getString("img"),
                                        product.getString("desc")



                                ));
//                                Toast.makeText(getApplicationContext(),"HELLO"+product.getString("product_name"),Toast.LENGTH_LONG).show();


                            }


                            SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this, productList);
                            catrecycler.setAdapter(adapter);
                            adapter.setOnItemClickListener(SubCategoryActivity.this);
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
                params.put("cat", category_name);
                return params;
            }
        };
        Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext())).add(stringRequest);
        // }

    }

    @Override
    public void onItemClick(int position) {

        SubCategoryList clickedItem = productList.get(position);
       // Toast.makeText(getApplicationContext(), "Under development", Toast.LENGTH_LONG).show();


        SharedPrefManager.getInstance(getApplicationContext())
                .subCatName(
                        clickedItem.getSHP_CAT()

                );
            Intent detailIntent = new Intent(getApplicationContext(), VenderActivity.class);
            detailIntent.putExtra("SUB_CAT_NAME", String.valueOf(clickedItem.getSHP_CAT()));
            startActivity(detailIntent);
        }

}