package com.blogspot.shudiptotrafder.movienews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blogspot.shudiptotrafder.movienews.adapter.MainAdapter;
import com.blogspot.shudiptotrafder.movienews.data.FetchMovies;
import com.blogspot.shudiptotrafder.movienews.data.Movies;
import com.blogspot.shudiptotrafder.movienews.database.DatabaseHelper;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayList<Movies> movies;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private ProgressDialog dialog;
    private boolean flag = false;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        final View no_internet_layout = view.findViewById(R.id.no_internet_lay);

        no_internet_layout.setVisibility(View.GONE);

        recyclerView = (RecyclerView) view.findViewById(R.id.mainRec);
        Button button = (Button) view.findViewById(R.id.no_internet_btn);
        dialog = new ProgressDialog(getContext());
        //TextView textView = (TextView) view.findViewById(R.id.no_internet_tv);

        String s = Utility.getShortMovies(getContext());

        databaseHelper = new DatabaseHelper(getContext(),s);

        if (!getDatabaseStatus()) {

            if (Utility.isNetworkAvailable(getContext())) {
                FetchMovies fetchMovies = new FetchMovies(getActivity().getApplication().getApplicationContext());
                fetchMovies.execute();

                dialog.setMessage("Please wait a second...");
                dialog.setCancelable(false);
                dialog.show();

            } else {
                recyclerView.setVisibility(View.GONE);
                no_internet_layout.setVisibility(View.VISIBLE);
            }

        } else {
            setRealLayout();
        }


        //if no internet then btn click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!flag){
            if (getDatabaseStatus()) {
                if (Utility.isNetworkAvailable(getContext())) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FetchMovies fetchMovies = new FetchMovies(getActivity());
                            fetchMovies.execute();
                        }
                    });
                    thread.start();
                    flag = true;
                }
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
    }

    private void setRealLayout() {

        dialog.dismiss();

        //get Movies From Database
        movies = databaseHelper.getAllMovies();
        Log.e("movies", String.valueOf(movies.size()));

        int i = getContext().getResources().getConfiguration().orientation;

        GridLayoutManager gridLayoutManager;

        if (i == 2) {
            gridLayoutManager = new GridLayoutManager(getContext(), 3);

        } else {
            gridLayoutManager = new GridLayoutManager(getContext(), 2);

        }

        //gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

//        LinearLayoutManager linearLayoutManager = new
//                LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //setting layout manager
        recyclerView.setLayoutManager(gridLayoutManager);
        //recyclerView.setLayoutManager(linearLayoutManager);


        MainAdapter mainAdapter = new MainAdapter(movies, getContext());
        mainAdapter.setItemClickListener(new MainAdapter.ClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra(Intent.EXTRA_SUBJECT, position);
                intent.putParcelableArrayListExtra(Intent.EXTRA_TEXT, movies);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(mainAdapter);
    }

    private boolean getDatabaseStatus() {
        final SharedPreferences preferences = getContext().getSharedPreferences("FirstTime", Context.MODE_PRIVATE);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        setRealLayout();
                    }
                };

                preferences.registerOnSharedPreferenceChangeListener(listener);
            }
        });

        thread.start();

        Log.e("Status",String.valueOf(preferences.getBoolean(Utility.getShortMovies(getContext()), false)));

        return preferences.getBoolean(Utility.getShortMovies(getContext()), false);
    }

}
