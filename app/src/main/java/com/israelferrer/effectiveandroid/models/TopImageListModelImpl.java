package com.israelferrer.effectiveandroid.models;

import com.israelferrer.effectiveandroid.entities.Image;
import com.israelferrer.effectiveandroid.service.CustomApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by icamacho on 8/19/15.
 */
public class TopImageListModelImpl implements TopImageListModel {
    private final CustomApiClient client;

    public TopImageListModelImpl(CustomApiClient client) {
        this.client = client;
    }

    public TopImageListModelImpl(TwitterSession activeSession) {
        this(new CustomApiClient(activeSession));
    }

    @Override
    public void getMostRtImages(final Callback<List<Image>> callback) {
        client.getTimelineService().homeTimeline(200, true, true, true, true,
                new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        final List<Image> items = processTweets(result);
                        callback.success(items, null);

                    }

                    @Override
                    public void failure(TwitterException e) {
                        callback.failure(e);
                    }
                });
    }

    private List<Image> processTweets(Result<List<Tweet>> result) {
        final List<Image> items = new ArrayList<>();
        for (Tweet tweet : result.data) {
            if (tweet.entities != null && tweet.entities.media != null &&
                    tweet.entities.media.size() > 0 &&
                    isElegibleImage(tweet.entities.media)) {
                items.add(Image.create(tweet));
            }
        }
        Collections.sort(items);
        return items;
    }

    private boolean isElegibleImage(List<MediaEntity> mediaEntities) {
        if (mediaEntities == null || mediaEntities.get(0) == null || !mediaEntities.get(0).type
                .equals("photo")) {
            return false;
        }
        return true;
    }
}
