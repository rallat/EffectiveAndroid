package com.israelferrer.effectiveandroid.presenters;

import com.israelferrer.effectiveandroid.entities.Image;
import com.israelferrer.effectiveandroid.models.TopImageListModel;
import com.israelferrer.effectiveandroid.models.TopImageListModelImpl;
import com.israelferrer.effectiveandroid.ui.views.TopImagesListView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icamacho on 8/19/15.
 */
public class TopImagesListPresenterImpl implements TopImagesListPresenter {
    private final TopImageListModel model;

    private TopImagesListView view;
    private List<Image> viewArticles;

    public TopImagesListPresenterImpl(TopImagesListView view) {
        this.view = view;
        this.model = new TopImageListModelImpl(
                TwitterCore.getInstance().getSessionManager().getActiveSession());
    }

    @Override
    public void create() {
        if (viewArticles == null) {
            viewArticles = new ArrayList<>();
            model.getMostRtImages(new Callback<List<Image>>() {
                @Override
                public void success(Result<List<Image>> result) {
                    viewArticles.addAll(result.data);
                    view.setImage(viewArticles);
                }

                @Override
                public void failure(TwitterException e) {
                    view.logout();
                }
            });
        } else {
            view.setImage(viewArticles);
        }
    }

    @Override
    public void setView(TopImagesListView view) {
        this.view = view;
    }
}
