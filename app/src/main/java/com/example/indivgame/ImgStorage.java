package com.example.indivgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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
    private String photos = "photos";
    private int defaults;

    //Code based on https://developer.android.com/training/data-storage/app-specific

    //Singleton class
    private ImgStorage(Context contextIn){
        context = contextIn;
        images = new ArrayList<>();
        description = new ArrayList<>();
        defaults = 10;
        for(int i=0; i<defaults;i++){
            int identify = context.getResources().getIdentifier("img"+ (i+1), "drawable",context.getPackageName());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),identify);
            images.add(bitmap);
        }
        description.add("Professor");
        description.add("Lake");
        description.add("Waterfall");
        description.add("Mountain");
        description.add("Lake-Night");
        description.add("Lion");
        description.add("Snake");
        description.add("Panda");
        description.add("Owl");
        description.add("Butterfly");
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

    public int getDefaults(){
        return defaults;
    }

    public void addImage(Bitmap bitmap, String descript){
        images.add(bitmap);
        description.add(descript);
    }

    public void removeImage(int index){
        images.remove(index);
        description.remove(index);
    }

}
