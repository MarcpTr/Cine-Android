package com.example.cine.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cine.InfoActivity;
import com.example.cine.R;
import com.example.cine.pojo.Cast;
import com.example.cine.utils.DownLoadImageTask;

import java.util.ArrayList;
import java.util.List;

public class CastCardAdapter extends RecyclerView.Adapter<CastCardAdapter.ViewHolderCastCard> {
    List<Cast> cast;
    public CastCardAdapter(List<Cast> cast){
        this.cast=cast;
    }
    @NonNull
    @Override
    public CastCardAdapter.ViewHolderCastCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cast,parent,false);
        return new CastCardAdapter.ViewHolderCastCard(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CastCardAdapter.ViewHolderCastCard holder, int position) {
        holder.nameCast.setText(cast.get(position).getName());
        holder.rolCast.setText(cast.get(position).getCharacter());

        if (cast.get(position).getProfilePath()==null || cast.get(position).getProfilePath().equals("")){
          //  holder.castImg.setImageResource(R.drawable.film);
        }else{
            new DownLoadImageTask(holder.castImg).execute("https://image.tmdb.org/t/p/w154"+cast.get(position).getProfilePath());
        }
        holder.castCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent intent= new Intent(view.getContext(), CastActivity.class);
                intent.putExtra("id", cast.get(position).getId());
                view.getContext().startActivity(intent);*/
            }
        });
    }
    @Override
    public int getItemCount() {
        return cast.size();
    }
    public class ViewHolderCastCard extends RecyclerView.ViewHolder{
        ImageView castImg;
        TextView nameCast;
        TextView rolCast;
        CardView castCard;
        public ViewHolderCastCard(@NonNull View itemView) {
            super(itemView);
            castCard=itemView.findViewById(R.id.castCard);
            castImg=itemView.findViewById(R.id.castImg);
            rolCast=itemView.findViewById(R.id.rolCast);
            nameCast=itemView.findViewById(R.id.nameCast);
            nameCast.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}
