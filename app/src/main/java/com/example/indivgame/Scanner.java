package com.example.indivgame;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Scanner {
    private static Scanner instance;
    private ArrayList<Integer> scores;
    private Context context;
    private String highscores = "highscores";
    private String photos = "photos";

    //Code based on https://developer.android.com/training/data-storage/app-specific

    //Singleton class
    private Scanner(Context contextIn){
        context = contextIn;
        scores = new ArrayList<>();

        try{
            FileInputStream fis = context.openFileInput(highscores);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                if(line.equals("null")){
                    scores.add(null);
                }else {
                    scores.add(Integer.valueOf(line));
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e){
            File file = new File(context.getFilesDir(), highscores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scanner getInstance(Context context){
        if(instance == null){
            instance = new Scanner(context);
        }
        return instance;
    }

    public ArrayList<Integer> getScores(){
        while(10 != scores.size()){
            scores.add(null);
            scores.add(null);
        }
        return scores;
    }

    public void updateScores(int index, Integer s, int type){
        scores.set(index+type, s);
        write();
    }

    public void write(){
        try{
            FileOutputStream fos = context.openFileOutput(highscores, Context.MODE_PRIVATE);
            for(int i=0; i<scores.size(); i++){
                if(scores.get(i) == null){
                    fos.write(("null\n").getBytes());
                }else {
                    fos.write((scores.get(i) + "\n").getBytes());
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
