package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    private Photo photo;
    private TextView outcome;
    private Bitmap bitmap;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            byte[] byteArray = getIntent().getByteArrayExtra("IMAGE");
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            size = extras.getInt("SIZE");
        }
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img2);

        photo = (Photo) findViewById(R.id.view);
        outcome = (TextView) findViewById(R. id.textView2);

        //https://coderwall.com/p/immp8q/get-height-of-a-view-in-oncreate-method-android
        final ViewTreeObserver observer= photo.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Bitmap b = Bitmap.createScaledBitmap(bitmap, photo.getWidth()-50, photo.getHeight()-50,true);
                photo.initialize(b,size);
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
                        if(photo.check()){
                            Log.e("Game","You win");
                            outcome.setText("You Win!");
                        }
                    }
                }
                return true;
            }
        });



    }
}