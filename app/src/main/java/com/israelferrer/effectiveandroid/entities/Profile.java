package com.israelferrer.effectiveandroid.entities;

import com.twitter.sdk.android.core.models.User;

/**
 * Created by icamacho on 8/23/15.
 */
public class Profile {
    private final String handle;
    private final String profileImageUrl;

    public Profile(String handle, String profileImageUrl) {
        this.handle = handle;
        this.profileImageUrl = profileImageUrl;
    }

    public String getHandle() {
        return handle;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static Profile create(User data) {
        return new Profile("@" + data.screenName, data.profileImageUrl.replace("_normal", ""));
    }
}
