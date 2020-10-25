package ru.mvlikhachev.mvvmretrofitdemo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.mvlikhachev.mvvmretrofitdemo.R;
import ru.mvlikhachev.mvvmretrofitdemo.adapter.ResultAdapter;
import ru.mvlikhachev.mvvmretrofitdemo.databinding.ActivityMainBinding;
import ru.mvlikhachev.mvvmretrofitdemo.model.MovieApiResponse;
import ru.mvlikhachev.mvvmretrofitdemo.model.Result;
import ru.mvlikhachev.mvvmretrofitdemo.service.MovieApiService;
import ru.mvlikhachev.mvvmretrofitdemo.service.RetrofitInstance;
import ru.mvlikhachev.mvvmretrofitdemo.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private PagedList<Result> results;
    private RecyclerView resultRecyclerView;
    private ResultAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private MainActivityViewModel mainActivityViewModel;

    private ActivityMainBinding activityMainBinding;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);



        mainActivityViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MainActivityViewModel.class);

        getPopularMovies();

        swipeRefreshLayout = activityMainBinding.swiperefresh;
        swipeRefreshLayout.setColorSchemeColors(R.color.design_default_color_primary);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getPopularMovies();
        });
    }

    public void getPopularMovies() {

//        mainActivityViewModel.getAllMovieData().observe(this, resultsList -> {
//              results = (ArrayList<Result>) resultsList;
//              fillRecyclerView();
//        });
        mainActivityViewModel.getPagedListLiveData().observe(this, new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(PagedList<Result> resultsList) {
                results = resultsList;
                fillRecyclerView();
            }
        });
    }

    private void fillRecyclerView() {

        resultRecyclerView = activityMainBinding.resultRecyclerView;

        adapter = new ResultAdapter(this);
        adapter.submitList(results);

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
        swipeRefreshLayout.setRefreshing(false);

    }
}