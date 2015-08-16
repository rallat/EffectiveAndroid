package com.israelferrer.effectiveandroid.presenters;

import com.israelferrer.effectiveandroid.ui.views.TopImagesListView;

/**
 * Created by icamacho on 8/19/15.
 */
public interface TopImagesListPresenter extends Presenter {
    void create();

    void setView(TopImagesListView view);
}

