package com.israelferrer.effectiveandroid.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.israelferrer.effectiveandroid.ui.ScreenContainer;
import com.israelferrer.effectiveandroid.ui.ScreenContainerImpl;

import butterknife.ButterKnife;

/**
 * Created by icamacho on 8/20/15.
 */
public abstract class EffectiveActivity extends AppCompatActivity {
    private ViewGroup mainFrame;
    private ScreenContainer screenContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenContainer = createScreenContainer();
        mainFrame = screenContainer.bind(this);
        getLayoutInflater().inflate(getLayout(), mainFrame);
        ButterKnife.bind(this);
    }

    ScreenContainer createScreenContainer() {
        return new ScreenContainerImpl();
    }

    @NonNull
    abstract Integer getLayout();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                screenContainer.getDrawerLayout().openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
