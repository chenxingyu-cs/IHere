package cmu.sv.flubber.ihere.ui;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import cmu.sv.flubber.ihere.R;
import cmu.sv.flubber.ihere.adapter.DiscoverAdapter;
import cmu.sv.flubber.ihere.entities.ITag;
import cmu.sv.flubber.ihere.ws.remote.RemoteItag;

public class DiscoveryActivity extends AppCompatActivity
        implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.PictureCallback, SensorEventListener {

    Camera mCamera;
    SurfaceView mPreview;
    LocationManager mLocationManager;
    // device sensor manager
    private SensorManager mSensorManager;


    String latitude;
    String longitude;
    float currentDegree;


    TextView tvHeading;
    TextView tvLocation;
    ArrayList<TextView > viewArrayList;
    ArrayList<ITag> iTagArrayList;
    DiscoverAdapter discoverAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

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

        //TODO need to test real time location
        new DiscoverTask().execute("100","100","100");
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
        // TODO: click button to send location
        //mCamera.takePicture(this, null, null, this);

        discoverAdapter.show();
        Toast.makeText(this, "Discovering...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShutter() {
        // MEI SHA YONG
        //Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        // MEI SHA YONG
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
            //Toast.makeText(DiscoveryActivity.this, "onLocationChanged",
              //      Toast.LENGTH_SHORT).show();
            String text  = "latitude: " + latitude + "\nlongitude: " + longitude;
            tvLocation.setText(text);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(DiscoveryActivity.this, provider + "'s status changed to "+status +"!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(DiscoveryActivity.this, "Provider " + provider + " enabled!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(DiscoveryActivity.this, "Provider " + provider + " disabled!",
                    Toast.LENGTH_SHORT).show();
        }
    };

    private class DiscoverTask extends AsyncTask< String, Integer, ArrayList<ITag>> {
        protected ArrayList<ITag> doInBackground(String... location) {

            iTagArrayList = RemoteItag.discoverItags(location[0], location[1], location[2]);
            return iTagArrayList;
        }

        protected void onPostExecute(ArrayList<ITag> iTagArrayListag) {
            if(iTagArrayListag == null || iTagArrayListag.size() == 0)
                ;

            else
                //use adapter for dispay
                initAdapter();
                discoverAdapter = new DiscoverAdapter(viewArrayList, iTagArrayList, DiscoveryActivity.this);

        }
    }

    private void initAdapter(){
        viewArrayList = new ArrayList<>();
        int start = R.id.test1;
        int end = R.id.test5;

        for(int i = start; i <= end; i++){
            viewArrayList.add((TextView) findViewById(i));

        }


    }

}
