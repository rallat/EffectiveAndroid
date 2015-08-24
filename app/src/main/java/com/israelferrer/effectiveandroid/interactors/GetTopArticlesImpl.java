package com.israelferrer.effectiveandroid.interactors;

import android.support.annotation.NonNull;

import com.israelferrer.effectiveandroid.entities.Article;
import com.israelferrer.effectiveandroid.repository.TweetRepository;
import com.israelferrer.effectiveandroid.repository.TweetRepositoryImpl;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.UrlEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by icamacho on 8/23/15.
 */
public class GetTopArticlesImpl implements GetTopArticles {
    private final TweetRepository tweetRepository;

    public GetTopArticlesImpl() {
        this(new TweetRepositoryImpl(
                TwitterCore.getInstance().getSessionManager().getActiveSession()));
    }

    GetTopArticlesImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public void execute(final Callback<List<Article>> callback) {
        tweetRepository.getTimeline(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                final List<Article> items = processTweets(result);
                callback.success(items, null);
            }

            @Override
            public void failure(TwitterException e) {
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
