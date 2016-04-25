package cmu.sv.flubber.ihere.ws.local;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by zhengyiwang on 4/13/16.
 */

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    int mStartMode;
    IBinder mBinder;
    private String phoneNumber;
    private GoogleApiClient mGoogleApiClient;
    String formattedDate;
    public static final String TAG = LocationService.class.getSimpleName();

    public LocationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mGoogleApiClient.connect();
        phoneNumber = intent.getExtras().getString("phone");
        Log.i(TAG, phoneNumber);
        return mStartMode;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            Log.i(TAG, "Looks like location is not able to get.");
        }
        else {
            String locationMessage = Double.toString(location.getLatitude()) + "\n"
                    + Double.toString(location.getLongitude());
            Log.i(TAG, locationMessage);
            Intent retIntent = new Intent();
            retIntent.putExtra("locationValue", formattedDate);
            retIntent.setAction("locationRet");
            sendBroadcast(retIntent);
        }
    }

    private void sendSMS(String location){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, location, null, null);
        Log.i(TAG, "Location SMS sent.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed.");
    }
}
