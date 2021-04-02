package com.example.indivgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Recycle extends RecyclerView.Adapter<Recycle.ViewHolder> {

    private ArrayList<Bitmap> images;
    private ArrayList<String> description;
    private int defaultNum;
    private Context context;
    private int puzzleSize;

    //RecycleView Adapter based on
    //https://www.youtube.com/watch?v=18VcnYN5_LM
    //https://www.youtube.com/watch?v=Vyqz_-sJGFk

    public Recycle(Context contextIn, ArrayList<Bitmap> img, ArrayList<String> des, int defaultIn, int size) {
        context = contextIn;
        images = img;
        description = des;
        defaultNum = defaultIn;
        puzzleSize = size;
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
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                images.get(position).compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("IMAGE", byteArray);
                intent.putExtra("SIZE", puzzleSize);
                context.startActivity(intent);
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
        Button delete;
        Button play;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);
            text = itemView.findViewById(R.id.textView3);
            delete = itemView.findViewById(R.id.button2);
            play = itemView.findViewById(R.id.button3);
        }
    }
}