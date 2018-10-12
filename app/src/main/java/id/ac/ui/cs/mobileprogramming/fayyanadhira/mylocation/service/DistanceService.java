package id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.Random;

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
        String curr_longi = intent.getStringExtra("curr_longi");
        String curr_latti = intent.getStringExtra("curr_latti");

        Double current_longi = Double.parseDouble(curr_longi.split(" ")[1]);
        Double current_latti = Double.parseDouble(curr_latti.split(" ")[1]);

        int[] options = {1,2,3};
        int rand = getRandom(options);

        String result = "";
        Bundle bundle = new Bundle();

        switch(rand) {
            case 1:
                double distanceParis = getDistanceFromLatLonInKm(current_latti, current_longi, 48.84, 2.35);
                result = "You are " + String.format("%.2f", distanceParis) + " km away from Paris!";
                break;
            case 2:
                double distanceLondon = getDistanceFromLatLonInKm(current_latti, current_longi, 51.50, 0.12);
                result = "London is just " + String.format("%.2f", distanceLondon) + " km away from you!";
                break;
            case 3:
                double distanceNewYork = getDistanceFromLatLonInKm(current_latti, current_longi, 40.71, 74.00);
                result = String.format("%.2f", distanceNewYork) + " km is the distance between you and New York!";
                break;
        }

        try {
            Thread.sleep(5000);
            bundle.putString("result", result);
            receiver.send(SUCCESS_CODE, bundle);
        } catch (Exception e) {
            receiver.send(ERROR_CODE, Bundle.EMPTY);
            e.printStackTrace();
        }
    }

    public double getDistanceFromLatLonInKm(double lat1, double lon1,double lat2, double lon2) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return d;
    }

    public double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    public static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
