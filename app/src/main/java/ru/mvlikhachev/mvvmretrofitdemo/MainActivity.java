package ru.mvlikhachev.mvvmretrofitdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.mvlikhachev.mvvmretrofitdemo.adapter.ResultAdapter;
import ru.mvlikhachev.mvvmretrofitdemo.model.MovieApiResponse;
import ru.mvlikhachev.mvvmretrofitdemo.model.Result;
import ru.mvlikhachev.mvvmretrofitdemo.service.MovieApiService;
import ru.mvlikhachev.mvvmretrofitdemo.service.RetrofitInstance;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Result> results;
    private RecyclerView resultRecyclerView;
    private ResultAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPopularMovies();

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.design_default_color_primary);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getPopularMovies();
        });
    }

    public void getPopularMovies() {
        MovieApiService movieApiService = RetrofitInstance.getService();

        Call<MovieApiResponse> call =
                movieApiService.getPopularMovies(getString(R.string.api_key));
        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {

                MovieApiResponse movieApiResponse =  response.body();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    results = (ArrayList<Result>) movieApiResponse.getResults();

                    fillRecyclerView();
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {

                Log.d("TestResponse", "ERROR!");

            }
        });
    }

    private void fillRecyclerView() {

        resultRecyclerView = findViewById(R.id.resultRecyclerView);

        adapter = new ResultAdapter(this, results);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            resultRecyclerView.setLayoutManager(
                    new GridLayoutManager(this, 2));
        } else {
            resultRecyclerView.setLayoutManager(
                    new GridLayoutManager(this, 4));
        }

        resultRecyclerView.setItemAnimator(new DefaultItemAnimator());
        resultRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}