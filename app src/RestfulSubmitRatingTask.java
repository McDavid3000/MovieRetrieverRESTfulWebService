package com.example.movieslikeapp2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Async task that submits a rating provided by user via PUT request
 *
 * @author Devin Grant-Miles
 */

public class RestfulSubmitRatingTask extends AsyncTask<String, Void, String> {
    // context for toast message
    private Context mContext;

    public RestfulSubmitRatingTask(final Context context) {
        mContext = context;
    }

    //send PUT request for user inputted movie rating
    protected String doInBackground(String... params) {
        if (params.length == 0) {
            return "No URL provided";
        }
        try {
            URL movieURL = new URL(params[0]);//Movie DB URL
            HttpURLConnection conn = (HttpURLConnection) movieURL.openConnection();
            conn.setReadTimeout(3000); // 3000ms
            conn.setConnectTimeout(3000); // 3000ms
            conn.setRequestMethod("PUT");
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                return "Rating submitted successfully";
            } else {
                return "HTTP Response code " + responseCode;
            }
        } catch (MalformedURLException e) {
            Log.e("RestfulSubmitRatingTask", "Malformed URL: " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("RestfulSubmitRatingTask", "IOException: " + e);
            e.printStackTrace();
        }
        return "Error during HTTP request to url " + params[0];
    }

    //toast message to indicate success of rating submission
    protected void onPostExecute(String workerResult) {
        Toast.makeText(mContext, workerResult, Toast.LENGTH_SHORT).show();
    }
}