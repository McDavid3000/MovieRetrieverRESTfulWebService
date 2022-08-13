package com.example.movieslikeapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Movie list Activity that appends the user search string to the web resource url which is
 * passed to the lookup async task which produces a list of movies based on the user input
 *
 * @author Devin Grant-Miles
 */
public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list2);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView movieList = findViewById(R.id.movieSearchResults);
        RestfulSearchLookupTask task = new RestfulSearchLookupTask(movieList);
        task.execute("http://10.0.2.2:8080/MovieRestfulService2/webresources/movies/" + message);
    }
}

