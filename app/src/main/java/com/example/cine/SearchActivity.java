package com.example.cine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.cine.adapter.MovieCardAdapter;
import com.example.cine.model.SearchViewModel;
import com.example.cine.pojo.Movie;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private EditText searchET;

    private String query;
    private static final String QUERY_KEY = "query";

    private RecyclerView recyclerSearchView;
    private MovieCardAdapter movieCardAdapter;
    private SearchViewModel searchViewModel;
    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchET = findViewById(R.id.searchET);

        recyclerSearchView = findViewById(R.id.recyclerSearchView);
        recyclerSearchView.setHasFixedSize(false);
        recyclerSearchView.setLayoutManager(new GridLayoutManager(this, 2));
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
     /*   if (savedInstanceState != null) {
            Log.d("saved", savedInstanceState.toString());
            query = savedInstanceState.getString(QUERY_KEY);

            if (query != null) {
                Log.d("query", query);
                searchViewModel.getSearchMovies(query).observe(this, movies -> {
                    movieCardAdapter = new MovieCardAdapter((ArrayList<Movie>) movies);
                    recyclerSearchView.setAdapter(movieCardAdapter);
                    Log.d("search", "recall");
                });
            }
        }*/

        bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.principal:
                    Intent mainIntent = new Intent(SearchActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    break;
                case R.id.busqueda:
                    Intent searchIntent = new Intent(SearchActivity.this, SearchActivity.class);
                    startActivity(searchIntent);
                    break;
                case R.id.listas:
                    Intent listsIntent = new Intent(SearchActivity.this, ListActivity.class);
                    startActivity(listsIntent);
                    break;
                case R.id.perfil:
                    Intent profileIntent = new Intent(SearchActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    break;
                default:
                    throw new IllegalArgumentException("item not implemented : " + item.getItemId());
            }
            return true;
        });
    }

    public void searchClick(View v) {
        String actualQuery = String.valueOf(searchET.getText());
        if (!actualQuery.equals(query) && !actualQuery.equals("")) {
            query = String.valueOf(searchET.getText());
            searchViewModel.getSearchMovies(query).observe(this, movies -> {
                movieCardAdapter = new MovieCardAdapter((ArrayList<Movie>) movies);
                recyclerSearchView.setAdapter(movieCardAdapter);
            });
        }
    }

    public void mainClick(View v) {
        Intent profileIntent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(profileIntent);
    }

    public void ListsClick(View v) {
        Intent profileIntent = new Intent(SearchActivity.this, ListActivity.class);
        startActivity(profileIntent);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        searchET.setText(savedInstanceState.getString(QUERY_KEY));
        query = savedInstanceState.getString(QUERY_KEY);

        if (query != null) {
            searchViewModel.getSearchMovies(query).observe(this, movies -> {
                movieCardAdapter = new MovieCardAdapter((ArrayList<Movie>) movies);
                recyclerSearchView.setAdapter(movieCardAdapter);
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(QUERY_KEY, query);
        super.onSaveInstanceState(outState);
    }
}