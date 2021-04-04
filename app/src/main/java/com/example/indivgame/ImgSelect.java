package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

public class ImgSelect extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Recycle adapter;
    private ImgStorage imgStorage;
    private ArrayList<Bitmap> images;
    private ArrayList<String> description;
    private int puzzleSize;
    private ImageButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_select);

        imgStorage = imgStorage.getInstance(this);
        images = imgStorage.getImages();
        description = imgStorage.getDescription();

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

        back = (ImageButton) findViewById(R.id.imageButton2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImgSelect.this, MainActivity.class);
                startActivity(intent);
            }
        });
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