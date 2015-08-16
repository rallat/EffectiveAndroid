package com.israelferrer.effectiveandroid.presenters;

import com.israelferrer.effectiveandroid.entities.Profile;
import com.israelferrer.effectiveandroid.models.ScreenContainerModel;
import com.israelferrer.effectiveandroid.models.ScreenContainerModelImpl;
import com.israelferrer.effectiveandroid.ui.views.ScreenContainerView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

/**
 * Created by icamacho on 8/23/15.
 */
public class ScreenContainerPresenterImpl implements ScreenContainerPresenter {

    private final ScreenContainerView view;
    private final ScreenContainerModel model;

    ScreenContainerPresenterImpl(ScreenContainerView view, ScreenContainerModel model) {
        this.view = view;
        this.model = model;
    }

    public ScreenContainerPresenterImpl(ScreenContainerView view) {
        this(view, new ScreenContainerModelImpl(
                TwitterCore.getInstance().getSessionManager().getActiveSession()));
    }

    @Override
    public void loadUser() {
        model.getUserProfile(new Callback<Profile>() {
            @Override
            public void success(Result<Profile> result) {
                view.showProfile(result.data);
            }

            @Override
            public void failure(TwitterException e) {

            }
        });
    }
}
