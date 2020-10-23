package ru.mvlikhachev.mvvmretrofitdemo.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mvlikhachev.mvvmretrofitdemo.R;
import ru.mvlikhachev.mvvmretrofitdemo.service.MovieApiService;
import ru.mvlikhachev.mvvmretrofitdemo.service.RetrofitInstance;

public class MovieRepo {

    private ArrayList<Result> results = new ArrayList<>();

    private MutableLiveData<List<Result>> mutableLiveData =
            new MutableLiveData<>();
    private Application application;

    public MovieRepo(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Result>> getMutableLiveData() {

        MovieApiService movieApiService = RetrofitInstance.getService();

        Call<MovieApiResponse> call =
                movieApiService.getPopularMovies(application.getApplicationContext().getString(R.string.api_key));

        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                MovieApiResponse movieApiResponse =  response.body();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    results = (ArrayList<Result>) movieApiResponse.getResults();

                }
                mutableLiveData.setValue(results);
            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }
}
