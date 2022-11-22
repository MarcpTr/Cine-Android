package com.example.cine.retrofit;


import com.example.cine.pojo.Details;
import com.example.cine.pojo.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("3/trending/movie/week?language=es-Es&api_key=b7048181b82a3678ad874fa00559a427&page=1")
    Call<Movies> getTrend();

    @GET("3/discover/movie?sort_by=popularity.desc&language=es-Es&api_key=b7048181b82a3678ad874fa00559a427&page=1")
    Call<Movies> getMostPopulars();

    @GET("3/discover/movie?primary_release_date.gte=2014-09-15&primary_release_date.lte=2014-10-22&language=es-Es&api_key=b7048181b82a3678ad874fa00559a427&page=1")
    Call<Movies> getInTheatres();

    @GET("3/movie/{id}?api_key=b7048181b82a3678ad874fa00559a427&language=es-Es&append_to_response=videos,credits,similar")
    Call<Details> getInfoMovie(@Path("id") String id);

    @GET("3/search/movie?api_key=b7048181b82a3678ad874fa00559a427&language=es-Es")
    Call<Movies> getSearch(@Query("query") String query);
}