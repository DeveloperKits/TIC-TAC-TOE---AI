package com.example.tictoctoe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.example.tictoctoe.Game.TicTacToe_Minimax_algo;
import com.example.tictoctoe.Game.TicTocToe_Easy_Algo;
import com.example.tictoctoe.databinding.CustomDialogEnterGameBinding;

import java.util.HashMap;
import java.util.Random;

public class EnterGameDialog {

    private CustomDialogEnterGameBinding gameBinding;

    private final Context context;
    private final View view;

    public EnterGameDialog(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    @SuppressLint("SetTextI18n")
    public void createDialog(){
        gameBinding = CustomDialogEnterGameBinding.inflate(LayoutInflater.from(context));
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setView(gameBinding.getRoot());
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);

        gameBinding.cancel.setOnClickListener(view1 -> alertDialog.dismiss());

        gameBinding.done.setOnClickListener(view1 -> {

            Random random = new Random();
            int x = random.nextInt(2), y = random.nextInt(16);

            String[] piece = {"X", "0"};
            String[] OpponentName = {"Toyota", "Mega Man", "Awesom-O", "Bishop", "Clank", "Daft Punk", "Roboto", "Robbie",
                    "Astro Boy", "Roomba", "Cindi", "Rosie", "Terminator", "Sojourner", "Rodriguez", "Wall-E"};

            boolean isHard = true;
            if (gameBinding.radioButton2.isChecked()){
                isHard = false;

            }

            HashMap<String, String> add = new HashMap<>();
            add.put("Piece Image", piece[x]);
            add.put("name", OpponentName[y]);

            Intent intent;

            if (isHard){
                intent = new Intent(context, TicTacToe_Minimax_algo.class);
            }else {
                intent = new Intent(context, TicTocToe_Easy_Algo.class);
            }

            intent.putExtra("hashmap", add);

        });

        alertDialog.show();
    }
}
