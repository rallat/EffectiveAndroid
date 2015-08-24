package com.israelferrer.effectiveandroid.interactors;

import com.israelferrer.effectiveandroid.BuildConfig;
import com.israelferrer.effectiveandroid.entities.Article;
import com.israelferrer.effectiveandroid.repository.TweetRepository;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.core.models.TweetEntities;
import com.twitter.sdk.android.core.models.UrlEntity;
import com.twitter.sdk.android.core.models.User;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Created by icamacho on 8/15/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class GetTopArticlesTest {
    private static final TwitterException ANY_EXCEPTION = new TwitterException("Random Exception");
    private static final long TWEET_ID = 582295217115541505L;


    private GetTopArticlesImpl useCase;
    @Mock
    private TweetRepository model;
    @Mock
    private Callback<List<Article>> callback;
    @Captor
    private ArgumentCaptor<Callback> callbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<Result<List<Article>>> articleArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        useCase = new GetTopArticlesImpl(model);
    }

    @Test
    public void testGetMostRtArticles_failure() throws Exception {
        useCase.execute(callback);
        verify(model).getTimeline(callbackArgumentCaptor.capture());
        Callback<List<Tweet>> callbackApi = callbackArgumentCaptor.getValue();
        callbackApi.failure(ANY_EXCEPTION);
        verify(callback).failure(ANY_EXCEPTION);
    }

    @Test
    public void testGetMostRtArticles_success() throws Exception {
        useCase.execute(callback);
        verify(model).getTimeline(callbackArgumentCaptor.capture());
        Callback<List<Tweet>> callbackApi = callbackArgumentCaptor.getValue();
        callbackApi.success(new Result<>(createTweets(), null));
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(Article.create(createUrlTweet(TWEET_ID, null, "", "",
                "israelferrer.com")));
        verify(callback).success(articleArgumentCaptor.capture());
        Result<List<Article>> articles = articleArgumentCaptor.getValue();
        assertEquals(articles.data, expectedArticles);
    }

    private List<Tweet> createTweets() {
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(createUrlTweet(TWEET_ID, null, "", "", "twitter.com"));
        tweets.add(createUrlTweet(TWEET_ID, null, "", "", "israelferrer.com"));
        tweets.add(createUrlTweet(TWEET_ID, null, "", "", "vimeo.com"));
        tweets.add(createUrlTweet(TWEET_ID, null, "", "", "goo.gl"));
        tweets.add(createUrlTweet(TWEET_ID, null, "", "", "vine.co"));
        tweets.add(createUrlTweet(TWEET_ID, null, "", "", "youtube.com"));
        tweets.add(createUrlTweet(TWEET_ID, null, "", "", "youtu.be"));
        tweets.add(createUrlTweet(TWEET_ID, null, "", "", null));
        return tweets;
    }

    static Tweet createUrlTweet(long id, User user, String text, String timestamp,
                                String url) {

        final TweetEntities entities = new TweetEntities(newUrlEntityList(url), null, null, null);
        return new TweetBuilder()
                .setId(id)
                .setUser(user)
                .setText(text)
                .setCreatedAt(timestamp)
                .setEntities(entities)
                .build();
    }

    public static List<UrlEntity> newUrlEntityList(String url) {
        List<UrlEntity> list = new ArrayList<>();
        list.add(new UrlEntity("url", url, "displayUrl", 0, 0));
        return list;
    }
}