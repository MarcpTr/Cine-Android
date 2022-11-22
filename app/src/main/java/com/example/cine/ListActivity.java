package com.example.cine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cine.adapter.MovieCardAdapter;
import com.example.cine.model.ListsViewModel;
import com.example.cine.pojo.Movie;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {
    FirebaseUser user;
    boolean logged;

    ArrayList<Movie> likedMovies, watchedMovies, watchlistMovies;
    private RecyclerView recyclerLikedView, recyclerWatchedView, recyclerWatchlistView;
    private ListsViewModel listsViewModel;
    private MovieCardAdapter movieCardAdapter;
    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logged = isLogged();
        addBottonNavigation();
        if (logged) {
            likedMovies = new ArrayList<>();
            watchedMovies = new ArrayList<>();
            watchlistMovies = new ArrayList<>();
            recyclerLikedView = findViewById(R.id.LikedView);
            recyclerLikedView.setHasFixedSize(false);
            recyclerLikedView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            recyclerWatchedView = findViewById(R.id.recyclerWatchedView);
            recyclerWatchedView.setHasFixedSize(false);
            recyclerWatchedView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            recyclerWatchlistView = findViewById(R.id.recyclerWatchlistView);
            recyclerWatchlistView.setHasFixedSize(false);
            recyclerWatchlistView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            listsViewModel = new ViewModelProvider(this).get(ListsViewModel.class);
            listsViewModel.getListsMovies().observe(this, listsMovies -> {

                if ((ArrayList<HashMap>) listsMovies.get("liked") != null) {

                    for (HashMap movie : (ArrayList<HashMap>) listsMovies.get("liked")) {
                        Movie movieLiked = new Movie();
                        movieLiked.setId(Integer.parseInt((String) movie.get("id")));
                        movieLiked.setTitle((String) movie.get("title"));
                        movieLiked.setPosterPath((String) movie.get("posterPath"));
                        likedMovies.add(movieLiked);
                    }
                    movieCardAdapter = new MovieCardAdapter((ArrayList<Movie>) likedMovies);
                    recyclerLikedView.setAdapter(movieCardAdapter);
                }
                if ((ArrayList<HashMap>) listsMovies.get("watched") != null) {


                    for (HashMap movie : (ArrayList<HashMap>) listsMovies.get("watched")) {
                        Movie movieWatch = new Movie();
                        movieWatch.setId(Integer.parseInt((String) movie.get("id")));
                        movieWatch.setTitle((String) movie.get("title"));
                        movieWatch.setPosterPath((String) movie.get("posterPath"));
                        watchedMovies.add(movieWatch);
                    }
                    movieCardAdapter = new MovieCardAdapter((ArrayList<Movie>) watchedMovies);
                    recyclerWatchedView.setAdapter(movieCardAdapter);
                }
                if ((ArrayList<HashMap>) listsMovies.get("watchlist") != null) {
                    for (HashMap movie : (ArrayList<HashMap>) listsMovies.get("watchlist")) {
                        Movie movieWatchlist = new Movie();
                        movieWatchlist.setId(Integer.parseInt((String) movie.get("id")));
                        movieWatchlist.setTitle((String) movie.get("title"));
                        movieWatchlist.setPosterPath((String) movie.get("posterPath"));
                        watchlistMovies.add(movieWatchlist);
                    }
                    movieCardAdapter = new MovieCardAdapter((ArrayList<Movie>) watchlistMovies);
                    recyclerWatchlistView.setAdapter(movieCardAdapter);
                }
            });

        } else {
            Log.d("firebase", "no logged");
        }

    }
    private void addBottonNavigation(){
        bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.principal:
                    Intent mainIntent = new Intent(ListActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    break;
                case R.id.busqueda:
                    Intent searchIntent = new Intent(ListActivity.this, SearchActivity.class);
                    startActivity(searchIntent);
                    break;
                case R.id.listas:
                    Intent listsIntent = new Intent(ListActivity.this, ListActivity.class);
                    startActivity(listsIntent);
                    break;
                case R.id.perfil:
                    Intent profileIntent = new Intent(ListActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    break;
                default:
                    throw new IllegalArgumentException("item not implemented : " + item.getItemId());
            }
            return true;
        });
    }

    public boolean isLogged() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            setContentView(R.layout.activity_must_login);
            return false;
        }
        setContentView(R.layout.activity_list);
        return true;

    }

    public void loginClick(View v) {
        Intent profileIntent = new Intent(ListActivity.this, LoginActivity.class);
        startActivity(profileIntent);
    }

}