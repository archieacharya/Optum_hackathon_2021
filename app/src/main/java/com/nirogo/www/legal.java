package com.nirogo.www;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class legal extends AppCompatActivity {

    CardView policy, condition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legal);

        policy = findViewById(R.id.cv_policy);
        condition = findViewById(R.id.cv_condition);


        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com/"));
                startActivity(browserIntent);

                }

        });

        condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://amazon.com/"));
                startActivity(browserIntent);


            }

        });


    }
}
