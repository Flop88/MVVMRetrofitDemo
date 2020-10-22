package ru.mvlikhachev.mvvmretrofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.mvlikhachev.mvvmretrofitdemo.model.MovieApiResponse;
import ru.mvlikhachev.mvvmretrofitdemo.service.MovieApiService;
import ru.mvlikhachev.mvvmretrofitdemo.service.RetrofitInstance;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("TestResponse", "onCreate started!");
        getPopularMovies();
    }

    public void getPopularMovies() {
        MovieApiService movieApiService = RetrofitInstance.getService();

        Log.d("TestResponse", "getPopularMovies started!");
        Call<MovieApiResponse> call =
                movieApiService.getPopularMovies(getString(R.string.api_key));
        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {

                Log.d("TestResponse", "onResponse started!");
                MovieApiResponse movieApiResponse =  response.body();

            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {

                Log.d("TestResponse", "ERROR!");

            }
        });
    }
}