package com.israelferrer.effectiveandroid.repository;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by icamacho on 8/23/15.
 */
public interface TweetRepository {
    void getTimeline(final Callback<List<Tweet>> callback);
}
