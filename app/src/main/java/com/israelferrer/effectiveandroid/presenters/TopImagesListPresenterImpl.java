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
    private List<Image> viewImages;

    public TopImagesListPresenterImpl(TopImagesListView view) {
        this.view = view;
        this.model = new TopImageListModelImpl(
                TwitterCore.getInstance().getSessionManager().getActiveSession());
    }

    @Override
    public void create() {
        if (viewImages == null || viewImages.size() == 0) {
            viewImages = new ArrayList<>();
            model.getMostRtImages(new Callback<List<Image>>() {
                @Override
                public void success(Result<List<Image>> result) {
                    viewImages.addAll(result.data);
                    setImages(viewImages);
                }

                @Override
                public void failure(TwitterException e) {
                    if (view != null) {
                        view.logout();
                    }
                }
            });
        } else {
            setImages(viewImages);
        }
    }

    private void setImages(List<Image> articles) {
        if (view != null) {
            view.setImage(articles);
        }
    }

    @Override
    public void setView(TopImagesListView view) {
        this.view = view;
    }
}
