package cmu.sv.flubber.ihere.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import cmu.sv.flubber.ihere.R;
import cmu.sv.flubber.ihere.entities.ITag;
import cmu.sv.flubber.ihere.entities.User;
import cmu.sv.flubber.ihere.ws.remote.RemoteItag;
import cmu.sv.flubber.ihere.ws.remote.RemoteUser;

public class CreateActivity extends HomeActivity {


    EditText editText;


    LocationManager mLocationManager;
    String latitude;
    String longitude;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button createTagButton = (Button)findViewById(R.id.btn_create_tag);
        createTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createButton();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        try {

            mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Location loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc == null)
                loc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (loc != null) {
                latitude = String.valueOf(loc.getLatitude());
                longitude = String.valueOf(loc.getLongitude());
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                    0, mLocationListener);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                    0, mLocationListener);
        } catch (SecurityException ex) {
            Toast.makeText(getBaseContext(), "Cannot get location info!", Toast.LENGTH_LONG).show();
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
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

    @Override
    public void onPause() {
        super.onPause();
        try {
            mLocationManager.removeUpdates(mLocationListener);
        } catch (SecurityException ex) {
            Toast.makeText(getBaseContext(), "Security Exception!", Toast.LENGTH_LONG).show();
        }
    }

    public void createButton() {
        editText = (EditText)findViewById(R.id.create_tag);
        String content = editText.getText().toString();


        progressDialog = new ProgressDialog(CreateActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new CreateTask().execute(content);
    }

    private class CreateTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... strings) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int userid = settings.getInt("userid", -1);
            Calendar calander = Calendar.getInstance();
            Date date = calander.getTime();
            ITag tag = new ITag(userid, strings[0], Float.parseFloat(longitude), date, Float.parseFloat(latitude), null);
            Boolean res = RemoteItag.createItag(tag);
            return res;
        }

        protected void onPostExecute(Boolean res) {
            if(res)
            {
                progressDialog.dismiss();
                Toast.makeText(getBaseContext(), "ITag created!", Toast.LENGTH_LONG).show();
                createSuccess();
            }
            else
            {
                progressDialog.dismiss();
                Toast.makeText(getBaseContext(), "Cannot create ITAG!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void createSuccess() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, HomeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
