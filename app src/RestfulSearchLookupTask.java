package com.example.movieslikeapp2;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * RestfulSearchLookupTask performs GET request based on user input for similar movies
 * parses XML response and displays content to user
 *
 * @author Devin Grant-Miles
 */
public class RestfulSearchLookupTask extends AsyncTask<String, Void, String> {
    private TextView movieView;
    public static final String EXTRA_MESSAGE = "com.example.movieslikeapp2.MESSAGE";

    public RestfulSearchLookupTask(TextView movieView) {
        this.movieView = movieView;
    }

    //send GET request and parse XML response
    protected String doInBackground(String... params) {
        if (params.length == 0) {
            return "No URL provided";
        }
        try {
            URL movieURL = new URL(params[0]);//Movie DB URL
            HttpURLConnection conn = (HttpURLConnection) movieURL.openConnection();
            conn.setReadTimeout(3000); // 3000ms
            conn.setConnectTimeout(3000); // 3000ms
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder xmlResponse = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    xmlResponse.append(line);
                    line = br.readLine();
                }
                br.close();
                conn.disconnect();
                if (xmlResponse.length() == 0)
                    return "No results found";

                StringBuilder movieList = new StringBuilder();
                int movieIndex = xmlResponse.indexOf("<movie>");
                while (movieIndex >= 0) {
                    int titleStartIndex
                            = xmlResponse.indexOf("<title>", movieIndex) + 7;
                    int titleEndIndex
                            = xmlResponse.indexOf("</", titleStartIndex);
                    String title = (titleEndIndex > titleStartIndex) ?
                            xmlResponse.substring(titleStartIndex,
                                    titleEndIndex) : "No title";
                    int yearStartIndex = xmlResponse.indexOf
                            ("<year>", movieIndex) + 6;
                    int yearEndIndex = xmlResponse.indexOf
                            ("</", yearStartIndex);
                    String year
                            = (yearEndIndex > yearStartIndex) ?
                            xmlResponse.substring(yearStartIndex,
                                    yearEndIndex) : "No year";
                    movieList.append("Title: ").append(title)
                            .append(", Year: ").append(year)
                            .append("\n");
                    movieIndex = xmlResponse.indexOf("<movie>", movieIndex + 1);
                }
                return movieList.toString();
            } else
                return "HTTP Response code " + responseCode;
        } catch (MalformedURLException e) {
            Log.e("RestfulSearchLookupTask", "Malformed URL: " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("RestfulSearchLookupTask", "IOException: " + e);
            e.printStackTrace();
        }
        return "Error during HTTP request to url " + params[0];
    }

    //Takes XML response and creates a clickable link out of all the returned movie titles
    protected void onPostExecute(String workerResult) {
        int movieIndex = workerResult.indexOf("Title");
        SpannableString ss = new SpannableString(workerResult);

        while (movieIndex >= 0) {
            int titleStartIndex
                    = workerResult.indexOf("Title: ", movieIndex) + 7;
            int titleEndIndex
                    = workerResult.indexOf(", Year", titleStartIndex);
            final String title = workerResult.substring(titleStartIndex,
                    titleEndIndex);

            int yearStartIndex
                    = workerResult.indexOf("Year: ", movieIndex) + 6;
            int yearEndIndex
                    = workerResult.indexOf("\n", yearStartIndex);
            final String year = workerResult.substring(yearStartIndex,
                    yearEndIndex);

            //OnClick method starts an intent and Activity that takes the title and year of the
            // clicked movie
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(widget.getContext(), MovieDetailActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, title + "/" + year);
                    widget.getContext().startActivity(intent);
                }
            };

            ss.setSpan(clickableSpan, titleStartIndex, titleEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            movieIndex = workerResult.indexOf("Title", movieIndex + 1);
        }

        //set the text displayed in the view
        movieView.setText(ss);
        movieView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}