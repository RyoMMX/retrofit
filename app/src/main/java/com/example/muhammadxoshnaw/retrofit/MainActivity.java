package com.example.muhammadxoshnaw.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.alexbykov.nopaginate.callback.OnLoadMoreListener;
import ru.alexbykov.nopaginate.paginate.NoPaginate;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MovieAdapter adapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    NoPaginate noPaginate;
    int page = 1;
    MovieServices movieServices;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.movie_rv);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieServices = retrofit.create(MovieServices.class);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Movie movie) {
                Toast.makeText(MainActivity.this, movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        noPaginate = NoPaginate.with(recyclerView)
                .setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        if (!isLoading) {
                            isLoading = true;
                            Call<Root> rootCall = movieServices.getMovies(++page);
                            rootCall.enqueue(new OnMovieLoadListener());
                        }
                    }
                })
                .build();
    }

    private class OnMovieLoadListener implements Callback<Root> {

        @Override
        public void onResponse(Call<Root> call, Response<Root> response) {
            Root mRoot = response.body();
            Log.v(TAG, "URL = " + response.raw().request().url());
            if (mRoot != null) {
                if (mRoot.getResults() != null) {
                    adapter.addMovies(mRoot.getResults());
                    Log.v(TAG, "data size = " + mRoot.getResults().size());
                }
            }

            isLoading = false;
        }

        @Override
        public void onFailure(Call<Root> call, Throwable t) {
            isLoading = false;
        }
    }
}
