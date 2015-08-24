package com.israelferrer.effectiveandroid.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.israelferrer.effectiveandroid.entities.Article;
import com.israelferrer.effectiveandroid.service.CustomApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.UrlEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by icamacho on 8/15/15.
 */
public class TopArticleListModelImpl implements TopArticleListModel {
    private final CustomApiClient client;

    public TopArticleListModelImpl(TwitterSession session) {
        this(new CustomApiClient(session));
    }

    TopArticleListModelImpl(CustomApiClient client) {
        this.client = client;
    }

    @Override
    public void getMostRtArticles(final Callback<List<Article>> callback) {
        client.getTimelineService().homeTimeline(200, true, true, true, true,
                new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        final List<Article> items = processTweets(result);
                        callback.success(items, null);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        Log.d("API error", e.getMessage());
                        callback.failure(e);
                    }
                });
    }

    @NonNull
    private List<Article> processTweets(Result<List<Tweet>> result) {
        final List<Article> items = new ArrayList<>();
        for (Tweet tweet : result.data) {
            if (tweet.entities != null && tweet.entities.urls != null &&
                    tweet.entities.urls.size() > 0 &&
                    isEligibleDomain(tweet.entities.urls)) {
                items.add(Article.create(tweet));
            }
        }
        Collections.sort(items);
        return items;
    }

    private boolean isEligibleDomain(List<UrlEntity> urls) {
        if (urls == null || urls.get(0) == null || urls.get(0).expandedUrl == null) {
            return false;
        }
        final String url = urls.get(0).expandedUrl;
        return !url.contains("twitter.com") && !url.contains("goo.gl") &&
                !url.contains("vine.co") && !url.contains("vimeo.com") &&
                !url.contains("youtube.com") && !url.contains("youtu.be");
    }

}
