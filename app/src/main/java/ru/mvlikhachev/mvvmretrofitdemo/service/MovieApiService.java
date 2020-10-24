package ru.mvlikhachev.mvvmretrofitdemo.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mvlikhachev.mvvmretrofitdemo.model.MovieApiResponse;

public interface MovieApiService {

    @GET("movie/popular")
    Call<MovieApiResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieApiResponse> getPopularMoviesWithPaging(
            @Query("api_key") String apiKey, @Query("page") Long page);

}
