package com.blogspot.shudiptotrafder.movienews.data;

import android.support.compat.BuildConfig;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * MovieNews
 * com.blogspot.shudiptotrafder.movienews.data
 * Created by Shudipto Trafder on 1/30/2017 at 11:58 AM.
 * Don't modify without permission of Shudipto Trafder
 */

class HttpHandler {

    //shot movie list
    private String accurateUrl = null;

    private HttpURLConnection urlConnection;
    private InputStream inputStream;

    HttpHandler(String accurateUrl) {
        this.accurateUrl = accurateUrl;

    }

    String makeServiceCall(){

        String response = null;

        try {
            URL url = new URL(accurateUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            response = convertStreamToString(inputStream);

        } catch (IOException e) {
            slt("Error Creating Url or input stream",e);
        } finally {

            try {
                inputStream.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sl(response);

        return response;
    }

    private String convertStreamToString(InputStream inputStream) {

        String jsonString;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();

        String line;

        try {
            while ((line=bufferedReader.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            slt("Error reading line in bufferedReader",e);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                slt("Error closing reader or stream",e);
            }
        }

        jsonString = stringBuilder.toString();

        sl(jsonString);

        return jsonString;
    }

    private static void sl(String string){

        final String TAG = "HttpHandler";

        if (BuildConfig.DEBUG){
            Log.e(TAG,string);
        }
    }

    private static void slt(String string, Throwable th){

        final String TAG = "HttpHandler";

        if (BuildConfig.DEBUG){
            Log.e(TAG,string,th);
        }
    }

}
