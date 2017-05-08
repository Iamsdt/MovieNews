package com.blogspot.shudiptotrafder.movienews.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.blogspot.shudiptotrafder.movienews.data.Movies;

import java.util.ArrayList;

/**
 * MovieNews
 * com.blogspot.shudiptotrafder.movienews
 * Created by Shudipto Trafder on 2/19/2017 at 4:49 PM.
 * Don't modify without permission of Shudipto Trafder
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private final Context context;

    //name and version
    private static final String DB_NAME = "Movies.db";
    private static final int DB_VERSION = 1;

    //table name
    private String TABLE_NAME = "popular";
    private final String ID = "_id";
    private final String POSTER_PATH = "poster_path";
    private final String OVERVIEW = "overview";
    private final String RELEASE_DATE = "release_date";
    private final String ORIGINAL_TITLE = "original_title";
    private final String ORIGINAL_LANG = "original_language";
    private final String TITLE = "title";
    private final String POPULARITY = "popularity";
    private final String VOTE_AVENGER = "vote_average";
    private final String VOTE_COUNT = "vote_count";

    //SQL quarry
    private final String SQL_DATABASE_COMMAND = "CREATE TABLE " + TABLE_NAME + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + POSTER_PATH + " TEXT, "
            + OVERVIEW + " TEXT, " + RELEASE_DATE + " TEXT, " + ORIGINAL_TITLE + " TEXT, "
            + ORIGINAL_LANG + " TEXT, " + TITLE + " TEXT, " + POPULARITY + " TEXT, "
            + VOTE_AVENGER + " TEXT, " + VOTE_COUNT + " TEXT, "
            +" UNIQUE ("+TITLE+") ON CONFLICT REPLACE)";



    public DatabaseHelper(Context con,String tableName) {
        super(con, DB_NAME, null, DB_VERSION);
        context = con;
        this.TABLE_NAME = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DATABASE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public long insertData(Movies movies) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(POSTER_PATH, movies.getPoster());
        contentValues.put(OVERVIEW, movies.getOverView());
        contentValues.put(RELEASE_DATE, movies.getRelease_date());
        contentValues.put(ORIGINAL_LANG, movies.getLanguage());
        contentValues.put(ORIGINAL_TITLE, movies.getOriginal_title());
        contentValues.put(TITLE, movies.getTitle());
        contentValues.put(POPULARITY, movies.getPopularity());
        contentValues.put(VOTE_COUNT, movies.getVote_count());
        contentValues.put(VOTE_AVENGER, movies.getVote_average());

        long inserted = -1;

        String query = "SELECT " + TITLE + " FROM " + TABLE_NAME + " WHERE " + TITLE + " = '" + movies.getTitle() + "'";

        try {
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {

                return inserted;

            } else {
                inserted = db.insert(TABLE_NAME, null, contentValues);
            }

            cursor.close();

        } catch (SQLException e) {
            Log.e("SQLException", "SQLException inserted failed cursor crashed");
        }

        return inserted;
    }

    //get All data to array list

    public ArrayList<Movies> getAllMovies() {

        ArrayList<Movies> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Movies movies = new Movies();

                movies.setPoster(getDataFromCursor(cursor, POSTER_PATH));
                movies.setLanguage(getDataFromCursor(cursor, ORIGINAL_LANG));
                movies.setOriginal_title(getDataFromCursor(cursor, ORIGINAL_TITLE));
                movies.setRelease_date(getDataFromCursor(cursor, RELEASE_DATE));
                movies.setOverView(getDataFromCursor(cursor, OVERVIEW));
                movies.setTitle(getDataFromCursor(cursor, TITLE));
                movies.setPopularity(getDataFromCursor(cursor, POPULARITY));
                movies.setVote_average(getDataFromCursor(cursor, VOTE_AVENGER));
                movies.setVote_count(getDataFromCursor(cursor, VOTE_COUNT));

                arrayList.add(movies);

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return arrayList;
    }

    private String getDataFromCursor(Cursor cursor, String collumnName) {
        return cursor.getString(cursor.getColumnIndex(collumnName));
    }

    public void initializeDatabaseForTheFirstTime(ArrayList<Movies> moviesList) {

        Log.e("database first time", "Call");

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("BEGIN");

        ContentValues contentValues = new ContentValues();

        for (Movies movies : moviesList) {

            contentValues.put(POSTER_PATH, movies.getPoster());
            contentValues.put(OVERVIEW, movies.getOverView());
            contentValues.put(RELEASE_DATE, movies.getRelease_date());
            contentValues.put(ORIGINAL_LANG, movies.getLanguage());
            contentValues.put(ORIGINAL_TITLE, movies.getOriginal_title());
            contentValues.put(TITLE, movies.getTitle());
            contentValues.put(POPULARITY, movies.getPopularity());
            contentValues.put(VOTE_COUNT, movies.getVote_count());
            contentValues.put(VOTE_AVENGER, movies.getVote_average());

            long status = database.insert(TABLE_NAME, null, contentValues);

            if (status > 0) {
                Log.e("database first time", "success");
            } else {
                Log.e("database first time", "failed");
            }

        }
        database.execSQL("COMMIT");

        SharedPreferences preferences = context.
                getSharedPreferences("FirstTime", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(TABLE_NAME, true);

        editor.apply();

    }

}
