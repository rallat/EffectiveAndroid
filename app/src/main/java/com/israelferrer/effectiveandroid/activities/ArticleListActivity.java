package com.israelferrer.effectiveandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.israelferrer.effectiveandroid.service.CustomApiClient;
import com.israelferrer.effectiveandroid.R;
import com.israelferrer.effectiveandroid.models.Article;
import com.israelferrer.effectiveandroid.recycler.ArticleRecyclerView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.UrlEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleListActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArticleRecyclerView adapter;
    private GestureDetectorCompat gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);


        // allows for optimizations if all items are of the same size:
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(this);
        gestureDetector =
                new GestureDetectorCompat(this, new RecyclerViewDemoOnGestureListener());
        new CustomApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession())
                .getTimelineService()
                .homeTimeline(200, true, true, true, true, new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        final List<Article> items = new ArrayList<>();
                        for (Tweet tweet : result.data) {
                            if (tweet.entities.urls.size() > 0 && isEligibleDomain(tweet.entities
                                    .urls)) {
                                items.add(Article.create(tweet));
                            }
                        }
                        Collections.sort(items);
                        adapter = new ArticleRecyclerView(items);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        logout();
                    }
                });
    }

    private boolean isEligibleDomain(List<UrlEntity> urls) {
        final String url = urls.get(0).expandedUrl;
        return !url.contains("twitter.com") && !url.contains("goo.gl") &&
                !url.contains("vine.co") && !url.contains("vimeo.com") &&
                !url.contains("youtube.com") && !url.contains("youtu.be");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            onClick(view);
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
        }
    }

    private void onClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        Article data = adapter.getItem(position);
        View innerContainer = view.findViewById(R.id.container_inner_item);
        Intent startIntent = new Intent(this, ArticleActivity.class);
        startIntent.putExtra("article", data);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, innerContainer, "TITLE");
        ActivityCompat.startActivity(this, startIntent, options.toBundle());
    }

}
