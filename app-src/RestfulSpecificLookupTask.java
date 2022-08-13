package com.example.movieslikeapp2;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Task looks up a specific movie via GET request using url with movie title and year appended
 * XML response is parsed and displayed to user
 *
 * @author Devin Grant-Miles
 */
public class RestfulSpecificLookupTask extends AsyncTask<String, Void, String> {
    private TextView movieView;
    public static final String EXTRA_MESSAGE = "com.example.movieslikeapp2.MESSAGE";

    public RestfulSpecificLookupTask(TextView movieView) {
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

                    int genre1StartIndex = xmlResponse.indexOf
                            ("<genre1>", movieIndex) + 8;
                    int genre1EndIndex = xmlResponse.indexOf
                            ("</", genre1StartIndex);
                    String genre1
                            = (genre1EndIndex > genre1StartIndex) ?
                            xmlResponse.substring(genre1StartIndex,
                                    genre1EndIndex) : "No genre";

                    int genre2StartIndex = xmlResponse.indexOf
                            ("<genre2>", movieIndex) + 8;
                    int genre2EndIndex = xmlResponse.indexOf
                            ("</", genre2StartIndex);
                    String genre2
                            = (genre2EndIndex > genre2StartIndex) ?
                            xmlResponse.substring(genre2StartIndex,
                                    genre2EndIndex) : "No second genre";

                    int star1StartIndex = xmlResponse.indexOf
                            ("<star1>", movieIndex) + 7;
                    int star1EndIndex = xmlResponse.indexOf
                            ("</", star1StartIndex);
                    String star1
                            = (star1EndIndex > star1StartIndex) ?
                            xmlResponse.substring(star1StartIndex,
                                    star1EndIndex) : "No star";

                    int lengthStartIndex = xmlResponse.indexOf
                            ("<length>", movieIndex) + 8;
                    int lengthEndIndex = xmlResponse.indexOf
                            ("</", lengthStartIndex);
                    String length
                            = (lengthEndIndex > lengthStartIndex) ?
                            xmlResponse.substring(lengthStartIndex,
                                    lengthEndIndex) : "No length";

                    int rRatedStartIndex = xmlResponse.indexOf
                            ("<rRated>", movieIndex) + 8;
                    int rRatedEndIndex = xmlResponse.indexOf
                            ("</", rRatedStartIndex);
                    String rRated
                            = (rRatedEndIndex > rRatedStartIndex) ?
                            xmlResponse.substring(rRatedStartIndex,
                                    rRatedEndIndex) : "Unknown R rating";

                    int yearStartIndex = xmlResponse.indexOf
                            ("<year>", movieIndex) + 6;
                    int yearEndIndex = xmlResponse.indexOf
                            ("</", yearStartIndex);
                    String year
                            = (yearEndIndex > yearStartIndex) ?
                            xmlResponse.substring(yearStartIndex,
                                    yearEndIndex) : "No year";

                    int imdbRatingStartIndex = xmlResponse.indexOf
                            ("<imdbRating>", movieIndex) + 12;
                    int imdbRatingEndIndex = xmlResponse.indexOf
                            ("</", imdbRatingStartIndex);
                    String imdbRating
                            = (imdbRatingEndIndex > imdbRatingStartIndex) ?
                            xmlResponse.substring(imdbRatingStartIndex,
                                    imdbRatingEndIndex) : "No IMDB Rating";

                    movieList.append("Title: ").append(title).append("\n")
                            .append("Year: ").append(year).append("\n")
                            .append("Genre 1: ").append(genre1).append("\n")
                            .append("Genre 2: ").append(genre2).append("\n")
                            .append("Starring: ").append(star1).append("\n")
                            .append("Length: ").append(length).append("\n")
                            .append("R Rated: ").append(rRated).append("\n")
                            .append("IMDB Rating: ").append(imdbRating).append("\n")
                    ;
                    movieIndex = xmlResponse.indexOf("<movie>", movieIndex + 1);
                }
                return movieList.toString();
            } else
                return "HTTP Response code " + responseCode;
        } catch (MalformedURLException e) {
            Log.e("RestfulLookupTask", "Malformed URL: " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("RestfulLookupTask", "IOException: " + e);
            e.printStackTrace();
        }
        return "Error during HTTP request to url " + params[0];
    }

    protected void onPostExecute(String workerResult) {
        movieView.setText(workerResult);
    }
}