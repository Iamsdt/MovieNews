package com.blogspot.shudiptotrafder.movienews.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MovieNews
 * com.blogspot.shudiptotrafder.movienews.data
 * Created by Shudipto Trafder on 1/30/2017 at 11:28 AM.
 * Don't modify without permission of Shudipto Trafder
 */

public class Movies implements Parcelable {

    private String poster,overView,release_date, original_title,language,
            title,popularity,vote_count,video,vote_average;


    //constructor
    public Movies(){
        //blank
    }

//    public Movies(String poster, String overView, String release_date,
//                  String original_title, String language, String title,
//                  String popularity, String vote_count, String video,
//                  String vote_average) {
//
//        this.poster = poster;
//        this.overView = overView;
//        this.release_date = release_date;
//        this.original_title = original_title;
//        this.language = language;
//        this.title = title;
//        this.popularity = popularity;
//        this.vote_count = vote_count;
//        this.video = video;
//        this.vote_average = vote_average;
//    }

    //getters and setters

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

//    public String getVideo() {
//        return video;
//    }
//
//    public void setVideo(String video) {
//        this.video = video;
//    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster);
        dest.writeString(this.overView);
        dest.writeString(this.release_date);
        dest.writeString(this.original_title);
        dest.writeString(this.language);
        dest.writeString(this.title);
        dest.writeString(this.popularity);
        dest.writeString(this.vote_count);
        dest.writeString(this.video);
        dest.writeString(this.vote_average);
    }

    private Movies(Parcel in) {
        this.poster = in.readString();
        this.overView = in.readString();
        this.release_date = in.readString();
        this.original_title = in.readString();
        this.language = in.readString();
        this.title = in.readString();
        this.popularity = in.readString();
        this.vote_count = in.readString();
        this.video = in.readString();
        this.vote_average = in.readString();
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
