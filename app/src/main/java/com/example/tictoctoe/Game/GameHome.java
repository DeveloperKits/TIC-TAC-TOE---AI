package com.example.tictoctoe.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tictoctoe.EnterGameDialog;
import com.example.tictoctoe.R;
import com.example.tictoctoe.databinding.ActivityGameHomeBinding;


public class GameHome extends AppCompatActivity {

    private ActivityGameHomeBinding gameHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameHomeBinding = ActivityGameHomeBinding.inflate(getLayoutInflater());
        View view = gameHomeBinding.getRoot();
        setContentView(view);

        //gameHomeBinding.coronaRunner.setOnClickListener(view1 -> startActivity(new Intent(this, Corona_Runner.class)));

        gameHomeBinding.ticTocToe.setOnClickListener(view1 -> {
            @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.custom_dialog_enter_game,null);
            EnterGameDialog gameDialog = new EnterGameDialog(this, mView);
            gameDialog.createDialog();
        });

        gameHomeBinding.back.setOnClickListener(view1 -> onBackPressed());
    }
}