package com.israelferrer.effectiveandroid.ui;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.israelferrer.effectiveandroid.R;
import com.israelferrer.effectiveandroid.entities.Profile;
import com.israelferrer.effectiveandroid.presenters.ScreenContainerPresenter;
import com.israelferrer.effectiveandroid.presenters.ScreenContainerPresenterImpl;
import com.israelferrer.effectiveandroid.ui.activities.TopArticleListActivity;
import com.israelferrer.effectiveandroid.ui.activities.TopImagesListActivity;
import com.israelferrer.effectiveandroid.ui.views.ScreenContainerView;
import com.israelferrer.effectiveandroid.util.image.CircleTransform;
import com.israelferrer.effectiveandroid.util.image.ImageLoader;
import com.israelferrer.effectiveandroid.util.image.PicassoImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icamacho on 8/20/15.
 */
public class ScreenContainerImpl implements ScreenContainer, ScreenContainerView {
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.activity_content)
    ViewGroup container;
    TextView usernameView;
    private ImageView userProfileView;
    private ImageLoader imageLoader;
    private ScreenContainerPresenter presenter;

    @VisibleForTesting
    public ScreenContainerImpl(ScreenContainerPresenter presenter) {
        this.presenter = presenter;
    }
    public ScreenContainerImpl(){
        presenter = new ScreenContainerPresenterImpl(this);
    }

    @Override
    public ViewGroup bind(AppCompatActivity activity) {
        imageLoader = new PicassoImageLoader(activity);
        activity.setContentView(R.layout.base_activity);
        ButterKnife.bind(this, activity);
        setupDrawerLayout(activity);
        initToolbar(activity);
        presenter.loadUser();
        return container;
    }

    @Override
    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    private void setupDrawerLayout(final AppCompatActivity activity) {
        usernameView = (TextView) navigationView.findViewById(R.id.username);
        userProfileView = (ImageView) navigationView.findViewById(R.id.avatar);

        if (activity instanceof TopArticleListActivity) {
            navigationView.getMenu().getItem(0).setChecked(true);
        } else if (activity instanceof TopImagesListActivity) {
            navigationView.getMenu().getItem(1).setChecked(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent = null;
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.drawer_top_articles:
                        if (!(activity instanceof TopArticleListActivity)) {
                            intent = new Intent(activity, TopArticleListActivity.class);
                        }
                        break;
                    case R.id.drawer_top_images:
                        if (!(activity instanceof TopImagesListActivity)) {
                            intent = new Intent(activity, TopImagesListActivity.class);
                        }
                        break;
                }
                if (intent != null) {
                    activity.startActivity(intent);
                    activity.finish();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initToolbar(AppCompatActivity activity) {
        activity.setSupportActionBar(toolbar);
        final ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void showProfile(Profile profile) {
        usernameView.setText(profile.getHandle());
        imageLoader.loadWithTransformation(userProfileView, profile.getProfileImageUrl(),
                new CircleTransform());
    }
}
