package com.israelferrer.effectiveandroid.presenters;

import com.israelferrer.effectiveandroid.entities.Article;
import com.israelferrer.effectiveandroid.interactors.GetTopArticles;
import com.israelferrer.effectiveandroid.interactors.GetTopArticlesImpl;
import com.israelferrer.effectiveandroid.ui.views.TopArticleListView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icamacho on 8/15/15.
 */
public class TopArticleListPresenterImpl implements TopArticleListPresenter {
    private TopArticleListView view;
    private final GetTopArticles useCase;
    private List<Article> viewArticles;

    public TopArticleListPresenterImpl(TopArticleListView view) {
        this(view, new GetTopArticlesImpl());
    }

    TopArticleListPresenterImpl(TopArticleListView view, GetTopArticles useCase) {
        this.view = view;
        this.useCase = useCase;
    }

    @Override
    public void create() {
        if (viewArticles == null || viewArticles.size() == 0) {
            viewArticles = new ArrayList<>();
            useCase.execute(new Callback<List<Article>>() {
                @Override
                public void success(Result<List<Article>> result) {
                    viewArticles.addAll(result.data);
                    setArticles(viewArticles);
                }

                @Override
                public void failure(TwitterException e) {
                    if (view != null) {
                        view.logout();
                    }
                }
            });
        } else {
            setArticles(viewArticles);
        }
    }


    @Override
    public void setView(TopArticleListView view) {
        this.view = view;
    }

    private void setArticles(List<Article> articles) {
        if (view != null) {
            view.setArticles(articles);
        }
    }
}
