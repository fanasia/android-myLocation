package id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.R;
import id.ac.ui.cs.mobileprogramming.fayyanadhira.mylocation.service.DistanceService;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements LocationListener {
    static final int REQUEST_LOCATION = 1;

    LocationManager locationManager;

    @BindView(R.id.latitude) TextView latitude;
    @BindView(R.id.longitude) TextView longitude;

    DistanceReceiver distanceReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        distanceReceiver = new DistanceReceiver(new Handler());

        getLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }

    @OnClick(R.id.distance_button)
    public void startService() {
        Intent intent = new Intent(MainActivity.this, DistanceService.class);
        intent.putExtra("receiver", distanceReceiver);
        intent.putExtra("curr_longi", longitude.getText());
        intent.putExtra("curr_latti", latitude.getText());
        startService(intent);
    }

    void getLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null){
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                this.latitude.setText("Latitude: " + latti);
                this.longitude.setText("Longitude: " + longi);
            } else {
                this.latitude.setText("Unable to find correct location.");
                this.longitude.setText("Unable to find correct location. ");
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length > 0) {
                    // Validate the permissions result
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // All good
                    } else {
                        // Close your app
                        closeNow();
                    }
                }
                break;
        }
    }

    private void closeNow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        } else {
            finish();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double latti = location.getLatitude();
        double longi = location.getLongitude();

        this.latitude.setText("Latitude: " + latti);
        this.longitude.setText("Longitude: " + longi);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(this, "Enable new Provider" + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet",
                Toast.LENGTH_SHORT).show();
    }

    private class DistanceReceiver extends ResultReceiver {

        public DistanceReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            switch (resultCode) {
                case 0 :
                    Toast.makeText(getApplicationContext(), "Error in sleep", Toast.LENGTH_SHORT).show();
                    break;
                case 1 :
                    Toast.makeText(getApplicationContext(), resultData.getString("result"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.onReceiveResult(resultCode, resultData);
        }
    }
}