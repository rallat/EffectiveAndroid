package com.israelferrer.effectiveandroid.interactors;

import com.israelferrer.effectiveandroid.entities.Article;
import com.twitter.sdk.android.core.Callback;

import java.util.List;

/**
 * Created by icamacho on 8/23/15.
 */
public interface GetTopArticles {
    void execute(Callback<List<Article>> articles);
}
