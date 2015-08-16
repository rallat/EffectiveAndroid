package com.israelferrer.effectiveandroid.util.image;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by icamacho on 8/22/15.
 */
public class PicassoImageLoader implements ImageLoader {
    private final Picasso picasso;

    public PicassoImageLoader(Context context) {
        this.picasso = Picasso.with(context);
    }

    @Override
    public void load(ImageView view, String url) {
        picasso.load(url).into(view);
    }

    @Override
    public void loadWithTransformation(ImageView view, String url, Transformation transformation) {
        picasso.load(url).transform(transformation).into(view);
    }
}
