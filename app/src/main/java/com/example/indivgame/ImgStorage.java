package com.example.indivgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ImgStorage {
    private static ImgStorage instance;
    private ArrayList<Bitmap> images;
    private ArrayList<String> description;
    private Context context;
    private String highscores = "highscores";
    private String photos = "photos";

    //Code based on https://developer.android.com/training/data-storage/app-specific

    //Singleton class
    private ImgStorage(Context contextIn){
        context = contextIn;
        images = new ArrayList<>();
        description = new ArrayList<>();
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(),R.drawable.img1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(),R.drawable.img2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(),R.drawable.img3);
        images.add(bitmap1);
        images.add(bitmap2);
        images.add(bitmap3);

        for(int i=0;i<images.size();i++){
            Bitmap bitmap = Bitmap.createScaledBitmap(images.get(i), 500, 500,true);
            images.set(i,bitmap);
        }

        description.add("Temp");
        description.add("Temp");
        description.add("Professor");
    }

    public static ImgStorage getInstance(Context context){
        if(instance == null){
            instance = new ImgStorage(context);
        }
        return instance;
    }

    public ArrayList<Bitmap> getImages(){
        return images;
    }

    public ArrayList<String> getDescription(){
        return description;
    }

}
