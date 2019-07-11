package com.myapplicationdev.android.project0044;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thrd = new Thread() {
            @Override
            public void run() {
                try{
                    sleep(3000);
                    Intent i = new Intent(getApplicationContext(), CategoryActivity.class);
                    startActivity(i);
                    finish();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thrd.start();
    }
}
