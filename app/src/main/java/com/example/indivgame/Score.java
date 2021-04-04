package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Score extends AppCompatActivity {

    private TextView textView;
    private int index;
    private Scanner scanner;
    private ArrayList<Integer> highscores;
    private int time;
    private int moves;
    private Button scores;
    private Button home;
    private Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //Creates a pop up window
        //https://www.youtube.com/watch?v=fn5OlqQuOCk
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int width = display.widthPixels;
        int height = display.heightPixels;
        getWindow().setLayout((int)(width*.75),(int)(height*.3));

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            index = extras.getInt("INDEX");
            time = extras.getInt("TIME");
            moves = extras.getInt("MOVES");
        }

        scanner = scanner.getInstance(this);
        highscores = scanner.getScores();

        textView = (TextView) findViewById(R.id.textView11);
        String text = "                     Times      Moves\nHigh Score: ";
        if(highscores.get(index) <10) {
            text += highscores.get(index) + "sec       " + highscores.get(index+1) + "\n";
        }else if(highscores.get(index) <100) {
            text += highscores.get(index) + "sec    " + highscores.get(index+1) + "\n";
        }else if(highscores.get(index) <1000) {
            text += highscores.get(index) + "sec  " + highscores.get(index+1) + "\n";
        }else if(highscores.get(index) <10000){
            text += highscores.get(index) + "sec " + highscores.get(index+1) + "\n";
        }else{
            text += ">9999sec" + highscores.get(index+1) + "\n";
        }
        text += "Your Score: ";
        if(time <10) {
            text += time + "sec       " + moves + "\n";
        }else if(time <100) {
            text += time + "sec    " + moves + "\n";
        }else if(time <1000) {
            text += time + "sec  " + moves + "\n";
        }else if(time <10000){
            text += time + "sec " + moves + "\n";
        }else{
            text += ">9999sec" + moves + "\n";
        }

        textView.setText(text);

        scores = (Button) findViewById(R.id.button12);
        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Score.this, HighScore.class);
                startActivity(intent);
                finish();
            }
        });
        home = (Button) findViewById(R.id.button13);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Score.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        play = (Button) findViewById(R.id.button14);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Score.this, ImgSelect.class);
                startActivity(intent);
                finish();
            }
        });
    }
}