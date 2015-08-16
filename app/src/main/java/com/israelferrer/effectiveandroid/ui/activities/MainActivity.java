package com.israelferrer.effectiveandroid.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.twitter.sdk.android.core.TwitterCore;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TwitterCore.getInstance().getSessionManager().getActiveSession() == null) {
            startLogin();
        } else {
            startApp();
        }
    }

    private void startLogin() {
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void startApp() {
        finish();
        startActivity(new Intent(this, TopArticleListActivity.class));
    }
}
