package com.blogspot.shudiptotrafder.movienews.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blogspot.shudiptotrafder.movienews.R;
import com.blogspot.shudiptotrafder.movienews.data.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * MovieNews
 * com.blogspot.shudiptotrafder.movienews.adapter
 * Created by Shudipto Trafder on 1/30/2017 at 1:57 PM.
 * Don't modify without permission of Shudipto Trafder
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {


    //array list that's contain weather type data
    private final ArrayList<Movies> arrayList;
    //context of used class
    private Context context;
    //private Intent intent;
    private ClickListener clickListener;

    public MainAdapter(ArrayList<Movies> movies, Context context) {
        this.arrayList = movies;
        this.context = context;
        //this.intent = intent;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //create a new view  using LayoutInflater and
        //inflate our list item layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);

        //new MyViewHolder class return
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        //get weathers data from position of array list
        final Movies movies = arrayList.get(position);


        //set all text
//        holder.name.setText(String.format("Title: %s", movies.getTitle()));
//        holder.country.setText(String.format("Overview: %s",movies.getOverView()));
//        holder.main.setText(String.format("Original Title: %s",movies.getOriginal_title()));
//        holder.des.setText(String.format("Popularity: %s",movies.getPopularity()));
//        holder.day.setText(String.format("Vote: %s",movies.getVote_average()));
//        holder.night.setText(String.format("Language: %s",movies.getLanguage()));
//        holder.date.setText(String.format("Release_date: %s",movies.getRelease_date()));

        try {
            Picasso.with(context).load(getImageUrl(movies.getPoster()))
                    .fit().into(holder.imageView);

           int i = holder.imageView.getMaxHeight();

            Log.e("Image height",String.valueOf(i)+getImageUrl(movies.getPoster()));

        } catch (Exception e){
            e.printStackTrace();
        }



        //Log.e("Adapter", "onBindViewHolder: "+weather.getDate());

        //position of adapter
        // this position is send to DetailsActivity
        //final int pos = holder.getAdapterPosition();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.itemClicked(v,holder.getAdapterPosition());
                }
            }
        });


        //set card as Click able
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, DetailsActivity.class);
//                intent.putExtra(Intent.EXTRA_SUBJECT,pos);
//                intent.putParcelableArrayListExtra(Intent.EXTRA_TEXT,arrayList);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //this methods is called in used activity
    public void setItemClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    private String getImageUrl(String path){
        String url;
        String baseUrl = "http://image.tmdb.org/t/p/w185/";

        url = baseUrl+path;

        return url;
    }

    //view holder class
    class MyViewHolder extends RecyclerView.ViewHolder{

        //Views
        //private TextView name,country,main,des,day,night,date;
        private final CardView cardView;
        private final ImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            //this view used in first time to check all data from server
            //after created another activity no need this view
            //bind Views
            //name = (TextView) itemView.findViewById(R.id.name);
            //country = (TextView) itemView.findViewById(R.id.country);
            //main = (TextView) itemView.findViewById(R.id.main);
            //des = (TextView) itemView.findViewById(R.id.des);
            //day = (TextView) itemView.findViewById(R.id.day);
            //night = (TextView) itemView.findViewById(R.id.night);
            //date = (TextView) itemView.findViewById(R.id.date);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public interface ClickListener{
        void itemClicked(View view, int position);
    }

}
