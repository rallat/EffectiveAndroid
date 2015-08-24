package com.israelferrer.effectiveandroid.presenters;

import com.israelferrer.effectiveandroid.BuildConfig;
import com.israelferrer.effectiveandroid.entities.Article;
import com.israelferrer.effectiveandroid.interactors.GetTopArticles;
import com.israelferrer.effectiveandroid.ui.views.TopArticleListView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by icamacho on 8/15/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TopArticleListPresenterTest {
    private static final Result<List<Article>> ANY_RESULT =
            new Result<>(Collections.<Article>emptyList(), null);
    private static final TwitterException ANY_EXCEPTION = new TwitterException("Random Exception");

    private ArgumentCaptor<Callback> callbackCaptor;
    private TopArticleListPresenterImpl presenter;
    private GetTopArticles useCase;
    private TopArticleListView view;

    @Before
    public void setUp() throws Exception {
        view = mock(TopArticleListView.class);
        useCase = mock(GetTopArticles.class);
        presenter = new TopArticleListPresenterImpl(view, useCase);
        callbackCaptor = ArgumentCaptor.forClass(Callback.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreate_success() throws Exception {
        presenter.create();

        verify(useCase).execute(callbackCaptor.capture());
        Callback<List<Article>> callback = callbackCaptor.getValue();
        callback.success(ANY_RESULT);
        verify(view).setArticles(ANY_RESULT.data);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreate_successNullView() throws Exception {
        presenter.create();
        presenter.setView(null);
        verify(useCase).execute(callbackCaptor.capture());
        Callback<List<Article>> callback = callbackCaptor.getValue();
        callback.success(ANY_RESULT);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreate_failure() throws Exception {
        presenter.create();

        verify(useCase).execute(callbackCaptor.capture());
        Callback<List<Article>> callback = callbackCaptor.getValue();
        callback.failure(ANY_EXCEPTION);
        verify(view).logout();
    }
}
