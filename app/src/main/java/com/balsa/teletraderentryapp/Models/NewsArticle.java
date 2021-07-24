package com.balsa.teletraderentryapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NewsArticle implements Parcelable {

    private String id;
    private String author;
    private String dateTime;
    private String headline;
    private String imageId;

    public NewsArticle(String id, String author, String dateTime, String headline, String imageId) {
        this.id = id;
        this.author = author;
        this.dateTime = dateTime;
        this.headline = headline;
        this.imageId = imageId;
    }

    protected NewsArticle(Parcel in) {
        id = in.readString();
        author = in.readString();
        dateTime = in.readString();
        headline = in.readString();
        imageId = in.readString();
    }

    public static final Creator<NewsArticle> CREATOR = new Creator<NewsArticle>() {
        @Override
        public NewsArticle createFromParcel(Parcel in) {
            return new NewsArticle(in);
        }

        @Override
        public NewsArticle[] newArray(int size) {
            return new NewsArticle[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }


    @Override
    public String toString() {
        return "NewsArticle{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", dateTime=" + dateTime +
                ", headline='" + headline + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(dateTime);
        dest.writeString(headline);
        dest.writeString(imageId);
    }
}
