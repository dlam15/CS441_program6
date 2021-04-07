package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    private Photo photo;
    private TextView moves;
    private Bitmap bitmap;
    private int size;
    private Chronometer chronometer;
    private int time;
    private Button hint;
    private int count;
    private Scanner scanner;
    private ArrayList<Integer> highscores;
    private int index;
    private Button instruction;
    private Button back;
    private ImgStorage imgStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imgStorage = imgStorage.getInstance(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            bitmap = imgStorage.getImages().get(extras.getInt("IMAGE"));
            size = extras.getInt("SIZE");
            index = 2*size -6; //Index for the high scores of this puzzle size
        }

        photo = (Photo) findViewById(R.id.view);
        chronometer = (Chronometer) findViewById(R.id.timer);
        chronometer.setFormat("Time: %s");
        moves = (TextView) findViewById(R.id.textView4);
        hint = (Button) findViewById(R.id.hintBtn);

        count = 0;
        scanner = scanner.getInstance(this);
        highscores = scanner.getScores();

        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo.toggleHint();
            }
        });

        instruction = (Button) findViewById(R.id.button8);
        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Game.this, Instructions.class);
                startActivity(intent);
            }
        });

        back = (Button) findViewById(R.id.button5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Game.this, Confirm.class);
                intent.putExtra("TYPE",1);
                startActivityForResult(intent, 1);
            }
        });

        //Listen to be able to get the size of the width and height
        //https://coderwall.com/p/immp8q/get-height-of-a-view-in-oncreate-method-android
        final ViewTreeObserver observer= photo.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Bitmap b = Bitmap.createScaledBitmap(bitmap, photo.getWidth()-50, photo.getHeight()-50,true);
                photo.initialize(b,size);
                //Chronometer
                //https://www.youtube.com/watch?v=RLnb4vVkftc
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                //Stop listening so that the game doesn't keep randomizing
                //https://stackoverflow.com/questions/18285540/stop-listening-for-more-listener-events
                photo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        photo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    boolean changed = photo.clicked((int)event.getX(),(int)event.getY());
                    if(changed){
                        count += 1;
                        moves.setText("Moves: " + count);
                        if(photo.check()){
                            //https://stackoverflow.com/questions/526524/android-get-time-of-chronometer-widget
                            time = (int)(SystemClock.elapsedRealtime() - chronometer.getBase())/1000; //Get the time in seconds
                            chronometer.stop();
                            if(highscores.get(index) == null || highscores.get(index) > time){
                                scanner.updateScores(index,time,0);//type 0 = time
                            }
                            if(highscores.get(index+1) == null || highscores.get(index+1) > count){
                                scanner.updateScores(index,count,1);//type 1 = moves
                            }
                            back.setEnabled(false);
                            hint.setEnabled(false);
                            instruction.setEnabled(false);
                            Intent intent = new Intent(Game.this, Score.class);
                            intent.putExtra("INDEX", index);
                            intent.putExtra("TIME",time);
                            intent.putExtra("MOVES",count);
                            startActivity(intent);
                        }
                    }
                }
                return true;
            }
        });

    }

    //Get the results of an activity
    //https://www.javatpoint.com/android-startactivityforresult-example
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode ==1 && resultCode ==0){
            finish(); //End the game but don't let them use the back button to get back to game
        }

    }
}