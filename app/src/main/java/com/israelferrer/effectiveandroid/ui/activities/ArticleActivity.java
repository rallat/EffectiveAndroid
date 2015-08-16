package com.israelferrer.effectiveandroid.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.israelferrer.effectiveandroid.R;
import com.israelferrer.effectiveandroid.entities.Article;
import com.twitter.sdk.android.core.TwitterCore;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {
    public static final String EXTRA_ARTICLE = "article";
    public static final String TRANSITION_SHARED_ELEMENT = "title";

    @Bind(R.id.cv)
    CardView cardView;
    @Bind(R.id.title)
    TextView titleView;
    @Bind(R.id.retweet)
    TextView retweetView;
    @Bind(R.id.webview)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        Article article = extras.getParcelable(EXTRA_ARTICLE);

        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        View innerContainer = cardView.findViewById(R.id.container_inner_item);
        ViewCompat.setTransitionName(innerContainer, TRANSITION_SHARED_ELEMENT);
        if (article != null) {
            titleView.setText(article.getTitle());
            retweetView.setText(article.getRetweetCount());
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(article.getUrl());
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        TwitterCore.getInstance().logOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
