package com.mateoj.multiactivitydrawer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mateoj.multiactivitydrawer.Interfaces.GoogleClient;
import com.mateoj.multiactivitydrawer.POJO.Example;
import com.mateoj.multiactivitydrawer.POJO.Result;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    Button NearbyPlaces, QuickReport, Report, EmergencyContacts, LiveTraffic, rate, Info, Women, SOS;
    String strName;
    StringBuilder sb, sms;
    TextView Resp;
    List<Result> pavan;
    public GoogleMap mMap;
    Context context;
    private LocationRequest mLocationRequest;
    MarkerOptions markerOptions;

    double mLatitude = 19.9974;
    double mLongitude = 73.7876113;
    double mlat;
    double mlong;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    double longitude, latitude;
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

  //  private final LatLng Nashik = new LatLng(latitude, longitude);

    double[] lat = new double[25];
    double[] lonG = new double[25];
    private Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        Resp = (TextView) findViewById(R.id.latlng);
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }

        //textView.setText(message[1]);
        FirebaseMessaging.getInstance().subscribeToTopic("Hello");
        FirebaseInstanceId.getInstance().getToken();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.home1:
                // startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.profile:

                return true;

            case R.id.about_us:
                //startActivity(new Intent(this, Feedback.class));
                return true;

            case R.id.emer:
                startActivity(new Intent(this, Static_Contacts.class));
                return true;


            case R.id.lang:
                //startActivity(new Intent(this, Notify.class));
                return true;


            case R.id.feedback:
                startActivity(new Intent(this, Feedback.class));
                return true;

            case R.id.notify:
                startActivity(new Intent(this, Notify.class));
                return true;

            case R.id.alert:
                //  startActivity(new Intent(this, NoToolbar.class));
                return true;


        }

        return super.onOptionsItemSelected(item);
    }


    private void init() {


        NearbyPlaces = (Button) findViewById(R.id.nearby);
        QuickReport = (Button) findViewById(R.id.Quick);
        //EmergencyContacts= (Button) findViewById(R.id.emergent);
        //Report= (Button) findViewById(R.id.report);
        // LiveTraffic= (Button) findViewById(R.id.traffic);
        rate = (Button) findViewById(R.id.area);
        // Info= (Button) findViewById(R.id.repo);
        Women = (Button) findViewById(R.id.women);
        SOS = (Button) findViewById(R.id.sos);
        LiveTraffic = (Button) findViewById(R.id.gog_traffic);

        // Resp =(TextView) findViewById(R.id.latlang);


        assert QuickReport != null;
        QuickReport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               // Kumar();
                Intent i = new Intent(MainActivity.this, OtherActivity.class);
                //  String string = latitude.  .toString();
                i.putExtra("aloc", latitude);
                i.putExtra("aloc_1", longitude);
                startActivityForResult(i, 10);

                //   startActivity(new Intent(MainActivity.this, OtherActivity.class));
            }

        });

        assert NearbyPlaces != null;
        NearbyPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initiateDialogNow();


            }

        });



      /*  assert EmergencyContacts != null;
        EmergencyContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });*/


        assert rate != null;
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, LetsRate.class));

            }

        });


       /* assert LiveTraffic != null;
        LiveTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NoToolbar.class));
            }

        });*/


       /* assert Info != null;
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });*/

        assert Women != null;
        Women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, NoHamburger.class));

            }

        });

      /*  assert Report != null;
        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });*/


        assert SOS != null;
        SOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "9799235966;7382781729;7587298523");
smsIntent.putExtra("sms_body", "Please help me! I am in distress at" + " "+getAddress() +" "+"http://maps.google.com/maps?saddr=" + latitude + "," + longitude);
                startActivity(smsIntent);

            }

        });

    }


    private void initiateDialogNow() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setTitle("Pick your spots");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);

        arrayAdapter.add("Police stations");
        arrayAdapter.add("Hospitals");
        arrayAdapter.add("Blood banks");
        arrayAdapter.add("Atms");
        arrayAdapter.add("Petrol stations");
        arrayAdapter.add("Restaurants");



        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        strName = arrayAdapter.getItem(which);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "You will be shortly shown your nearby" + " " + strName, Toast.LENGTH_LONG).show();

                        setContentView(R.layout.nearby);

                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.mapFragment);

                        mMap = mapFragment.getMap();

                        cameraUpdate();

                        GetPlacesRetrofit(strName, strName);


                    }
                });
        builderSingle.show();


    }


    public void GetPlacesRetrofit(String first, String second) {
        //  mlat = mMap.getMyLocation().getLatitude();
        //mlong = mMap.getMyLocation().getLongitude();

         cameraUpdate();

        sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=").append(latitude).append(",").append(longitude);
        sb.append("&rankby=distance");
        sb.append("&type=").append(first);
        sb.append("&name=").append(second);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyCilC5J7kK1G2phCgzY6-0YGo8Jxmgmohc");


        GoogleClient client = ServiceGenerator.createService(GoogleClient.class);

        Call<Example> call3 = client.getTestData(String.valueOf(sb));


        call3.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {

                pavan = response.body().getResults();


                for (int i = 0; i <= 5; i++) {

                    try {

                        lat[i] = pavan.get(i).getGeometry().getLocation().getLat();
                        lonG[i] = pavan.get(i).getGeometry().getLocation().getLng();


                        String pav = pavan.get(i).getName();
                        String nav = pavan.get(i).getVicinity();
                         markerOptions = new MarkerOptions();

                        mMap.addMarker(markerOptions.position(new LatLng(latitude,longitude))
                                .title("Your location")
                                .anchor(0.5f, 0.5f)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));



                        mMap.addMarker(markerOptions.position(new LatLng(lat[i], lonG[i]))
                                .title(pav + " "+"near"+" " + nav)
                                .anchor(0.5f, 0.5f)

                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //     Resp.setText(pavan.get(i).getGeometry().getLocation().getLat().toString() + "," + pavan.get(i).getGeometry().getLocation().getLng().toString());

                }


                Log.e("Res:", response.body().getResults().toString());

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("Err:", t.toString());
            }
        });
    }


    private void cameraUpdate() {


        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
       // mMap.setMyLocationEnabled(true);

        LatLng Nashik = new LatLng(latitude, longitude);




        //  mMap.setTrafficEnabled(true);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Nashik, 15));

// Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());

// Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(Nashik)      // Sets the center of the map to Mountain View
                .zoom(15)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void traffic(View view) {
        Intent intent = new Intent(this, traffic.class);
        intent.putExtra("aloc", latitude);
        intent.putExtra("aloc_1", longitude);
        startActivityForResult(intent, 10);



    }
    /***********************************************************************************************************************************************/
    private void getLastKnownLocation() {

        if (checkPlayServices()) {

            buildGoogleApiClient();
            createLocationRequest();
        }


    }


    protected synchronized void buildGoogleApiClient() {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        // Toast.makeText(getApplicationContext(),
        //       "successfully connected",
        //     Toast.LENGTH_SHORT).show();
        displayLocation();

        startLocationUpdates();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    private void displayLocation() {

        //  Toast.makeText(getApplicationContext(),
        //        "Display Location is triggered now",
        //      Toast.LENGTH_LONG).show();


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }


       // Resp.setText(String.valueOf(latitude) + "," + String.valueOf(longitude));
    }




    private void showGPSDisabledAlertToUser() {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);

                        //checkPlayServices();
                    }


                });


        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    private boolean checkPlayServices() {

        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    /**********************************************************************************************************************************/
    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;

        //  Toast.makeText(getApplicationContext(), "Location changed!",
        //        Toast.LENGTH_SHORT).show();


        displayLocation();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() { try {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);

    }catch (Exception e){
        e.printStackTrace();
    }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }


    private String getAddress(){

        String errorMessage="";
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = gc.getFromLocation(latitude,longitude, 1);
             sms = new StringBuilder();

            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                    sms.append(address.getAddressLine(i)).append("\n");
                sms.append(address.getLocality()).append("\n");
                sms.append(address.getPostalCode()).append("\n");
                sms.append(address.getCountryName());


            }
        }catch (IOException i1) {
            // Catch network or other I/O problems.
           // errorMessage = getString(R.string.service_not_available);
            Log.e(TAG, errorMessage, i1);
            return null;
        } catch (IllegalArgumentException i2) {
            // Catch invalid latitude or longitude values.
            //errorMessage = getString(R.string.invalid_lat_long_used);
            Log.e(TAG, errorMessage + ". " + "Latitude = " + latitude + ", Longitude = " + longitude +
                    i2);
            return null;
        }

        return String.valueOf(sms);
    }



}




