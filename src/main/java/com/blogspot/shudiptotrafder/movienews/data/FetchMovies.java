package com.blogspot.shudiptotrafder.movienews.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.blogspot.shudiptotrafder.movienews.BuildConfig;
import com.blogspot.shudiptotrafder.movienews.Utility;
import com.blogspot.shudiptotrafder.movienews.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * MovieNews
 * com.blogspot.shudiptotrafder.movienews
 * Created by Shudipto Trafder on 1/30/2017 at 11:27 AM.
 * Don't modify without permission of Shudipto Trafder
 */

public class FetchMovies extends AsyncTask<Void, Void, Void> {

    private ArrayList<Movies> moviesArrays;
    private final Context context;
    //private ProgressDialog progressDialog;
    //private RecyclerView recyclerView;
    //private String format;
    //private MainAdapter mainAdapter;
    private DatabaseHelper databaseHelper;


    public FetchMovies(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Please wait");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        sl("Start");
    }

    @Override
    protected Void doInBackground(Void... params) {

        moviesArrays = new ArrayList<>();

        String s = Utility.getShortMovies(context);

        //initilize database helper
        databaseHelper = new DatabaseHelper(context,s);

        //control all http task
        HttpHandler httpHandler = new HttpHandler(getAccurateUrl());

        //get json in a string form
        //from HttpHandler Class
        String jsonString = httpHandler.makeServiceCall();

        //sl("jsonString "+ jsonString);

        if (jsonString != null) {
            //make new json object
            try {
                //make new json object
                JSONObject jsonObject = new JSONObject(jsonString);

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                //sl(jsonArray.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    //taking object from position of i
                    JSONObject result = jsonArray.getJSONObject(i);

                    //poster path
                    String path = result.getString("poster_path");
                    //overview
                    String overview = result.getString("overview");

                    //release_date
                    String release_date = result.getString("release_date");

                    //original_title
                    String original_title = result.getString("original_title");

                    //original_language
                    String original_language = result.getString("original_language");

                    //title
                    String title = result.getString("title");

                    //popularity
                    String popularity = result.getString("popularity");

                    //vote_average
                    String vote_average = result.getString("vote_average");

                    //vote_average
                    String vote_count = result.getString("vote_count");


                    Movies movies = new Movies();

                    //setters
                    movies.setPoster(path);
                    movies.setLanguage(original_language);
                    movies.setOriginal_title(original_title);
                    movies.setRelease_date(release_date);
                    movies.setOverView(overview);
                    movies.setTitle(title);
                    movies.setPopularity(popularity);
                    movies.setVote_average(vote_average);
                    movies.setVote_count(vote_count);

                    moviesArrays.add(movies);

                    if (getDatabaseStatus()){

                        long insertedData = databaseHelper.insertData(movies);

                        if (insertedData >= 0) {
                            sl("Data inserted");
                        } else {
                            sl("Already inserted or failed");
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                slt("JSONException on fetch data", e);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (!getDatabaseStatus()){

            if (moviesArrays != null && moviesArrays.size() > 0){
                databaseHelper.initializeDatabaseForTheFirstTime(moviesArrays);
            }

        }
    }

    private boolean getDatabaseStatus() {
        SharedPreferences preferences = context.getSharedPreferences("FirstTime", Context.MODE_PRIVATE);

        sl(String.valueOf(preferences.getBoolean(Utility.getShortMovies(context), false)));

        return preferences.getBoolean(Utility.getShortMovies(context), false);
    }

    private String getAccurateUrl() {

        String url = null;
        String format = Utility.getShortMovies(context);

        try {

            final String FORECAST_BASE_URL =
                    "http://api.themoviedb.org/3/movie/" + format;
            final String appID = "api_key";

            //build URI using URI builder
            Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(appID, com.blogspot.shudiptotrafder.movienews.BuildConfig.MOVIE_API_KEY)
                    .build();

            url = buildUri.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //sl("Accurate url"+url);

        return url;
    }

    private static void sl(String string) {

        final String TAG = "FetchMovies";

        if (BuildConfig.DEBUG) {
            Log.e(TAG, string);
        }
    }

    private static void slt(String string, Throwable th) {

        final String TAG = "FetchMovies";

        if (BuildConfig.DEBUG) {
            Log.e(TAG, string, th);
        }
    }
}

