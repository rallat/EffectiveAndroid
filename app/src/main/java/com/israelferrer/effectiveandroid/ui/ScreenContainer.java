package com.israelferrer.effectiveandroid.ui;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

/**
 * Created by icamacho on 8/20/15.
 * Original https://github.com/JakeWharton/u2020/tree/master/src/main/java/com/jakewharton/u2020/ui
 */

/**
 * An indirection which allows controlling the root container used for each activity.
 */
public interface ScreenContainer {
    /**
     * The root {@link android.view.ViewGroup} into which the activity should place its contents.
     */
    ViewGroup bind(AppCompatActivity activity);

    /**
     * Returns the drawerLayout of this window.
     *
     */
    DrawerLayout getDrawerLayout();
}