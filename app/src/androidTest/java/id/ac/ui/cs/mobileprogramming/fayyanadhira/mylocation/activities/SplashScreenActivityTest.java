package id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.activities;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.R;
import id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.activity.SplashScreenActivity;

import static org.junit.Assert.*;

public class SplashScreenActivityTest {

    @Rule
    public ActivityTestRule<SplashScreenActivity> splashActivityActivityTestRule= new ActivityTestRule<SplashScreenActivity>(SplashScreenActivity.class);

    private SplashScreenActivity splashActivity = null;

    @Before
    public void setUp() throws Exception {
        splashActivity = splashActivityActivityTestRule.getActivity();
    }

    @Test
    public void launchSplashScreen() {
        assertNotNull(splashActivity.findViewById(R.id.imageView));
    }

    @After
    public void tearDown() throws Exception {
        splashActivity = null;
    }
}
