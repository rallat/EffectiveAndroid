package com.israelferrer.effectiveandroid.service;

import com.israelferrer.effectiveandroid.service.TimelineService;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;

/**
 * Created by icamacho on 8/9/15.
 */
public class CustomApiClient extends TwitterApiClient {
    public CustomApiClient(Session session) {
        super(session);
    }

    public TimelineService getTimelineService() {
        return (TimelineService) this.getService(TimelineService.class);
    }
}
