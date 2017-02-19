package com.example.kelly79126.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kelly79126.flickster.adapters.MovieArrayAdapter;
import com.example.kelly79126.flickster.movies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    @BindView(R.id.lvMovies) ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);
        setListViewListener();

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        getMovieFromUrl(url);
    }

    private void setListViewListener(){
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // first parameter is the context, second is the class of the activity to launch
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                // put "extras" into the bundle for access in the second activity
                i.putExtra("posterPath", movies.get(position).getPosterPath());
                i.putExtra("backdropPath", movies.get(position).getBackdropPath());
                i.putExtra("tilte", movies.get(position).getTitle());
                i.putExtra("overview", movies.get(position).getOverview());
                i.putExtra("releaseDate", movies.get(position).getReleaseDate());
                i.putExtra("originalLanguage", movies.get(position).getOriginalLanguage());
                i.putExtra("popularity", movies.get(position).getPopularity());
                i.putExtra("voteAverage", movies.get(position).getVoteAverage());
                // brings up the second activity
                startActivity(i);
            }
        });
    }

    private void getMovieFromUrl(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Flickster", "getMovieFromUrl call failure " + e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // Read data on the worker thread
                final String responseData = response.body().string();

                // Run view-related code back on the main thread
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(responseData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (obj != null) {
                            JSONArray movieJsonResult = null;
                            try {
                                movieJsonResult = obj.getJSONArray("results");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            movies.addAll(Movie.fromJSONArray(movieJsonResult));
                            movieAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }
}
