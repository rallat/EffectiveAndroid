package com.israelferrer.effectiveandroid.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.twitter.sdk.android.core.models.Tweet;

/**
 * Created by icamacho on 8/6/15.
 */
public class Article implements Comparable<Article>, Parcelable {
    private final String title;
    private final String url;
    private final int retweetCount;
    private final String mediaUrl;

    public Article(String title, String url, int retweetCount, String mediaUrl) {
        this.title = title;
        this.url = url;
        this.retweetCount = retweetCount;
        this.mediaUrl = mediaUrl;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getRetweetCount() {
        return String.valueOf(retweetCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (retweetCount != article.retweetCount) return false;
        if (title != null ? !title.equals(article.title) : article.title != null) return false;
        return !(url != null ? !url.equals(article.url) : article
                .url != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + retweetCount;
        return result;
    }

    @Override
    public int compareTo(@NonNull Article another) {
        if (this.equals(another) || this.retweetCount == another.retweetCount) return 0;
        return (this.retweetCount > another.retweetCount) ? -1 : 1;
    }

    public static Article create(Tweet tweet) {
        String imgUrl = null;
        if (tweet.entities.media != null && tweet.entities.media.size() > 0) {
            imgUrl = tweet.entities.media.get(0).mediaUrl;
        }
        final String title = tweet.text.split("http")[0];
        return new Article(title, tweet.entities.urls.get(0).expandedUrl, tweet.retweetCount,
                imgUrl == null ? "" : imgUrl + ":thumb");
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

    protected Article(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.retweetCount = in.readInt();
        this.mediaUrl = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getMediaUrl() {
        return mediaUrl;
    }
}
