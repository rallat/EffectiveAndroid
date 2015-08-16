package com.israelferrer.effectiveandroid.ui.activities;

import com.israelferrer.effectiveandroid.BuildConfig;
import com.israelferrer.effectiveandroid.presenters.TopArticleListPresenter;
import com.israelferrer.effectiveandroid.ui.ScreenContainer;
import com.israelferrer.effectiveandroid.ui.ScreenContainerImpl;
import com.israelferrer.effectiveandroid.presenters.ScreenContainerPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by icamacho on 8/15/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TopArticleListActivityTest {

    @Test
    public void testOnCreate() throws Exception {
        final TopArticleListPresenter presenter = mock(TopArticleListPresenter.class);
        TopArticleListActivity activity = new TopArticleListActivity() {
            @Override
            public TopArticleListPresenter createPresenter() {
                return presenter;
            }

            @Override
            ScreenContainer createScreenContainer() {
                return new ScreenContainerImpl(mock(ScreenContainerPresenter.class));
            }
        };
        ActivityController<TopArticleListActivity> ac = ActivityController.of(
                Robolectric.getShadowsAdapter(), activity);
        ac.create();
        verify(presenter).create();
    }
}
