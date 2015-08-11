package com.israelferrer.effectiveandroid.service;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by icamacho on 8/9/15.
 */
public interface TimelineService {
    @GET("/1.1/statuses/home_timeline.json")
    void homeTimeline(@Query("count") Integer var1, @Query("trim_user") Boolean var4, @Query
            ("exclude_replies") Boolean var5, @Query("contributor_details") Boolean var6,
                      @Query("include_entities") Boolean var7, Callback<List<Tweet>> var8);

}
