package com.example.indivgame;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class GamePlay extends View {

    private Bitmap original;
    private ArrayList<ArrayList<Integer>> coord;

    private final Img[] current = new Img[9];
    private final Random random = new Random();
    private TimeAnimator timeAnimator;

    private int blank;
    private int size;
    private int width;
    private int height;
    private int chosen;
    private int last;
    private float changeX;
    private float changeY;
    private boolean moving;

    private static class Img{
        private float x;
        private float y;
        private Bitmap bitmap;
    }

    public GamePlay(Context context) {
        super(context);
    }

    public GamePlay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GamePlay(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh){
        super.onSizeChanged(width,height,oldw,oldh);

        initialize(width,height);

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
                shuffle(deltaTime);
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

    public void initialize(int widthIn, int heightIn){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img1);
        Bitmap b = Bitmap.createScaledBitmap(bitmap,widthIn-50,heightIn-50,true);
        original = b;
        size = 3;
        last = -1;
        moving = false;
        coord = new ArrayList<>();
        width = (original.getWidth()/size);
        height = (original.getHeight()/size);
        int x = 0;
        int y = 0;
        int spaceX = 0;
        int spaceY = 0;
        for(int i=0;i<(size*size-1);i++){
            //Take part of the image
            //https://stackoverflow.com/questions/7234986/how-to-split-image-to-2-parts
            Bitmap temp = Bitmap.createBitmap(original,x,y,width,height);
            final Img img = new Img();
            img.bitmap = temp;
            img.x = x+spaceX;
            img.y = y+spaceY;
            current[i] = img;
            x += width;
            spaceX += 10;
            if(x > (original.getWidth()-width)){
                y += height;
                x = 0;
                spaceX = 0;
                spaceY += 10;
                ArrayList<Integer> coordinates = new ArrayList<>();
                coordinates.add(x+spaceX);
                coordinates.add(y+spaceY);
                coord.add(coordinates);
            }
            //Add location of blank image
            if(i == (size*size-2)){
                blank = i+1;
                final Img blnk = new Img();
                blnk.bitmap = null;
                blnk.x = x+spaceX;
                blnk.y = y+spaceY;
                current[i+1] = blnk;
                ArrayList<Integer> blankLoc = new ArrayList<>();
                blankLoc.add(x+spaceX);
                blankLoc.add(y+spaceY);
                coord.add(blankLoc);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        for(final Img img : current){
            if(img != null && img.bitmap != null) {
                canvas.drawBitmap(img.bitmap, img.x, img.y, null);
            }
        }
    }


    private void shuffle(float deltaMs){
        if (!moving) {
            ArrayList<Integer> options = allMovable(last);
            int choice = random.nextInt(options.size());
            last = blank;
            chosen = options.get(choice);
            changeX = current[chosen].x;
            changeY = current[chosen].y;
            moving = true;
        }
        update(chosen, deltaMs);

    }

    private ArrayList<Integer> allMovable(int last){

        ArrayList<Integer> ret = new ArrayList<>();
        for(int i=0; i<current.length; i++){
            int num = movable(i);
            if(num>=0 && i != last){
                ret.add(i);
            }
        }
        return ret;
    }

    public int movable(int num){
        if(blank/size == num/size && (abs(blank%size - num%size) == 1)){ //same row (one off)
            return 0;
        }
        if(blank%size == num%size && (abs(blank/size - num/size) == 1)){ //same column (one off)
            return 1;
        }
        return -1;
    }

    public boolean update(int num, float deltaMs){
        final float deltaSeconds = deltaMs / 1000f;
        int move = movable(num);
        if(move<0){
            return false;
        } else if(move == 0){
            if(blank < num){
                current[num].x -= 100 * deltaSeconds;
                if(current[num].x < current[blank].x){
                    current[num].x  = changeX;
                    current[blank].bitmap = current[num].bitmap;
                    current[num].bitmap = null;
                    blank +=1;
                    moving = false;
                }
            } else if(blank > num){
                current[num].x += 100 * deltaSeconds;
                if(current[num].x > current[blank].x){
                    current[num].x  = changeX;
                    current[blank].bitmap = current[num].bitmap;
                    current[num].bitmap = null;
                    blank -=1;
                    moving = false;
                }
            }
        } else if(move == 1){
            if(blank < num) {
                current[num].y -= 150 * deltaSeconds;
                if(current[num].y < current[blank].y){
                    current[num].y  = changeY;
                    current[blank].bitmap = current[num].bitmap;
                    current[num].bitmap = null;
                    blank +=size;
                    moving = false;
                }
            }else if(blank > num){
                current[num].y += 150 * deltaSeconds;
                if(current[num].y > current[blank].y){
                    current[num].y  = changeY;
                    current[blank].bitmap = current[num].bitmap;
                    current[num].bitmap = null;
                    blank -= size;
                    moving = false;
                }
            }
        }
        invalidate();
        return true;
    }

}
