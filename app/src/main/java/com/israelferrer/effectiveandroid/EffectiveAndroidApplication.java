package com.israelferrer.effectiveandroid;

import android.app.Application;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by icamacho on 8/6/15.
 */
public class EffectiveAndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setUpFabric();
    }

    public void setUpFabric() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.CONSUMER_KEY,
                BuildConfig.CONSUMER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig));
    }
}
