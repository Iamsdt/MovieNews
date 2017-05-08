package com.blogspot.shudiptotrafder.movienews;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

/**
 * MovieNews
 * com.blogspot.shudiptotrafder.movienews
 * Created by Shudipto Trafder on 2/4/2017 at 4:55 PM.
 * Don't modify without permission of Shudipto Trafder
 */

public class Utility {

    static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();

        return info != null && info.isConnectedOrConnecting();
    }

    public static String getShortMovies(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getString(context.getString(R.string.pref_sort_movie_key),
                context.getString(R.string.pref_sort_movie_popular_value));
    }

}
