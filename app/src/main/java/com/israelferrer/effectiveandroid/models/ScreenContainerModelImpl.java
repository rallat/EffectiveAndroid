package com.israelferrer.effectiveandroid.models;

import com.israelferrer.effectiveandroid.entities.Profile;
import com.israelferrer.effectiveandroid.service.CustomApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by icamacho on 8/23/15.
 */
public class ScreenContainerModelImpl implements ScreenContainerModel {
    private final CustomApiClient client;

    public ScreenContainerModelImpl(TwitterSession session) {
        this.client = new CustomApiClient(session);
    }

    @Override
    public void getUserProfile(final Callback<Profile> callback) {
        client.userShow(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                callback.success(Profile.create(result.data),result.response);
            }

            @Override
            public void failure(TwitterException e) {

            }
        });
    }
}
