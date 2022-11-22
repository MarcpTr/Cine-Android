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
import com.example.cine.pojo.Movie;
import com.example.cine.utils.DownLoadImageTask;

import java.util.ArrayList;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.ViewHolderMovieCard> {
        ArrayList<Movie> movies;
        public MovieCardAdapter(ArrayList<Movie> movies){
                this.movies=movies;
        }
        @NonNull
        @Override
        public ViewHolderMovieCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie,parent,false);
                return new ViewHolderMovieCard(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolderMovieCard holder, int position) {
                holder.filmTitle.setText(movies.get(position).getTitle());
                holder.filmTitle.setTextColor(Color.WHITE);
               if (movies.get(position).getPosterPath()==null || movies.get(position).getPosterPath().equals("")){
                        holder.posterFilmImage.setImageResource(R.drawable.film);
                }else{
                        new DownLoadImageTask(holder.posterFilmImage).execute("https://image.tmdb.org/t/p/w154"+movies.get(position).getPosterPath());
                }
               holder.movieCard.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                               Intent intent= new Intent(view.getContext(), InfoActivity.class);
                               intent.putExtra("id", movies.get(position).getId());
                               view.getContext().startActivity(intent);
                       }
               });
        }
        @Override
        public int getItemCount() {
                return movies.size();
        }
        public class ViewHolderMovieCard extends RecyclerView.ViewHolder{
                ImageView posterFilmImage;
                TextView filmTitle;
                CardView movieCard;
                public ViewHolderMovieCard(@NonNull View itemView) {
                        super(itemView);
                        movieCard=itemView.findViewById(R.id.movieCard);
                        posterFilmImage=itemView.findViewById(R.id.filmPoster);
                        filmTitle=itemView.findViewById(R.id.filmTitle);
                        filmTitle.setMovementMethod(new ScrollingMovementMethod());
                }
        }
}
