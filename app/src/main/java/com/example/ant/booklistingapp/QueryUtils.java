package com.example.ant.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ant on 5/25/2017.
 */

public final class QueryUtils {

    private QueryUtils(){}

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<Book> fetchBookData (String requestUrl){
        // Create URL
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<Book> books = extractItemFromJson(jsonResponse);

        return books;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Book> extractItemFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse))
            return null;


        // Create an empty ArrayList that we can start adding books to
        List<Book> books = new ArrayList<>();
        //Try to parse the jsonResponse
        try {

            // Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject jsonBook = new JSONObject(jsonResponse);
            JSONArray jsonBookArray =  jsonBook.getJSONArray("items");

            for (int i = 0; i < jsonBookArray.length(); i++) {
                //Create JSONObject for each element in the array of JSONObjects
                JSONObject item = jsonBookArray.getJSONObject(i);
                // Get the JSONObject containing the book info
                JSONObject bookInfo = item.getJSONObject("volumeInfo");
                // Get the title in bookInfo
                String title = bookInfo.getString("title");
                // Create a list of authors from the JSONArray within bookInfo
                JSONArray jsonAuthors = bookInfo.getJSONArray("authors");
                List<String> authors = new ArrayList<>();
                for(int index = 0; index < jsonAuthors.length(); index++){
                    authors.add(jsonAuthors.getString(index));
                }
                // Get the url in bookInfo
                String url = bookInfo.getString("infoLink");
                Book book = new Book(authors, title, url);
                books.add(book);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of books
        return books;
    }



}
