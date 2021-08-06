package com.nirogo.www;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static android.Manifest.permission.CALL_PHONE;

public class AppointmentDetailsHistory extends AppCompatActivity {

    ImageView img;
    TextView doc_name, doc_loc, ord_id, date, slot, amount;
    Button call, whatsapp, direction;
    String lat, lon, whatsapp_number, call_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details_history);
        img = findViewById(R.id.icongrain);
        doc_name = findViewById(R.id.shop_name);
        doc_loc = findViewById(R.id.adresssssss);
        ord_id = findViewById(R.id.ord_id);
        date = findViewById(R.id.dateeedee);
        slot = findViewById(R.id.time);
        amount = findViewById(R.id.ttl_price);
        call = findViewById(R.id.call);
        whatsapp = findViewById(R.id.whats);
        direction = findViewById(R.id.direction);

        final Intent obj2 = getIntent();
        Glide.with(getApplicationContext())
                .load(obj2.getStringExtra("IMG"))
                .into(img);
        doc_name.setText(obj2.getStringExtra("SHOP_NAME"));
        doc_loc.setText(obj2.getStringExtra("ADD"));
        ord_id.setText("ORDER ID: "+obj2.getStringExtra("ORD_ID"));
        date.setText("DATE: "+obj2.getStringExtra("DATE"));
        slot.setText("SLOT: "+obj2.getStringExtra("START_TIME")+" - "+obj2.getStringExtra("END_TIME"));
        amount.setText("AMOUNT: INR "+obj2.getStringExtra("PRICE"));
        lat = obj2.getStringExtra("LAT");
        lon = obj2.getStringExtra("LON");
        call_number = obj2.getStringExtra("CALL");
        whatsapp_number = obj2.getStringExtra("WHATSAPP");


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Toast.makeText(OrderDetailsHistory.this, "OTP: "+otp, Toast.LENGTH_LONG).show();
                // Toast.makeText(OrderDetailsHistory.this, "MOBILE: "+merchant_mob, Toast.LENGTH_LONG).show();

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+91"+call_number));//change the number
                if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{CALL_PHONE}, 1);
                    }
                }

            }
        });


        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String text = "Welcome to nirogo chat";
//                    String toNumber = "919937798098";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+whatsapp_number +"&text="+text));
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lon));
                startActivity(intent);


            }
        });


    }


}