package com.israelferrer.effectiveandroid.ui.recycler;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.israelferrer.effectiveandroid.R;
import com.israelferrer.effectiveandroid.entities.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icamacho on 8/9/15.
 */
public class ArticleRecyclerView extends RecyclerView.Adapter<ArticleRecyclerView.ArticleViewHolder> {
    private List<Article> items;

    public ArticleRecyclerView(List<Article> items) {
        if (items == null) {
            throw new NullPointerException(
                    "items must not be null");
        }
        this.items = items;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.article_layout,
                        parent,
                        false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        final Article article = items.get(position);
        holder.title.setText(article.getTitle());
        holder.retweet.setText(article.getRetweetCount());
        if (!TextUtils.isEmpty(article.getMediaUrl())) {
            holder.media.setVisibility(View.VISIBLE);
            Picasso.with(holder.media.getContext()).load(article.getMediaUrl()).into(holder.media);
        } else {
            holder.media.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Article getItem(int position) {
        return items.get(position);
    }

    final static class ArticleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.retweet)
        TextView retweet;
        @Bind(R.id.media)
        ImageView media;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
