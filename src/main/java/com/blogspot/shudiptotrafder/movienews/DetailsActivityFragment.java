package com.blogspot.shudiptotrafder.movienews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.shudiptotrafder.movienews.data.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    public DetailsActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        ImageView poster = (ImageView) view.findViewById(R.id.details_imageView);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView original_title = (TextView) view.findViewById(R.id.original_title);
        TextView language = (TextView) view.findViewById(R.id.language);
        TextView overView = (TextView) view.findViewById(R.id.overview);
        TextView release_date = (TextView) view.findViewById(R.id.release_date);
        TextView vote_average = (TextView) view.findViewById(R.id.vote_average);
        TextView vote_count = (TextView) view.findViewById(R.id.vote_count);
        TextView popularity = (TextView) view.findViewById(R.id.popularity);

        Intent intent = getActivity().getIntent();

        ArrayList<Movies> arrayList = intent.getParcelableArrayListExtra(Intent.EXTRA_TEXT);

        int pos = intent.getIntExtra(Intent.EXTRA_SUBJECT,0);

        Movies movies = arrayList.get(pos);

        title.setText(getContext().getString(R.string.title,movies.getTitle()));
        original_title.setText(getContext().getString(R.string.original_title,movies.getOriginal_title()));
        language.setText(getContext().getString(R.string.original_language,movies.getLanguage()));
        overView.setText(getContext().getString(R.string.overview,movies.getOverView()));
        release_date.setText(getContext().getString(R.string.release_date,movies.getRelease_date()));
        vote_average.setText(getContext().getString(R.string.vote_average,movies.getVote_average()));
        vote_count.setText(getContext().getString(R.string.vote_count,movies.getVote_count()));
        popularity.setText(getContext().getString(R.string.popularity,movies.getPopularity()));

        Picasso.with(getContext()).load(getImageUrl(movies.getPoster())).fit().into(poster);

        return view;
    }

    private String getImageUrl(String path){
        String url;
        String baseUrl = "http://image.tmdb.org/t/p/w185/";

        url = baseUrl+path;

        return url;
    }
}
