package com.nirogo.www;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReferActivity extends AppCompatActivity {


    SharedPrefManager mInstance;
    ImageView whatsapp, facebook, insta, more;
    String app_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);

        facebook = findViewById(R.id.facebook);
        whatsapp = findViewById(R.id.whatsapp);
        insta = findViewById(R.id.insta);
        more = findViewById(R.id.more);

        refferMobbsrLink();


        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent(); sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send. "+app_link+" whatsapp"); sendIntent.setType("text/plain");

                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(); sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, app_link);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.facebook.katana");
                startActivity(sendIntent);
            }
        });


        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent(); sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send. "+app_link+" facebook"); sendIntent.setType("text/plain");

                sendIntent.setPackage("com.instagram.android");
                startActivity(sendIntent);
            }
        });



        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(); sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, app_link);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });



    }


    private void refferMobbsrLink() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.REFFER_MOBBSR_LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject product = new JSONObject(response);

                            app_link = product.getString("link");

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
                }) {           @Override
        protected java.util.Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("app_name", "customer");
            return params;
        }
        };
        Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext())).add(stringRequest);


    }

}
