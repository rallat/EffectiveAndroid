package com.israelferrer.effectiveandroid.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.israelferrer.effectiveandroid.PresenterHolder;
import com.israelferrer.effectiveandroid.R;
import com.israelferrer.effectiveandroid.entities.Image;
import com.israelferrer.effectiveandroid.presenters.TopImagesListPresenter;
import com.israelferrer.effectiveandroid.presenters.TopImagesListPresenterImpl;
import com.israelferrer.effectiveandroid.ui.recycler.ImageRecyclerView;
import com.israelferrer.effectiveandroid.ui.views.TopImagesListView;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.List;

import butterknife.Bind;

public class TopImagesListActivity extends EffectiveActivity implements RecyclerView
        .OnItemTouchListener, TopImagesListView {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImageRecyclerView adapter;
    private GestureDetectorCompat gestureDetector;
    private TopImagesListPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(this);
        gestureDetector =
                new GestureDetectorCompat(this, new RecyclerViewDemoOnGestureListener());

        presenter = createPresenter();
        presenter.create();
    }

    @NonNull
    @Override
    Integer getLayout() {
        return R.layout.activity_article_list;
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        presenter.setView(null);
        PresenterHolder.getInstance().putPresenter(TopImagesListPresenter.class, presenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.isFinishing()) {
            PresenterHolder.getInstance().remove(TopImagesListPresenter.class);
        }
    }

    public TopImagesListPresenter createPresenter() {
        TopImagesListPresenter presenter = PresenterHolder.getInstance().
                getPresenter(TopImagesListPresenter.class);
        if (presenter != null) {
            presenter.setView(this);
        } else {
            presenter = new TopImagesListPresenterImpl(this);
        }
        return presenter;
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
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void setImage(List<Image> images) {
        adapter = new ImageRecyclerView(images);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void logout() {
        TwitterCore.getInstance().logOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private class RecyclerViewDemoOnGestureListener extends
            GestureDetector.SimpleOnGestureListener {
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
        Image data = adapter.getItem(position);
        View innerContainer = view.findViewById(R.id.container_inner_item);
        Intent startIntent = new Intent(this, ImageActivity.class);
        startIntent.putExtra(ImageActivity.EXTRA_IMAGE, data);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, innerContainer,
                        ImageActivity.TRANSITION_SHARED_ELEMENT);
        ActivityCompat.startActivity(this, startIntent, options.toBundle());
    }
}
