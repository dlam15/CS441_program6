package com.example.indivgame;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class Complete extends View {

    private final Letter[] current = new Letter[9];
    private TimeAnimator timeAnimator;

    private final String complete = "COMPLETE!";
    private int chosen;
    private boolean moving;
    private Paint paint;

    private static class Letter{
        private float x;
        private float y;
        private String letter;
    }

    public Complete(Context context) {
        super(context);
    }

    public Complete(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Complete(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh){
        super.onSizeChanged(width,height,oldw,oldh);
        for(int i=0; i<current.length;i++){
            final Letter letter= new Letter();
            initialize(letter, i);
            current[i] = letter;
        }

    }

    @Override
    protected void onAttachedToWindow(){
        super.onAttachedToWindow();
        timeAnimator = new TimeAnimator();
        timeAnimator.setTimeListener(new TimeAnimator.TimeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT) // Needed for isLaidout
            @Override
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                if(!isLaidOut()){ //Needed so that it ignores when screen is not set up yet
                    return;
                }
                update();
                invalidate();
            }
        });
        timeAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        timeAnimator.cancel();
        timeAnimator.setTimeListener(null);
        timeAnimator.removeAllListeners();
        timeAnimator = null;
    }

    public void initialize(Letter letter, int i){
        letter.letter = complete.substring(i,i+1);
        letter.x = i*80+20;
        letter.y = 150;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        chosen = 0;
        moving = false;

    }

    @Override
    protected void onDraw(Canvas canvas){
        for(final Letter letter : current) {
            canvas.drawText(letter.letter, letter.x, letter.y, paint);

        }
    }


    private void update(){
        if (!moving) {
            current[chosen].y -= 3;
            if(current[chosen].y < 120){
                moving = true;
            }
        }else{
            current[chosen].y += 3;
            if(chosen == current.length-1){
                current[0].y -= 3;
            }else {
                current[chosen+1].y -= 3;
            }
            if(current[chosen].y == 150){
                current[chosen].y = 150;
                if(chosen == current.length-1){
                    chosen = 0;
                }else {
                    chosen += 1;
                }
            }
        }

    }

}
