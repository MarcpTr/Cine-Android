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

public class SearchViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movies;
    private  APIInterface apiInterface;
    private Movies resource;
    private String query;
    public LiveData<List<Movie>> getSearchMovies(String query) {
        if (movies == null) {
            this.query=query;
            movies = new MutableLiveData<List<Movie>>();
            loadMovies(this.query);
        }else if (!this.query.equals(query)){
            this.query=query;
            loadMovies(query);
        }
        return movies;
    }
   private void loadMovies(String query){
       apiInterface= APIClient.getClient().create(APIInterface.class);
       Call<Movies> call = apiInterface.getSearch(query);
       call.enqueue(new Callback<Movies>() {
           @Override
           public void onResponse(Call<Movies> call, Response<Movies> response) {
               resource = response.body();
               if(resource!=null){
                   List<Movie> results= resource.getMovies();
                   if(results!=null)
                       movies.setValue(results);
               }

           }
           @Override
           public void onFailure(Call<Movies> call, Throwable t) {
               call.cancel();
           }
       });
   }
}
