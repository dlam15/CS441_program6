package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Confirm extends AppCompatActivity {

    private int type;
    private String text;
    private TextView textView;
    private Button yes;
    private Button no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        //Creates a pop up window
        //https://www.youtube.com/watch?v=fn5OlqQuOCk
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int width = display.widthPixels;
        int height = display.heightPixels;
        getWindow().setLayout((int)(width*.7),(int)(height*.2));

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            type = extras.getInt("TYPE");
        }
        text = "";
        if(type == 0){
            text = "Are you sure you want to reset your scores?";
        }else if(type ==1){
            text = "Are you sure you want to quit?";
        }
        textView = (TextView) findViewById(R.id.textView9);
        textView.setText(text);

        yes = (Button) findViewById(R.id.button10);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent();
                setResult(0,intent);
                finish();
            }
        });

        no = (Button) findViewById(R.id.button11);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent();
                setResult(1,intent);
                finish();
            }
        });
    }
}