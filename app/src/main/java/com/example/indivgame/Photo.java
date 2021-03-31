package com.example.indivgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.abs;

public class Photo extends View {

    private Bitmap original;
    private ArrayList<Bitmap> correct;
    private ArrayList<Bitmap> current;
    private ArrayList<ArrayList<Integer>> coord;

    private int blank;
    private int size;
    private int width;
    private int height;

    public Photo(Context context) {
        super(context);
    }

    public Photo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Photo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initialize(Bitmap img, int num){
        original = img;
        size = num;
        correct = new ArrayList<>();
        current = new ArrayList<>();
        coord = new ArrayList<>();
        width = (original.getWidth()/size);
        height = (original.getHeight()/size);
        int x = 0;
        int y = 0;
        int spaceX = 0;
        int spaceY = 0;
        for(int i=0;i<(size*size-1);i++){
            Bitmap temp = Bitmap.createBitmap(original,x,y,width,height);
            correct.add(temp);
            current.add(temp);
            ArrayList<Integer> coordinates = new ArrayList<>();
            coordinates.add(x+spaceX);
            coordinates.add(y+spaceY);
            coord.add(coordinates);
            x += width;
            spaceX += 10;
            if(x > (original.getWidth()-width)){
                y += height;
                x = 0;
                spaceX = 0;
                spaceY += 10;
            }
            if(i == (size*size-2)){
                blank = i+1;
                ArrayList<Integer> blnk = new ArrayList<>();
                blnk.add(x+spaceX);
                blnk.add(y+spaceY);
                coord.add(blnk);
            }
        }
        correct.add(null);
        current.add(null);
        while(check()){
            shuffle();
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        for(int i=0; i<current.size(); i++){
            if(i != blank) {
                canvas.drawBitmap(current.get(i), coord.get(i).get(0), coord.get(i).get(1), null);
            }
        }
    }

    public boolean check(){
        for(int i=0; i<correct.size()-1; i++){
            if(current.get(i) == null) {
                return false;
            }
            if (!(correct.get(i).sameAs(current.get(i)))) {
                return false;
            }

        }
        return true;
    }

    public boolean clicked(int x, int y){
        for(int i=0; i<current.size(); i++){
            if(i != blank) {
                if (x > coord.get(i).get(0) && x < coord.get(i).get(0) + width &&
                        y > coord.get(i).get(1) && y < coord.get(i).get(1) + height) {
                    return update(i);
                }
            }
        }
        return false;
    }

    private void shuffle(){
        Random random = new Random();
        int moves = random.nextInt(size*15-size*10)+size*10;
        int last = -1;
        for(int i=0; i<moves; i++){
            ArrayList<Integer> options = allMovable(last);
            int choice = random.nextInt(options.size());
            last = options.get(choice);
            update(last);
        }
    }

    private ArrayList<Integer> allMovable(int last){

        ArrayList<Integer> ret = new ArrayList<>();
        for(int i=0; i<current.size(); i++){
            int num = movable(i);
            if(num>=0 && i != last){
                ret.add(i);
            }
        }
        return ret;
    }

    public int movable(int num){
        /*if(blank/size == num/size && (abs(blank%size - num%size) == 1)){ //same row (one off)
            return 0;
        }
        if(blank%size == num%size && (abs(blank/size - num/size) == 1)){ //same column (one off)
            return 1;
        }*/
        if(blank/size == num/size){ //same row
            return 0;
        }
        if(blank%size == num%size){ //same column
            return 1;
        }
        //Log.e("Order","immovable");
        return -1;
    }

    public boolean update(int num){
        int move = movable(num);
        if(move<0){
            return false;
        /*} else if(move == 0 || move == 1){
            current.set(blank,current.get(num));
            current.set(num, null);
            blank = num;*/
        } else if(move == 0){
            if(blank < num){
                int count = num-blank;
                for(int i=0; i<count; i++){
                    current.set(blank,current.get(blank+1));
                    current.set(blank+1, null);
                    blank +=1;
                }
            } else if(blank > num){
                int count = blank-num;
                for(int i=0; i<count; i++){
                    current.set(blank,current.get(blank-1));
                    current.set(blank-1, null);
                    blank -=1;
                }
            }
        } else if(move == 1){
            if(blank < num) {
                int count = num/size - blank/size;
                for (int i = 0; i<count; i++) {
                    current.set(blank, current.get(blank + size));
                    current.set(blank + size, null);
                    blank += size;
                }
            }else if(blank > num){
                int count = blank/size - num/size;
                for(int i=0; i<count; i++){
                    current.set(blank,current.get(blank-size));
                    current.set(blank-size, null);
                    blank -=size;
                }
            }
        }
        invalidate();
        return true;
    }

}
