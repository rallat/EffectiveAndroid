package com.israelferrer.effectiveandroid.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.twitter.sdk.android.core.models.Tweet;

/**
 * Created by icamacho on 8/19/15.
 */
public class Image implements Comparable<Image>, Parcelable {
    private final String title;
    private String url;
    private final int retweetCount;
    private final String mediaUrl;

    public Image(String title, int retweetCount, String mediaUrl) {
        this.title = title;
        this.retweetCount = retweetCount;
        this.mediaUrl = mediaUrl;
    }

    public static Image create(Tweet tweet) {
        String imgUrl = null;
        if (tweet.entities.media != null && tweet.entities.media.size() > 0) {
            imgUrl = tweet.entities.media.get(0).mediaUrl;
        }
        final String title = tweet.text.split("http")[0];
        return new Image(title, tweet.retweetCount,
                imgUrl == null ? "" : imgUrl + ":large");
    }


    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getRetweetCount() {
        return String.valueOf(retweetCount);
    }

    @NonNull
    public String getMediaUrl() {
        return mediaUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (retweetCount != image.retweetCount) return false;
        if (title != null ? !title.equals(image.title) : image.title != null) return false;
        if (url != null ? !url.equals(image.url) : image.url != null) return false;
        return !(mediaUrl != null ? !mediaUrl.equals(image.mediaUrl) : image.mediaUrl != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + retweetCount;
        result = 31 * result + (mediaUrl != null ? mediaUrl.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeInt(this.retweetCount);
        dest.writeString(this.mediaUrl);
    }

    protected Image(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.retweetCount = in.readInt();
        this.mediaUrl = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int compareTo(@NonNull Image another) {
        if (this.equals(another) || this.retweetCount == another.retweetCount) return 0;
        return (this.retweetCount > another.retweetCount) ? -1 : 1;
    }
}
