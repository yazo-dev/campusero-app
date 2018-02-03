package com.frezarin.campusparty.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;



public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 3000;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Service.getAccessToken(getApplicationContext()) != null) {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);

                    finish();
                }
                else {
                    OpenLoginActivity();
                }
            }
        }, SPLASH_TIMEOUT);
    }


    private void OpenLoginActivity(){
        Pair<View, String> p1 = Pair.create(findViewById(R.id.logo), "Logo");
        Pair<View, String> p2 = Pair.create(findViewById(R.id.background), "Background");

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(SplashScreenActivity.this, p1, p2);

        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        ActivityCompat.startActivity(SplashScreenActivity.this, intent, options.toBundle());
        finish();
    }
}
