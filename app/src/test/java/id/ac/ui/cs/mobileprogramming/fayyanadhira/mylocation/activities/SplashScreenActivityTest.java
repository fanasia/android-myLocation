package id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.activities;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowLooper;

import id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.activity.MainActivity;
import id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.activity.SplashScreenActivity;

import static junit.framework.TestCase.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class SplashScreenActivityTest {

    @Test
    public void testSplash(){
        ActivityController<SplashScreenActivity> controller = Robolectric.buildActivity(SplashScreenActivity.class).create().start();
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

        SplashScreenActivity splashActivity = controller.get();
        Intent expectedIntent = new Intent(splashActivity, MainActivity.class);

        assertEquals(expectedIntent.getComponent(), shadowOf(splashActivity).getNextStartedActivity().getComponent());
    }
}
