package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScore extends AppCompatActivity {

    private Scanner scanner;
    private ArrayList<Integer> highscores;
    private TextView text;
    private Button home;
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        scanner = scanner.getInstance(this);
        highscores = scanner.getScores();
        text = (TextView) findViewById(R.id.textView6);
        show();


        home = (Button) findViewById(R.id.button2);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HighScore.this, MainActivity.class);
                startActivity(intent);
            }
        });

        reset = (Button) findViewById(R.id.button9);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HighScore.this, Confirm.class);
                intent.putExtra("TYPE",0);
                startActivityForResult(intent, 0);
            }
        });

    }

    //Get the results of an activity
    //https://www.javatpoint.com/android-startactivityforresult-example
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode ==0  && resultCode ==0){
            for (int i = 0; i < highscores.size(); i += 2) {
                //Reset scores
                scanner.updateScores(i, null, 0);
                scanner.updateScores(i, null, 1);
            }
            show();
        }

    }

    public void show(){
        String write = "        Best        Best\n        times      moves\n";
        for(int i=0; i<highscores.size(); i+=2){
            int size = i/2+3;
            write += size + "x" + size + "  ";
            if(highscores.get(i) == null){
                write += "NA          NA\n";
            }else if(highscores.get(i) <10) {
                write += highscores.get(i) + "sec       " + highscores.get(i+1) + "\n";
            }else if(highscores.get(i) <100) {
                write += highscores.get(i) + "sec    " + highscores.get(i+1) + "\n";
            }else if(highscores.get(i) <1000) {
                write += highscores.get(i) + "sec  " + highscores.get(i+1) + "\n";
            }else if(highscores.get(i) <10000){
                write += highscores.get(i) + "sec " + highscores.get(i+1) + "\n";
            }else{
                write += ">9999sec" + highscores.get(i+1) + "\n";
            }
        }
        text.setText(write);
        text.invalidate();
    }
}