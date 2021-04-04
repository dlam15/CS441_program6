package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button game;
    private Button highscore;
    private Button instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = (Button) findViewById(R.id.startButton);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImgSelect.class);
                startActivity(intent);
            }
        });

        highscore = (Button) findViewById(R.id.button6);
        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HighScore.class);
                startActivity(intent);
            }
        });
        instruction = (Button) findViewById(R.id.button7);
        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Instructions.class);
                startActivity(intent);
            }
        });
    }
}