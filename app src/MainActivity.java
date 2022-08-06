package com.example.movieslikeapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Main activity class for Movies Like App
 * Has a text field, editable field and button to search for
 * movies similar to the one the user has inputted
 *
 * @author Devin Grant-Miles
 */
public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.movieslikeapp2.MESSAGE";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Create intent for new activity
    //Get user search term from editable field
    //Start new activity
    public void viewMovieList(View view) {
        Intent intent = new Intent(this, MovieListActivity.class);
        EditText editText = (EditText) findViewById(R.id.movieSearchField);
        String searchMovie = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, searchMovie);
        startActivity(intent);
    }
}


