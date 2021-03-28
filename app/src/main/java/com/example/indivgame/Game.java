package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    private Order order;
    private TextView outcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img1);

        order = (Order) findViewById(R.id.view);
        outcome = (TextView) findViewById(R. id.textView2);

        //https://coderwall.com/p/immp8q/get-height-of-a-view-in-oncreate-method-android
        final ViewTreeObserver observer= order.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Bitmap b = Bitmap.createScaledBitmap(bitmap,order.getWidth()-50,order.getHeight()-50,true);
                order.initialize(b,3);
                //https://stackoverflow.com/questions/18285540/stop-listening-for-more-listener-events
                order.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        order.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    boolean changed = order.clicked((int)event.getX(),(int)event.getY());
                    if(changed){
                        if(order.check()){
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