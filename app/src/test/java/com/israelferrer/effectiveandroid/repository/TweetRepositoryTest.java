package com.israelferrer.effectiveandroid.repository;

import com.israelferrer.effectiveandroid.BuildConfig;
import com.israelferrer.effectiveandroid.entities.Article;
import com.israelferrer.effectiveandroid.service.CustomApiClient;
import com.israelferrer.effectiveandroid.service.TimelineService;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by icamacho on 8/23/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TweetRepositoryTest {
    private static final TwitterException ANY_EXCEPTION = new TwitterException("Random Exception");

    @Mock
    private CustomApiClient client;
    @Mock
    private Callback<List<Tweet>> callback;
    @Mock
    private TimelineService timelineService;
    @Captor
    private ArgumentCaptor<Callback<List<Tweet>>> callbackArgumentCaptor;
    @Captor
    private ArgumentCaptor<Result<List<Article>>> articleArgumentCaptor;
    private TweetRepository model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(client.getTimelineService()).thenReturn(timelineService);
        model = new TweetRepositoryImpl(client);
    }

    @Test
    public void testGetMostRtArticles_failure() throws Exception {
        model.getTimeline(callback);
        verify(timelineService).homeTimeline(eq(200), eq(true), eq(true), eq(true), eq(true),
                callbackArgumentCaptor.capture());
        Callback<List<Tweet>> callbackApi = callbackArgumentCaptor.getValue();
        callbackApi.failure(ANY_EXCEPTION);
        verify(callback).failure(ANY_EXCEPTION);
    }

    @Test
    public void testGetMostRtArticles_success() throws Exception {
        model.getTimeline(callback);
        verify(timelineService).homeTimeline(eq(200), eq(true), eq(true), eq(true), eq(true),
                callbackArgumentCaptor.capture());
        Callback<List<Tweet>> callbackApi = callbackArgumentCaptor.getValue();
        Result<List<Tweet>> result= new Result<List<Tweet>>(new ArrayList<Tweet>() , null);
        callbackApi.success(result);
        verify(callback).success(result);
    }

}
