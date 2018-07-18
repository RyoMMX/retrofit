package com.example.muhammadxoshnaw.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieServices {

//    https://api.themoviedb.org/3/movie/popular?api_key=b983f4d87827c8fb33e44a28e9438aa2&language=en-US&page=1

    @GET("movie/popular?api_key=b983f4d87827c8fb33e44a28e9438aa2")
    Call<Root> getMovies(@Query("page") int page);
}
