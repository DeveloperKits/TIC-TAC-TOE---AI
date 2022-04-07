package com.example.tictoctoe.Game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tictoctoe.R;
import com.example.tictoctoe.databinding.ActivityTicTocToeEasyAlgoBinding;

import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class TicTocToe_Easy_Algo extends AppCompatActivity implements View.OnClickListener {

    private ActivityTicTocToeEasyAlgoBinding binding;
    private String tag, imageHint, computerName;

    private List<Integer> list, userTurnList, computerTurnList, tempIntList;
    private List<Object> tempList;
    private int X, O, user, computer;
    private Random random;
    private boolean have = false, Final = false, check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_toc_toe__easy__algo);
        binding = ActivityTicTocToeEasyAlgoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();

        random = new Random();
        X = R.drawable.fancing;
        O = R.drawable.yinyang;
        list = new ArrayList<>();
        tempIntList = new ArrayList<>();
        userTurnList = new ArrayList<>();
        computerTurnList = new ArrayList<>();

        imageHint = intent.getStringExtra("Piece Image");
        computerName = intent.getStringExtra("name");

        if (imageHint.equals("X")) {
            user = X;
            computer = O;
            binding.yourCell.setImageResource(X);
            binding.opponentCell.setImageResource(O);
        }else {
            user = O;
            computer = X;
            binding.yourCell.setImageResource(O);
            binding.opponentCell.setImageResource(X);
        }

        binding.back.setOnClickListener(view1 -> {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure! Are you ready to lose the game?")

                    .setPositiveButton("Yes", (dialog, which) -> {
                        onBackPressed();
                    })

                    .setNegativeButton("No", null)
                    .show();
        });

        if (!gameIsOver(userTurnList)) {
            binding.button00.setOnClickListener(this);
            binding.button01.setOnClickListener(this);
            binding.button02.setOnClickListener(this);
            binding.button10.setOnClickListener(this);
            binding.button11.setOnClickListener(this);
            binding.button12.setOnClickListener(this);
            binding.button20.setOnClickListener(this);
            binding.button21.setOnClickListener(this);
            binding.button22.setOnClickListener(this);
        } else {
            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
            gameWinDialog("You Win!");
        }
    }

    private void gameWinDialog(String s) {
        new AlertDialog.Builder(this)
                .setTitle("   "+s)
                .setPositiveButton("Back Home", (dialog, which) -> {
                    onBackPressed();
                })

                .setIcon(R.drawable.logo_game) // todo need to change
                .show();
    }

    @Override
    public void onClick(View view) {
        tag = view.getTag().toString();
        ImageView imageView = (ImageView) view;
        Final = false;

        if (!list.contains(Integer.parseInt(tag)) && !gameIsOver(userTurnList)) {
            imageView.setImageResource(user);
            list.add(Integer.parseInt(tag));
            userTurnList.add(Integer.parseInt(tag));

            if (gameIsOver(userTurnList)) {
                Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
                gameWinDialog("You Win!");
            } else if (list.size() < 9 && !gameIsOver(userTurnList) && !gameIsOver(computerTurnList)) {
                computer(userTurnList, computerTurnList);
            } else if (list.size() == 9) {
                if (!gameIsOver(userTurnList) && !gameIsOver(computerTurnList)) {
                    Toast.makeText(this, "Game Tied", Toast.LENGTH_SHORT).show();
                    gameWinDialog("Game Tied");

                } else if (gameIsOver(computerTurnList)) {
                    Toast.makeText(this, computerName + " win!", Toast.LENGTH_SHORT).show();
                    gameWinDialog(computerName+" Win!");

                } else {
                    Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
                    gameWinDialog("You Win!");
                }
            }

        } else if (gameIsOver(userTurnList)) {
            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
            gameWinDialog("You Win!");
        }
    }

    private boolean objectToIntegerList(Object o) {
        String n = o.toString().replaceAll("[\\[\\]]", " ");
        n = n.replaceAll("\\s", "");
        String[] out = n.split(",");

        tempIntList.clear();
        tempIntList.add(Integer.valueOf(out[0]));
        tempIntList.add(Integer.valueOf(out[1]));
        tempIntList.add(Integer.valueOf(out[2]));

        return check(tempIntList);
    }

    private void addListComputer() {
        int num1;
        num1 = random.nextInt(9) + 1;

        while (list.contains(num1)) {
            num1 = random.nextInt(9) + 1;
        }

        computerTurn(num1);
    }

    private void computer(List<Integer> userList, List<Integer> computerList) {
        int num1, num2, num3;
        Collections.sort(computerList);
        Collections.sort(userList);

        num2 = random.nextInt(2) + 2;
        num3 = random.nextInt(2) + 3;

        if (!gameIsOver(computerList)) {
            if (computerList.isEmpty() && userList.isEmpty()) {
                num1 = random.nextInt(9) + 1;
                computerTurn(num1);

            } else if (!userList.isEmpty() && computerList.isEmpty()) {
                if (userList.get(0) == 5) {
                    addListComputer();
                } else {
                    computerTurn(5);
                }

            } /*else if (userList.size() == 2 || computerList.size() == 2 || computerList.size() == num2 || computerList.size() == num3) {
                check = IntelligentComputer(userList, true);
                if (check) {
                    IntelligentComputer(computerList, false);
                }

            }*/ else {
                IntelligentComputer(computerList, false);
            }

        } else {
            Toast.makeText(this, computerName + " win!", Toast.LENGTH_SHORT).show();
            gameWinDialog(computerName+" Win!");
        }
    }

    public void computerTurn(int num1) {
        if (list.size() < 9 && !Final) {
            switch (num1) {
                case 1:
                    binding.button00.setImageResource(computer);
                    break;
                case 2:
                    binding.button01.setImageResource(computer);
                    break;
                case 3:
                    binding.button02.setImageResource(computer);
                    break;
                case 4:
                    binding.button10.setImageResource(computer);
                    break;
                case 5:
                    binding.button11.setImageResource(computer);
                    break;
                case 6:
                    binding.button12.setImageResource(computer);
                    break;
                case 7:
                    binding.button20.setImageResource(computer);
                    break;
                case 8:
                    binding.button21.setImageResource(computer);
                    break;
                default:
                    binding.button22.setImageResource(computer);
                    break;
            }
            list.add(num1);
            computerTurnList.add(num1);
            have = false;
            Final = true;

            if (gameIsOver(computerTurnList)) {
                Toast.makeText(this, computerName + " win!", Toast.LENGTH_SHORT).show();
                gameWinDialog(computerName+" Win!");
            }
        }
    }

    private boolean IntelligentComputer(List<Integer> computerList, boolean b) {
        have = false;

        // check for 1
        if (computerList.contains(1) && computerList.contains(2) && !list.contains(3) && !have) {
            have = true;
            computerTurn(3);
        } else if (computerList.contains(1) && computerList.contains(3) && !list.contains(2) && !have) {
            have = true;
            computerTurn(2);
        } else if (computerList.contains(1) && computerList.contains(4) && !list.contains(7) && !have) {
            have = true;
            computerTurn(7);
        } else if (computerList.contains(1) && computerList.contains(7) && !list.contains(4) && !have) {
            have = true;
            computerTurn(4);
        } else if (computerList.contains(1) && computerList.contains(5) && !list.contains(9) && !have) {
            have = true;
            computerTurn(9);
        } else if (computerList.contains(1) && computerList.contains(9) && !list.contains(5) && !have) {
            have = true;
            computerTurn(5);
        }

        // check for 2
        if (computerList.contains(2) && computerList.contains(1) && !list.contains(3) && !have) {
            have = true;
            computerTurn(3);
        } else if (computerList.contains(2) && computerList.contains(3) && !list.contains(1) && !have) {
            have = true;
            computerTurn(1);
        } else if (computerList.contains(2) && computerList.contains(5) && !list.contains(2) && !have) {
            have = true;
            computerTurn(8);
        } else if (computerList.contains(2) && computerList.contains(8) && !list.contains(5) && !have) {
            have = true;
            computerTurn(5);
        }

        // check for 3
        if (computerList.contains(3) && computerList.contains(2) && !list.contains(1) && !have) {
            have = true;
            computerTurn(1);
        } else if (computerList.contains(3) && computerList.contains(1) && !list.contains(2) && !have) {
            have = true;
            computerTurn(2);
        } else if (computerList.contains(3) && computerList.contains(6) && !list.contains(9) && !have) {
            have = true;
            computerTurn(9);
        } else if (computerList.contains(3) && computerList.contains(9) && !list.contains(6) && !have) {
            have = true;
            computerTurn(6);
        } else if (computerList.contains(3) && computerList.contains(5) && !list.contains(7) && !have) {
            have = true;
            computerTurn(7);
        } else if (computerList.contains(3) && computerList.contains(7) && !list.contains(5) && !have) {
            have = true;
            computerTurn(5);
        }

        // check for 4
        if (computerList.contains(4) && computerList.contains(1) && !list.contains(7) && !have) {
            have = true;
            computerTurn(7);
        } else if (computerList.contains(4) && computerList.contains(7) && !list.contains(1) && !have) {
            have = true;
            computerTurn(1);
        } else if (computerList.contains(4) && computerList.contains(5) && !list.contains(6) && !have) {
            have = true;
            computerTurn(6);
        } else if (computerList.contains(4) && computerList.contains(6) && !list.contains(5) && !have) {
            have = true;
            computerTurn(5);
        }

        // check for 5
        if (computerList.contains(5) && computerList.contains(9) && !list.contains(1) && !have) {
            have = true;
            computerTurn(1);
        } else if (computerList.contains(5) && computerList.contains(1) && !list.contains(9) && !have) {
            have = true;
            computerTurn(9);
        } else if (computerList.contains(5) && computerList.contains(6) && !list.contains(4) && !have) {
            have = true;
            computerTurn(4);
        } else if (computerList.contains(5) && computerList.contains(4) && !list.contains(6) && !have) {
            have = true;
            computerTurn(6);
        } else if (computerList.contains(5) && computerList.contains(2) && !list.contains(8) && !have) {
            have = true;
            computerTurn(8);
        } else if (computerList.contains(5) && computerList.contains(8) && !list.contains(2) && !have) {
            have = true;
            computerTurn(2);
        }

        // check for 6
        if (computerList.contains(6) && computerList.contains(3) && !list.contains(9) && !have) {
            have = true;
            computerTurn(9);
        } else if (computerList.contains(6) && computerList.contains(9) && !list.contains(3) && !have) {
            have = true;
            computerTurn(3);
        } else if (computerList.contains(6) && computerList.contains(5) && !list.contains(4) && !have) {
            have = true;
            computerTurn(4);
        } else if (computerList.contains(6) && computerList.contains(4) && !list.contains(5) && !have) {
            have = true;
            computerTurn(5);
        }

        // check for 7
        if (computerList.contains(7) && computerList.contains(4) && !list.contains(1) && !have) {
            have = true;
            computerTurn(1);
        } else if (computerList.contains(7) && computerList.contains(1) && !list.contains(4) && !have) {
            have = true;
            computerTurn(4);
        } else if (computerList.contains(7) && computerList.contains(5) && !list.contains(3) && !have) {
            have = true;
            computerTurn(3);
        } else if (computerList.contains(7) && computerList.contains(3) && !list.contains(5) && !have) {
            have = true;
            computerTurn(5);
        } else if (computerList.contains(7) && computerList.contains(9) && !list.contains(8) && !have) {
            have = true;
            computerTurn(8);
        } else if (computerList.contains(7) && computerList.contains(8) && !list.contains(9) && !have) {
            have = true;
            computerTurn(9);
        }

        // check for 8
        if (computerList.contains(8) && computerList.contains(7) && !list.contains(9) && !have) {
            have = true;
            computerTurn(9);
        } else if (computerList.contains(8) && computerList.contains(9) && !list.contains(7) && !have) {
            have = true;
            computerTurn(7);
        } else if (computerList.contains(8) && computerList.contains(5) && !list.contains(2) && !have) {
            have = true;
            computerTurn(2);
        } else if (computerList.contains(8) && computerList.contains(2) && !list.contains(5) && !have) {
            have = true;
            computerTurn(5);
        }

        // check for 9
        if (computerList.contains(9) && computerList.contains(5) && !list.contains(1) && !have) {
            have = true;
            computerTurn(1);
        } else if (computerList.contains(9) && computerList.contains(1) && !list.contains(5) && !have) {
            have = true;
            computerTurn(5);
        } else if (computerList.contains(9) && computerList.contains(7) && !list.contains(8) && !have) {
            have = true;
            computerTurn(8);
        } else if (computerList.contains(9) && computerList.contains(8) && !list.contains(7) && !have) {
            have = true;
            computerTurn(7);
        } else if (computerList.contains(9) && computerList.contains(6) && !list.contains(3) && !have) {
            have = true;
            computerTurn(3);
        } else if (computerList.contains(9) && computerList.contains(3) && !list.contains(6) && !have) {
            have = true;
            computerTurn(6);
        }

        if (!have && b) {
            b = true;
        } else if (!have && !b) {
            addListComputer();
            b = false;
        }
        return b;
    }

    private boolean gameIsOver(List<Integer> listView) {
        boolean returnValue = false;
        Collections.sort(listView);
        tempList = new ArrayList<>();
        tempList.clear();

        if (listView.size() == 3) {
            returnValue = check(listView);

        } else if (listView.size() > 3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tempList = Arrays.asList(Generator.combination(listView).simple(3).stream().toArray());

                for (int i = 0; i < tempList.size(); i++) {
                    returnValue = objectToIntegerList(tempList.get(i));
                    if (returnValue) {
                        break;
                    }
                }
            }
        } else {
            returnValue = false;
        }

        return returnValue;
    }

    private boolean check(List<Integer> listView) {
        boolean returnValue = false;

        Collections.sort(listView);

        if ((listView.get(0) == 1 || listView.get(0) == 4 || listView.get(0) == 7) &&
                (listView.get(0) + 1 == listView.get(1) && listView.get(1) + 1 == listView.get(2))) {

            returnValue = true;

        } else if (listView.get(0) == 1) {
            if ((listView.get(0) + 3 == listView.get(1) && listView.get(1) + 3 == listView.get(2)) ||
                    (listView.get(0) + 4 == listView.get(1) && listView.get(1) + 4 == listView.get(2))) {

                returnValue = true;
            }

        } else if (listView.get(0) == 2 && (listView.get(0) + 3 == listView.get(1) && listView.get(1) + 3 == listView.get(2))) {
            returnValue = true;

        } else if (listView.get(0) == 3) {
            if ((listView.get(0) + 3 == listView.get(1) && listView.get(1) + 3 == listView.get(2)) ||
                    (listView.get(0) + 2 == listView.get(1) && listView.get(1) + 2 == listView.get(2))) {

                returnValue = true;
            }
        }

        return returnValue;
    }

}




