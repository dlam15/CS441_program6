package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class ImgSelect extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Recycle adapter;

    private ArrayList<Bitmap> images;
    private ArrayList<String> description;
    private int puzzleSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_select);

        images = new ArrayList<>();
        description = new ArrayList<>();
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.img1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.img2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.img3);
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

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        adapter = new Recycle(this, images,description, 2, puzzleSize);
        refresh();

        //https://developer.android.com/guide/topics/ui/controls/spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                puzzleSize = position+3;
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.size, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void refresh(){
        adapter = new Recycle(this, images,description, 2, puzzleSize);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void remove(int index){
        images.remove(index);
        description.remove(index);
        refresh();
    }
}