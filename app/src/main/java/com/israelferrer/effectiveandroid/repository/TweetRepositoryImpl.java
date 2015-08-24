package com.israelferrer.effectiveandroid.repository;

import android.util.Log;

import com.israelferrer.effectiveandroid.service.CustomApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by icamacho on 8/23/15.
 */
public class TweetRepositoryImpl implements TweetRepository {
    private final CustomApiClient client;

    public TweetRepositoryImpl(TwitterSession session) {
        this.client = new CustomApiClient(session);
    }

    TweetRepositoryImpl(CustomApiClient client) {
        this.client = client;
    }

    @Override
    public void getTimeline(final Callback<List<Tweet>> callback) {
        client.getTimelineService().homeTimeline(200, true, true, true, true,
                new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        callback.success(result);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        Log.d("API error", e.getMessage());
                        callback.failure(e);
                    }
                });
    }
}
