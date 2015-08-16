package com.israelferrer.effectiveandroid.ui.recycler;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.israelferrer.effectiveandroid.R;
import com.israelferrer.effectiveandroid.entities.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icamacho on 8/19/15.
 */
public class ImageRecyclerView extends RecyclerView.Adapter<ImageRecyclerView.ImageViewHolder> {
    private List<Image> items;

    public ImageRecyclerView(List<Image> items) {
        if (items == null) {
            throw new NullPointerException(
                    "items must not be null");
        }
        this.items = items;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.image_layout,
                        parent,
                        false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final Image image = items.get(position);
        holder.retweet.setText(image.getRetweetCount());
        if (!TextUtils.isEmpty(image.getMediaUrl())) {
            holder.media.setVisibility(View.VISIBLE);
            Picasso.with(holder.media.getContext()).load(image.getMediaUrl()).into(holder.media);
        } else {
            holder.media.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Image getItem(int position) {
        return items.get(position);
    }

    final static class ImageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.retweet)
        TextView retweet;
        @Bind(R.id.media)
        ImageView media;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
