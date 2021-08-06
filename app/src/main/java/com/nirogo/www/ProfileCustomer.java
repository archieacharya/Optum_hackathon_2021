package com.nirogo.www;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileCustomer extends AppCompatActivity {

   TextView name, number;
   SharedPrefManager mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_customer);

        name = findViewById(R.id.name);
        number = findViewById(R.id.signupnum1);
        mInstance = new SharedPrefManager(this);
       // String nammmeee = mInstance.getUsername();
        //String numberrr = mInstance.getMobile();
      //  name.setText(mInstance.getUsername());
      //  number.setText(mInstance.getMobile());

        name.setText(mInstance.getUsername());
        number.setText("+91"+mInstance.getMobile());


        //Toast.makeText(this, mInstance.getMobile(), Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Mob: +91"+ mInstance.getMobile(), Toast.LENGTH_LONG).show();


    }
}
