package com.israelferrer.effectiveandroid.util.image;

import android.widget.ImageView;

import com.squareup.picasso.Transformation;

/**
 * Created by icamacho on 8/22/15.
 */
public interface ImageLoader {
    void load(ImageView view, String url);

    void loadWithTransformation(ImageView view, String url, Transformation transformation);
}
