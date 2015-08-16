package com.israelferrer.effectiveandroid.ui.activities;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.israelferrer.effectiveandroid.R;
import com.israelferrer.effectiveandroid.entities.Image;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icamacho on 8/19/15.
 */
public class ImageActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE = "image";
    public static final String TRANSITION_SHARED_ELEMENT = "content";

    @Bind(R.id.cv)
    CardView cardView;
    @Bind(R.id.title)
    TextView titleView;
    @Bind(R.id.retweet)
    TextView retweetView;
    @Bind(R.id.media)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        Image image = extras.getParcelable(EXTRA_IMAGE);

        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        View innerContainer = cardView.findViewById(R.id.container_inner_item);
        ViewCompat.setTransitionName(innerContainer, TRANSITION_SHARED_ELEMENT);
        if (image != null) {
            Picasso.with(this).load(image.getMediaUrl()).into(imageView);
            retweetView.setText(image.getRetweetCount());
            titleView.setText(image.getTitle());
        } else {
            finish();
        }
    }
}
