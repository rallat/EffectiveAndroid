package com.israelferrer.effectiveandroid.models;

import com.israelferrer.effectiveandroid.entities.Profile;
import com.twitter.sdk.android.core.Callback;

/**
 * Created by icamacho on 8/23/15.
 */
public interface ScreenContainerModel {
   void getUserProfile(Callback<Profile> callback);
}
