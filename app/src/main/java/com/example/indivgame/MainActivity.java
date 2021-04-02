package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private GamePlay gamePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img3);

        gamePlay = (GamePlay) findViewById(R.id.gamePlay);


        Button game = (Button) findViewById(R.id.startButton);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImgSelect.class);
                startActivity(intent);
            }
        });
    }
}