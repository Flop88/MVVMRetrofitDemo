package ru.mvlikhachev.mvvmretrofitdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.mvlikhachev.mvvmretrofitdemo.model.MovieRepo;
import ru.mvlikhachev.mvvmretrofitdemo.model.Result;

public class MainActivityViewModel extends AndroidViewModel {

    private MovieRepo movieRepo;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        movieRepo = new MovieRepo(application);
    }

    public LiveData<List<Result>> getAllMovieData() {

        return movieRepo.getMutableLiveData();
    }

}
