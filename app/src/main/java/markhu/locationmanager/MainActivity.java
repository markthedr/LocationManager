package markhu.locationmanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static java.lang.Math.round;

public class MainActivity extends ActionBarActivity {

    //minimum time and minimum location requests

    private final static int MIN_TIME = 50;
    private final static int MIN_DIST = 100;

    private LocationManager mlm;
    private TextView mLocationText;
    private LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocationText = (TextView) findViewById(R.id.tvLocation);
        mlm = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //when location changed change text
                String textToSet = "Location is long: " + round(location.getLongitude()) + " and lat: "
                        + round(location.getLatitude());
                mLocationText.setText(textToSet);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, mLocationListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlm.removeUpdates(mLocationListener);
    }


}
