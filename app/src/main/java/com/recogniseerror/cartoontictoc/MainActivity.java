package com.recogniseerror.cartoontictoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.recogniseerror.cartoontictoc.Game.GameHome;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            Intent goToMain;
            goToMain = new Intent(MainActivity.this, GameHome.class);
            startActivity(goToMain);
            finish();
        },1200);
    }
}