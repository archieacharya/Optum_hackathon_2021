package com.nirogo.www;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static com.nirogo.www.ForgotPassword.MOBILE_NUMBER;


public class ForgetOtpVerifyActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 300000;
    private TextView mTextViewCountDown;

    // private ProgressDialog progressDialog;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    public static final String MOBILE_NUMMMMMM = "mobile";

    private EditText editText1, editText2, editText3, editText4, editText5, editText6;
    private EditText[] editTexts;
    private String otpstring;
    String otpnew;

    private Button ResendOtp, OtpSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_otp_verify);

        editText1 = findViewById(R.id.otp1);
        editText2 = findViewById(R.id.otp2);
        editText3 = findViewById(R.id.otp3);
        editText4 = findViewById(R.id.otp4);
        editText5 = findViewById(R.id.otp5);
        editText6 = findViewById(R.id.otp6);
        editTexts = new EditText[]{editText1, editText2, editText3, editText4, editText5, editText6};

        editText1.addTextChangedListener(new PinTextWatcher(0));
        editText2.addTextChangedListener(new PinTextWatcher(1));
        editText3.addTextChangedListener(new PinTextWatcher(2));
        editText4.addTextChangedListener(new PinTextWatcher(3));
        editText5.addTextChangedListener(new PinTextWatcher(4));
        editText6.addTextChangedListener(new PinTextWatcher(5));

        editText1.setOnKeyListener(new OtpPinOnKeyListener(0));
        editText2.setOnKeyListener(new OtpPinOnKeyListener(1));
        editText3.setOnKeyListener(new OtpPinOnKeyListener(2));
        editText4.setOnKeyListener(new OtpPinOnKeyListener(3));
        editText5.setOnKeyListener(new OtpPinOnKeyListener(4));
        editText6.setOnKeyListener(new OtpPinOnKeyListener(5));

        otpstring = editText1.toString()+editText2.toString()+editText3.toString()+editText4.toString()+editText5.toString()+editText6.toString();

        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        ResendOtp = findViewById(R.id.resendotp);
        OtpSubmit = findViewById(R.id.otpsubmit);

        otpnew = new DecimalFormat("000000").format(new Random().nextInt(999999));


        OtpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp();
                // startActivity(new Intent(getApplicationContext(), SignIn.class));
                //finish();
                // resetTimer();
                // Toast.makeText(getApplicationContext(), "otp is "+editTexts, Toast.LENGTH_LONG).show();
            }
        });
        ResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                resendOtp();
                otpSendToMobile();
                // Toast.makeText(getApplicationContext(), "otp is "+editTexts, Toast.LENGTH_LONG).show();
            }
        });

        // updateCountDownText();


        // Intent intent = getIntent();
        //final String otp = intent.getStringExtra(OTPVER);

        startTimer();

        // Toast.makeText(getApplicationContext(), "otp is "+otp, Toast.LENGTH_LONG).show();

    }

    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst) {
                //  editTexts[currentIndex - 1].requestFocus();
                if (editTexts[currentIndex] == null) {
                    editTexts[currentIndex - 1].requestFocus();
                } else {
                    editTexts[currentIndex].requestFocus();
                }
            }
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            // Toast.makeText(getApplicationContext(), "otp is "+ Arrays.toString(editTexts), Toast.LENGTH_LONG).show();
            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }

    public class OtpPinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        OtpPinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }


    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                // mButtonStartPause.setText("Start");
                // mButtonStartPause.setVisibility(View.INVISIBLE);
                mTextViewCountDown.setText("00:00");
                ResendOtp.setVisibility(View.VISIBLE);
                OtpSubmit.setEnabled(false);
            }
        }.start();

        mTimerRunning = true;
        // mButtonStartPause.setText("pause");
        // mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        startTimer();
        ResendOtp.setVisibility(View.INVISIBLE);
        OtpSubmit.setEnabled(true);

        //mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void verifyOtp() {


        Intent aasss = getIntent();
        //final String name = aa.getStringExtra(NAME);
        final String mobile = aasss.getStringExtra(MOBILE_NUMBER);

       // final Editable mobile1 = editTextMobile.getEditText().getText();
        //final Editable password1 = editTextPassword.getEditText().getText();


       // assert mobile_aa != null;
        //final String mobile = mobile_aa;
        final String otp = editText1.getText().toString().trim()+editText2.getText().toString().trim()+editText3.getText().toString().trim()+editText4.getText().toString().trim()+editText5.getText().toString().trim()+editText6.getText().toString().trim();


        //Toast.makeText(getApplicationContext(), "OTP is "+otp, Toast.LENGTH_LONG).show();
        //progressDialog.setMessage("Registering user...");
        //progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.VERIFY_OTP_MOBILE_SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            if(jsonObject.getString("message").equals("successfull")){
                                Intent i = new Intent(getApplicationContext(), ChangePassword.class);
                                i.putExtra(MOBILE_NUMMMMMM, mobile);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            }

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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //params.put("username", name);
                // params.put("password", password);
                params.put("mobile", mobile);
                params.put("otp", otp);
                return params;
            }
        };


        RequestHandle.getInstance(this).addToRequestQueue(stringRequest);


    }

    private void resendOtp() {


        final Intent aa = getIntent();
        //final String name = aa.getStringExtra(NAME);
        final String mobile = aa.getStringExtra(MOBILE_NUMBER);
        final String otp = otpnew;


        //progressDialog.setMessage("Registering user...");
        //progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.UPDATE_OTP_MOBILE_SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), "OTP resend successfully", Toast.LENGTH_LONG).show();

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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //params.put("username", name);
                // params.put("password", password);
                params.put("mobile", mobile);
                params.put("otp", otp);
                return params;
            }
        };


        RequestHandle.getInstance(this).addToRequestQueue(stringRequest);


    }
    private void otpSendToMobile() {

        final Intent aa = getIntent();

        //final String name = aa.getStringExtra(NAME);
        final String sendto = aa.getStringExtra(MOBILE_NUMBER);
        final String message = otpnew;
        final String username = "t1codegenium";
        final String password = "73930909";
        final String senderId = "MoBBSR";
        // username=xxxx&password=xxxx&sender=senderId&sendto=919xxxx&message=hello

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.MSG_GATEWAY_OTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            // Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Please check your Internet and Try Again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("sender", senderId);
                params.put("sendto", sendto);
                params.put("message", message);
                return params;
            }
        };

        RequestHandle.getInstance(this).addToRequestQueue(stringRequest);
    }
}

