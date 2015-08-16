package com.israelferrer.effectiveandroid.models;

import com.israelferrer.effectiveandroid.entities.Image;
import com.twitter.sdk.android.core.Callback;

import java.util.List;

/**
 * Created by icamacho on 8/19/15.
 */
public interface TopImageListModel {
    void getMostRtImages(Callback<List<Image>> articles);
}
