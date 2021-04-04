package com.example.indivgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recycle extends RecyclerView.Adapter<Recycle.ViewHolder> {

    private ArrayList<Bitmap> images;
    private ArrayList<String> description;
    private int defaultNum;
    private Context context;
    private int puzzleSize;
    private ImgStorage imgStorage;
    private ImgSelect imgSelect;

    //RecycleView Adapter based on
    //https://www.youtube.com/watch?v=18VcnYN5_LM
    //https://www.youtube.com/watch?v=Vyqz_-sJGFk

    public Recycle(Context contextIn, ImgSelect img, int size) {
        context = contextIn;
        imgSelect = img;
        puzzleSize = size;
        imgStorage = imgStorage.getInstance(context);
        images = imgStorage.getImages();
        description = imgStorage.getDescription();
        defaultNum = imgStorage.getDefaults();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.img_selector, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position <defaultNum) {
            holder.delete.setVisibility(View.INVISIBLE);
        }else{
            holder.delete.setVisibility(View.VISIBLE);
        }
        Bitmap bitmap = Bitmap.createScaledBitmap(images.get(position), 100, 100,true);
        holder.img.setImageBitmap(bitmap);
        holder.text.setText(description.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Game.class);
                intent.putExtra("IMAGE", position);
                intent.putExtra("SIZE", puzzleSize);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSelect.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView text;
        ImageButton delete;
        Button play;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);
            text = itemView.findViewById(R.id.textView3);
            delete = itemView.findViewById(R.id.imageButton);
            play = itemView.findViewById(R.id.button3);
        }
    }
}
