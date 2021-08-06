package com.nirogo.www;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImageView iv = findViewById(R.id.iv);
        TextView ab = findViewById(R.id.TextViewBottomTitle);
        Animation anime;
        anime = AnimationUtils.loadAnimation(this,R.anim.mytransition);

        iv.startAnimation(anime);
        ab.startAnimation(anime);
        final Intent i = new Intent(this,SignIn.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
