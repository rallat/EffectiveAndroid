package com.israelferrer.effectiveandroid.ui.views;


import com.israelferrer.effectiveandroid.entities.Article;

import java.util.List;

public interface TopArticleListView {
    void setArticles(List<Article> articles);
    void logout();
}
