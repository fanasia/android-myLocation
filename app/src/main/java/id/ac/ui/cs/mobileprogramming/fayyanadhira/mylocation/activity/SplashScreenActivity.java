package id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.R;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler ssHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Fabric.with(this, new Crashlytics());

        ssHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // pindah activity
                try {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);

                    // tidak muncul lagi ketika di klik back
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        ssHandler.removeCallbacksAndMessages(null);
    }
}
