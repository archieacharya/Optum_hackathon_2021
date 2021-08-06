package com.nirogo.www;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity implements View.OnClickListener{

    private int backpress;
    private TextInputLayout editTextMobile, editTextPassword;
    private Button buttonLogin;
    TextView forgetpass;
    public static final String MOBILE_NUM = "mobile";
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        progressDialog = new ProgressDialog(this);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        forgetpass = findViewById(R.id.ForgotPwd);
        editTextMobile = findViewById(R.id.edtPhonelayout);
        editTextPassword = findViewById(R.id.edtPasswordLayout);
        buttonLogin = findViewById(R.id.editsign);
        TextView buttonRegister = findViewById(R.id.SignUp);


        buttonLogin.setOnClickListener(this);

        forgetpass.setOnClickListener(this);

        //final String forget_mobile = editTextMobile.getEditText().getText().toString().trim();



        buttonRegister.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(), newsignup.class);
                        startActivity(i);
                    }
                }
        );




    }

    private void userLogin(){

        final Editable mobile1 = editTextMobile.getEditText().getText();
        final Editable password1 = editTextPassword.getEditText().getText();


        final String mobile = mobile1.toString();
        final String password = password1.toString();

        progressDialog.setMessage("Logging you in");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ApiUrl.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getString("id"),
                                                obj.getString("mobile"),
                                                obj.getString("email"),
                                                obj.getString("username")


                                        );

                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("username"),
                                        Toast.LENGTH_LONG
                                ).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", mobile);
                params.put("password", password);
                return params;
            }

        };

        RequestHandle.getInstance(this).addToRequestQueue(stringRequest);
    }



    @Override
    public void onClick(View view) {
        final Editable mobile1 = editTextMobile.getEditText().getText();
        final Editable password1 = editTextPassword.getEditText().getText();


        final String mobile = mobile1.toString().trim();
        final String password = password1.toString().trim();

        if(view == buttonLogin){

            if (mobile.length() == 0) {
                editTextMobile.setError("Enter Mobile Number");
                editTextMobile.requestFocus();
            } else if (mobile.length() != 10) {
                editTextMobile.setError("Enter Correct Mobile Number");
                editTextMobile.requestFocus();
            } else if (password.length() == 0) {
                editTextPassword.setError("Enter Password");
                editTextPassword.requestFocus();
            }else {
                userLogin();
            }

        }

        if(view == forgetpass){


                Intent i = new Intent(view.getContext(), ForgotPassword.class);
               // i.putExtra(MOBILE_NUM, mobile);
                startActivity(i);
                finish();


        }


    }


    public void onBackPressed(){

        backpress = (backpress + 1);
        Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();
        if (backpress>1) {

            moveTaskToBack(true);
            Process.killProcess(Process.myPid());
            System.exit(1);
            finish();
        }

    }


}
