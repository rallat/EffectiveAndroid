package com.israelferrer.effectiveandroid.service;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.User;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by icamacho on 8/20/15.
 */
public interface UserService {
    @GET("/1.1/users/show.json")
    void userShow(@Query("screen_name") String screenName,
                  @Query("include_entities") boolean includeEntities, Callback<User> callback);
}
