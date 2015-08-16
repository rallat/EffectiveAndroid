package com.israelferrer.effectiveandroid.presenters;

import com.israelferrer.effectiveandroid.ui.views.TopArticleListView;

/**
 * Created by icamacho on 8/15/15.
 */
public interface TopArticleListPresenter extends Presenter {
    void create();

    void setView(TopArticleListView view);
}
