package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Name extends AppCompatActivity {

    private EditText text;
    private Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        //Creates a pop up window
        //https://www.youtube.com/watch?v=fn5OlqQuOCk
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int width = display.widthPixels;
        int height = display.heightPixels;
        getWindow().setLayout((int)(width*.7),(int)(height*.2));

        text = (EditText) findViewById(R.id.editText);

        accept = (Button) findViewById(R.id.button18);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent();
                intent.putExtra("NAME", text.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });



    }
}