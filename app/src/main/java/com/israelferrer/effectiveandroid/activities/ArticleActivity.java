package com.israelferrer.effectiveandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.israelferrer.effectiveandroid.R;
import com.israelferrer.effectiveandroid.models.Article;
import com.twitter.sdk.android.core.TwitterCore;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {

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
        Article article = extras.getParcelable("article");

        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        View innerContainer = cardView.findViewById(R.id.container_inner_item);
        ViewCompat.setTransitionName(innerContainer, "TITLE");
        titleView.setText(article.getTitle());
        retweetView.setText(article.getRetweetCount());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(article.getUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
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
