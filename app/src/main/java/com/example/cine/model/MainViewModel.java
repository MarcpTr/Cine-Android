package com.example.cine.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cine.pojo.Movies;
import com.example.cine.pojo.Movie;
import com.example.cine.retrofit.APIClient;
import com.example.cine.retrofit.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

   public enum Type {
        CINE,
        TREND,
        POPULAR
    }


    private MutableLiveData<List<Movie>> trendMovies, cineMovies, popularMovies ;
    private APIInterface apiInterface;
    private Movies resource;

    public LiveData<List<Movie>> getCineMovies( ) {
        if (cineMovies == null) {
            cineMovies = new MutableLiveData<List<Movie>>();
            loadMovies(cineMovies, Type.CINE);
        }
        return cineMovies;
    }
    public LiveData<List<Movie>> getTrendMovies( ) {
        if (trendMovies == null) {
            trendMovies = new MutableLiveData<List<Movie>>();
            loadMovies(trendMovies, Type.TREND);
        }
        return trendMovies;
    }
    public LiveData<List<Movie>> getPopularMovies( ) {
        if (popularMovies == null) {
            popularMovies = new MutableLiveData<List<Movie>>();
            loadMovies(popularMovies, Type.POPULAR);
        }
        return popularMovies;
    }
    private void loadMovies(MutableLiveData<List<Movie>> movies, Type type){
        apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<Movies> call;

        switch(type) {
            case CINE:
                call = apiInterface.getInTheatres();
                break;
            case POPULAR:
              call = apiInterface.getMostPopulars();
                break;
            case TREND:
                call = apiInterface.getTrend();
                break;
            default:
                call= apiInterface.getTrend();
        }
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                resource = response.body();
                movies.setValue(resource.getMovies());
            }
            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
