package com.wadson.projetandroidfinal;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    TextView shape[][] = new TextView[3][3], txtPlayer1, txtPlayer2;
    ViewGroup groupRow1,groupRow2,groupRow3;

    Player player1 , player2;
    Player playerTurn;

    boolean isNull = false;

    String CELLS[][] = new String[3][3];


    View.OnClickListener shapeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        setUpListener();

        groupRow1 = findViewById(R.id.group_row_1);
        groupRow2 = findViewById(R.id.group_row_2);
        groupRow3 = findViewById(R.id.group_row_3);

        txtPlayer1 =  ((TextView) findViewById(R.id.txt_x_score));
        txtPlayer2 =  ((TextView) findViewById(R.id.txt_o_score));

        for(int i = 0; i < groupRow1.getChildCount(); i++){
            shape[0][i] = (TextView) groupRow1.getChildAt(i);
            shape[0][i].setOnClickListener(shapeListener);
        }
        for(int i = 0; i < groupRow2.getChildCount(); i++){
            shape[1][i] = (TextView) groupRow2.getChildAt(i);
            shape[1][i].setOnClickListener(shapeListener);
        }
        for(int i = 0; i < groupRow3.getChildCount(); i++){
            shape[2][i] = (TextView) groupRow3.getChildAt(i);
            shape[2][i].setOnClickListener(shapeListener);
        }
        player1 = new Player();
        player2 = new Player();

        player1.score = 0;
        player1.name = "X";
        player1.text = "X";

        player2.score = 0;
        player2.name = "O";
        player2.text = "O";

        txtPlayer1.setText(getString(R.string.txt_score,player1.name,player1.score));
        txtPlayer2.setText(getString(R.string.txt_score,player2.name,player2.score));

        playerTurn = player1;
    }

    private void setUpListener() {
        shapeListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView textView = (TextView) view;
                textView.setClickable(false);

                textView.setText(playerTurn.text);
                checkGame();

                if(playerTurn == player1){
                    playerTurn = player2;
                }else{
                    playerTurn = player1;
                }
            }
        };
    }

    private void checkGame() {

        int count = 0;
        //row
        for(int i = 0; i < 3; i++){
            count = 0;
            for (int col = 0; col < shape.length; col++) {
                    if (playerTurn.text.equals(shape[i][col].getText())){
                        count++;
                        if(count >= 3){
                            winGame();
                            return;
                        }
                    }else{
                        count = 0;
                    }
                }
            }
        count = 0;

        //col
        for(int i = 0; i < 3; i++){
            count = 0;
            for (int col = 0; col < shape[i].length; col++) {
                if (playerTurn.text.equals(shape[col][i].getText())) {
                    count++;
                    if (count >= 3) {
                        winGame();
                        return;
                    }
                } else {
                    count = 0;
                }
            }
        }
        count = 0;
        //diag primary
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if(row - col == 0) {
                    if (playerTurn.text.equals(shape[row][col].getText())) {
                        Log.d(TAG, "row ["+ row +"]["+col+"] true ");
                        count++;
                        if (count >= 3) {
                            winGame();
                            return;
                        }
                    } else {
                        Log.d(TAG, "row ["+ row +"]["+col+"] false ");
                        count = 0;
                    }
                }
            }
        }
        count = 0;
        //diag secondary
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if(row + col == 3 - 1) {
                    if (playerTurn.text.equals(shape[row][col].getText())) {
                        count++;
                        if (count >= 3) {
                            winGame();
                            return;
                        }
                    } else {
                        count = 0;
                    }
                }
            }
        }
        for(TextView tvs[]: shape){
            for(TextView tv: tvs){
                if(tv.isClickable()){
                    return;
                }
            }
        }
        isNull = true;
        newGame();
    }

    @Override
    public void onClick(View view) {


    }
    public void winGame(){
        isNull = false;
        playerTurn.score++;
        if(playerTurn == player1){
            txtPlayer1.setText(getString(R.string.txt_score,player1.name,player1.score));
        }else{
            txtPlayer2.setText(getString(R.string.txt_score,player2.name,player2.score));
        }
        newGame();
    }
    public void newGame(){
        for(TextView tvs[]: shape){
            for(TextView tv: tvs){
                tv.setClickable(true);
                    tv.setText(null);
                }
            }
        Dialog dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.layout_dialog,null,false);
        dialog.setContentView(view);
        ((Button)view.findViewById(R.id.btn_yes)).setOnClickListener((v)->{
            dialog.dismiss();
        });
        ((Button)view.findViewById(R.id.btn_no)).setOnClickListener((v)->{
            dialog.dismiss();
            finish();
        });

        if(isNull){
            ((TextView)view.findViewById(R.id.text)).setText("Oups! aucun gagnant ");
        }
        else{
            ((TextView)view.findViewById(R.id.text)).setText(""+playerTurn.name +" a gagne! ");
        }
        dialog.show();


    }



}