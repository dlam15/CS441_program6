package com.example.indivgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AddImg extends AppCompatActivity {

    private Button draw;
    private Button gallery;
    private Button camera;
    private ImgStorage imgStorage;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_img);

        //Creates a pop up window
        //https://www.youtube.com/watch?v=fn5OlqQuOCk
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int width = display.widthPixels;
        int height = display.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.2));

        imgStorage = imgStorage.getInstance(this);

        gallery = (Button) findViewById(R.id.button16);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,3);
            }
        });

        camera = (Button) findViewById(R.id.button17);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,5);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 3){
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                Intent intent = new Intent(AddImg.this, Name.class);
                startActivityForResult(intent, 4);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(resultCode == RESULT_OK && requestCode == 4){
            Bundle extras = data.getExtras();
            String name = "null";
            if(extras != null){
                name = extras.getString("NAME");
            }
            imgStorage.addImage(bitmap,name);
            finish();
        }
        if (resultCode == RESULT_OK && requestCode == 5) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            Intent intent = new Intent(AddImg.this, Name.class);
            startActivityForResult(intent, 4);
        }
    }
}