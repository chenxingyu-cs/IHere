package cmu.sv.flubber.ihere.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;

import cmu.sv.flubber.ihere.R;
import cmu.sv.flubber.ihere.ws.local.LocationService;

public class PreviewActivity extends AppCompatActivity
        implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.PictureCallback, SensorEventListener {

    Camera mCamera;
    SurfaceView mPreview;
    LocationManager mLocationManager;
    // device sensor manager
    private SensorManager mSensorManager;


    // TODO: The data we need
    String latitude;
    String longitude;
    float currentDegree;


    TextView tvHeading;
    TextView tvLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        mPreview = (SurfaceView)findViewById(R.id.preview);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mCamera = Camera.open();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        tvHeading = (TextView) findViewById(R.id.headingText);
        tvLocation = (TextView) findViewById(R.id.locationText);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null)
            loc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (loc != null) {
            latitude = String.valueOf(loc.getLatitude());
            longitude = String.valueOf(loc.getLongitude());
            String text  = "latitude: " + latitude + "\nlongitude: " + longitude;
            tvLocation.setText(text);
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                0, mLocationListener);

    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();

        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCamera.release();
        Log.d("CAMERA","Destroy");
    }

    public void onCancelClick(View v) {
        finish();
    }

    public void onSnapClick(View v) {
        //mCamera.takePicture(this, null, null, this);
        Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
        Intent locationServiceIntent = new Intent(this, LocationService.class);
        startService(locationServiceIntent);
        stopService(locationServiceIntent);
    }

    @Override
    public void onShutter() {
        // TODO: click button to send location
        Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        //Here, we chose internal storage
        try {
            FileOutputStream out = openFileOutput("picture.jpg", Activity.MODE_PRIVATE);
            out.write(data);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width,selected.height);
        mCamera.setParameters(params);

        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW","surfaceDestroyed");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        currentDegree = Math.round(event.values[0]);

        tvHeading.setText("\n\nHeading: " + Float.toString(currentDegree) + " degrees");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }



    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
            Toast.makeText(PreviewActivity.this, "onLocationChanged",
                    Toast.LENGTH_SHORT).show();
            String text  = "latitude: " + latitude + "\nlongitude: " + longitude;
            tvLocation.setText(text);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(PreviewActivity.this, provider + "'s status changed to "+status +"!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(PreviewActivity.this, "Provider " + provider + " enabled!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(PreviewActivity.this, "Provider " + provider + " disabled!",
                    Toast.LENGTH_SHORT).show();
        }
    };


}