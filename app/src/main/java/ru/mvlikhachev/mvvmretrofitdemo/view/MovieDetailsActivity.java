package ru.mvlikhachev.mvvmretrofitdemo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ru.mvlikhachev.mvvmretrofitdemo.R;
import ru.mvlikhachev.mvvmretrofitdemo.databinding.ActivityMovieDetailsBinding;
import ru.mvlikhachev.mvvmretrofitdemo.model.Result;

public class MovieDetailsActivity extends AppCompatActivity {

    private Result result;

    private ActivityMovieDetailsBinding activityMovieDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        activityMovieDetailsBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_movie_details);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("movieData")) {

            result = intent.getParcelableExtra("movieData");

            activityMovieDetailsBinding.setResult(result);
            }
    }
}