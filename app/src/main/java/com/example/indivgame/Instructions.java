package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Instructions extends AppCompatActivity {

    private TextView textView;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        //Creates a pop up window
        //https://www.youtube.com/watch?v=fn5OlqQuOCk
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int width = display.widthPixels;
        int height = display.heightPixels;
        getWindow().setLayout((int)(width*.7),(int)(height*.5));

        textView = (TextView) findViewById(R.id.textView7);
        String text = "Click the boxes to move them around in order to fix the image back to its original position.";
        textView.setText(text);

        home = (Button) findViewById(R.id.button4);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //CLose activity to resume previous activity
            }
        });
    }
}