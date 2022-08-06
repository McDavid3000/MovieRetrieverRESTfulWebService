package com.example.movieslikeapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity that displays the details of a single movie and allows user to rate that same movie
 *
 * @author Devin Grant-Miles
 */
public class MovieDetailActivity extends AppCompatActivity {

    //method creates and starts RestfulSpecificLookupTask which takes in
    //web resource url with movie title and year appended to it
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);

        Intent intent = getIntent();
        String urlAppend = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView movieList = findViewById(R.id.movieDetailView);
        RestfulSpecificLookupTask task = new RestfulSpecificLookupTask(movieList);

        task.execute("http://10.0.2.2:8080/MovieRestfulService2/webresources/movies/" + urlAppend);
    }

    //method that gets user input of a rating for the displayed movie and submits it via
    //RestfulSubmitRatingTask
    //input validation not completed yet
    public void submitRating(View view) {
        EditText editRating = (EditText) findViewById(R.id.editRating);
        String rating = editRating.getText().toString();

        //get the movie text currently displayed
        TextView movieDetails = (TextView) findViewById(R.id.movieDetailView);
        String text = movieDetails.getText().toString();

        int index = 0;

        //parse the title and year from the movie already displayed
        int titleStartIndex
                = text.indexOf("Title: ", index) + 7;
        int titleEndIndex
                = text.indexOf("\n", titleStartIndex);
        final String title = text.substring(titleStartIndex,
                titleEndIndex);
        int yearStartIndex
                = text.indexOf("Year: ", index) + 6;
        int yearEndIndex
                = text.indexOf("\n", yearStartIndex);
        final String year = text.substring(yearStartIndex,
                yearEndIndex);

        RestfulSubmitRatingTask task = new RestfulSubmitRatingTask(MovieDetailActivity.this);

        task.execute("http://10.0.2.2:8080/MovieRestfulService2/webresources/movies/" + title + "/" + year + "/" + rating);
    }
}
