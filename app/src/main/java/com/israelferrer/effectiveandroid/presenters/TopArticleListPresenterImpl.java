package com.israelferrer.effectiveandroid.presenters;

import com.israelferrer.effectiveandroid.entities.Article;
import com.israelferrer.effectiveandroid.models.TopArticleListModel;
import com.israelferrer.effectiveandroid.models.TopArticleListModelImpl;
import com.israelferrer.effectiveandroid.ui.views.TopArticleListView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icamacho on 8/15/15.
 */
public class TopArticleListPresenterImpl implements TopArticleListPresenter {
    private TopArticleListView view;
    private final TopArticleListModel model;
    private List<Article> viewArticles;

    public TopArticleListPresenterImpl(TopArticleListView view) {
        this(view, new TopArticleListModelImpl(TwitterCore.getInstance().getSessionManager().
                getActiveSession()));
    }

    TopArticleListPresenterImpl(TopArticleListView view, TopArticleListModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void create() {
        if (viewArticles == null) {
            viewArticles = new ArrayList<>();
            model.getMostRtArticles(new Callback<List<Article>>() {
                @Override
                public void success(Result<List<Article>> result) {
                    viewArticles.addAll(result.data);
                    view.setArticles(viewArticles);
                }

                @Override
                public void failure(TwitterException e) {
                    view.logout();
                }
            });
        } else {
            view.setArticles(viewArticles);
        }
    }

    @Override
    public void setView(TopArticleListView view) {
        this.view = view;
    }
}
