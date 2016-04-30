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
    TextView tag1;
    TextView tag2;
    TextView tag3;
    ArrayList<TextView > viewArrayList;
    ArrayList<ITag> iTagArrayList;
    DiscoverAdapter discoverAdapter;


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

        initAdapter();

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
        mLocationManager.removeUpdates(mLocationListener);
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
        Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
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



    private void initAdapter(){
        viewArrayList = new ArrayList<>();
        /*
        tag1 = (TextView) findViewById(R.id.test1);
        tag2 = (TextView) findViewById(R.id.test2);
        tag3 = (TextView) findViewById(R.id.test3);


        viewArrayList.add(tag1);

        viewArrayList.add(tag2);

        viewArrayList.add(tag3);

        */

        //get the list of textview
        int start = R.id.test1;
        int end = R.id.test3;

        for(int i = start; i <= end; i++){
            viewArrayList.add((TextView) findViewById(i));

        }




        //async task to get list of iTagArrayList from server
        //TODO
        iTagArrayList = new ArrayList<>();
        iTagArrayList.add(new ITag("this is the only one"));

        //TODO get list of itag from server
        //iTagArrayList = RemoteItag.discoverItags("100", "100","100");


        //use adapter for dispay
        discoverAdapter = new DiscoverAdapter(viewArrayList, iTagArrayList);
    }

}
