package com.nirogo.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.nirogo.www.ForgetOtpVerifyActivity.MOBILE_NUMMMMMM;

public class ChangePassword extends AppCompatActivity {


    private int backpress;
    EditText newpass1, newpass2;
    Button chngpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newpass1 = findViewById(R.id.forgetpwd1);
        newpass2 = findViewById(R.id.forgetpwd2);
        chngpass = findViewById(R.id.changepwd);



        chngpass.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {

                        String password = newpass1.getText().toString().trim();
                        String cnfmpass = newpass2.getText().toString().trim();

                        if (password.length() == 0) {
                            newpass1.setError("Eneter Password");
                            newpass1.requestFocus();
                        } else if (password.length() < 8) {
                            newpass1.setError("Eneter Atleast 6 digit Password");
                            newpass1.requestFocus();
                        } else if (cnfmpass.length() == 0) {
                            newpass2.setError("Enter Confirm Password");
                            newpass2.requestFocus();
                        } else if (!cnfmpass.equals(password)) {
                            newpass2.setError("Not Matched! Enter Same Password");
                            newpass2.requestFocus();
                        }  else {
                            changePassword();

                            Toast.makeText(getApplicationContext(), "Password changed Successfully", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(i);
                            finish();

                        }



                        //Toast.makeText(getApplicationContext(), "btnclick", Toast.LENGTH_LONG).show();



                    }
                }
        );

    }
    private void changePassword(){

        //Toast.makeText(getApplicationContext(), "changepass", Toast.LENGTH_LONG).show();
        final Intent aa = getIntent();
        //final String name = aa.getStringExtra(NAME);

        final String mobile = aa.getStringExtra(MOBILE_NUMMMMMM);
        final String password = newpass1.getText().toString().trim();
       // Toast.makeText(getApplicationContext(), "mobile"+mobile, Toast.LENGTH_LONG).show();
       // Toast.makeText(getApplicationContext(), "password"+password, Toast.LENGTH_LONG).show();
        //progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.CHANGE_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Please check your Internet and Try Again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> params = new HashMap<>();
                //params.put("username", name);
                // params.put("password", password);
                params.put("mobile", mobile);
                params.put("password", password);
                return params;
            }
        };


        RequestHandle.getInstance(this).addToRequestQueue(stringRequest);


    }


    public void onBackPressed(){

        backpress = (backpress + 1);
        Toast.makeText(getApplicationContext(), " Set new password then go back ", Toast.LENGTH_SHORT).show();
        if (backpress>1) {

            Intent i = new Intent(this, SignIn.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }

    }
}
