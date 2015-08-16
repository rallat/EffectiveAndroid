package com.israelferrer.effectiveandroid.models;

import com.israelferrer.effectiveandroid.entities.Article;
import com.twitter.sdk.android.core.Callback;

import java.util.List;

/**
 * Created by icamacho on 8/15/15.
 */
public interface TopArticleListModel {
    void getMostRtArticles(Callback<List<Article>> articles);
}
