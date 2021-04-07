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
    private int puzzleSize;
    private ImageButton back;
    private Button create;
    private int rem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_select);

        imgStorage = imgStorage.getInstance(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
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

        create = (Button) findViewById(R.id.button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImgSelect.this, AddImg.class);
                startActivity(intent);
                refresh();
            }
        });
    }

    public void refresh(){
        adapter = new Recycle(this, ImgSelect.this, puzzleSize);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void remove(int index){
        rem = index;
        Intent intent = new Intent(ImgSelect.this, Confirm.class);
        intent.putExtra("TYPE",2);
        startActivityForResult(intent, 7);

    }

    //Get the results of an activity
    //https://www.javatpoint.com/android-startactivityforresult-example
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == 7 && resultCode == 0){
            imgStorage.removeImage(rem);
            refresh();
        }

    }
}