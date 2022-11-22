package com.example.cine;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.cine.adapter.MovieCardAdapter;
import com.example.cine.model.MainViewModel;
import com.example.cine.pojo.Movie;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ArrayList<Movie> trendMovies, cineMovies, popularMovies;
    private RecyclerView recyclerTrendView, recyclerCineView, recyclerPopularView;
    private MovieCardAdapter movieCardAdapter;
    private MainViewModel mainViewModel;
    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trendMovies = new ArrayList<>();
        cineMovies = new ArrayList<>();
        popularMovies = new ArrayList<>();

        recyclerTrendView = findViewById(R.id.recyclerTrendsView);
        recyclerTrendView.setHasFixedSize(false);
        recyclerTrendView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerCineView = findViewById(R.id.recyclerCineView);
        recyclerCineView.setHasFixedSize(false);
        recyclerCineView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerPopularView = findViewById(R.id.recyclerPopularView);
        recyclerPopularView.setHasFixedSize(false);
        recyclerPopularView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getTrendMovies().observe(this, movies -> {
            movieCardAdapter = new MovieCardAdapter((ArrayList<Movie>) movies);
            recyclerTrendView.setAdapter(movieCardAdapter);
        });
        mainViewModel.getCineMovies().observe(this, movies -> {
            movieCardAdapter = new MovieCardAdapter((ArrayList<Movie>) movies);
            recyclerCineView.setAdapter(movieCardAdapter);
        });
        mainViewModel.getPopularMovies().observe(this, movies -> {
            movieCardAdapter = new MovieCardAdapter((ArrayList<Movie>) movies);
            recyclerPopularView.setAdapter(movieCardAdapter);
        });

        bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.principal:
                    Intent mainIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    break;
                case R.id.busqueda:
                    Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(searchIntent);
                    break;
                case R.id.listas:
                    Intent listsIntent = new Intent(MainActivity.this, ListActivity.class);
                    startActivity(listsIntent);
                    break;
                case R.id.perfil:
                    Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    break;
                default:
                    throw new IllegalArgumentException("item not implemented : " + item.getItemId());
            }
            return true;
        });
    }

    public void profileClick(View v) {
        Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(profileIntent);
    }
    public void searchClick(View v) {
        Intent profileIntent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(profileIntent);
    }
    public void ListsClick(View v) {
        Intent profileIntent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(profileIntent);
    }

}