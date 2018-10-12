package id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class DistanceService extends IntentService {

    final int SUCCESS_CODE = 1;
    final int ERROR_CODE = 0;

    public DistanceService() {
        super("distance-service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String result = "Service has running for 10s";
        Bundle bundle = new Bundle();

        try {
            Thread.sleep(10000);
            bundle.putString("result", result);
            receiver.send(SUCCESS_CODE, bundle);
        } catch (Exception e) {
            receiver.send(ERROR_CODE, Bundle.EMPTY);
            e.printStackTrace();
        }
    }
}
