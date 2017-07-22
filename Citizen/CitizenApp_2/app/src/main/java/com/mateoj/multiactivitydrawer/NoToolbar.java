package com.mateoj.multiactivitydrawer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class NoToolbar extends BaseActivity implements OnMapReadyCallback {

    private static final LatLng Nashik = new LatLng(20.005366,73.7792796);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_toolbar);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapFrag);
        //getSupportFragmentManager().addOnBackStackChangedListener(MainActivity.class);

        mapFragment.getMapAsync(this);


    }

    @Override
    protected boolean useToolbar() {
        return false;
    }

    @Override
    protected boolean useDrawerToggle() {
        return false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

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

        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

      //  googleMap.setTrafficEnabled(true);


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Nashik, 15));

// Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());

// Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(Nashik)      // Sets the center of the map to Mountain View
                .zoom(16)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
  /*  @Override
    public void finish() {
        // Prepare data intent
        Intent data = new Intent(this,MainActivity.class);
     //   data.putExtra("returnKey1", "Swinging on a star. ");
      //  data.putExtra("returnKey2", "You could be better then you are. ");
        // Activity finished ok, return the data
       setResult(RESULT_OK, data);
        super.onBackPressed();
       // startActivityForResult(data,10);

       super.finish();
    } */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        // Otherwise defer to system default behavior.
     //   super.onBackPressed();
    }

}








 /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.home1:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.profile:
                startActivity(new Intent(this, OtherActivity.class));
                return true;

            case R.id.about_us :
                startActivity(new Intent(this, NoHamburger.class));
                return true;

            case R.id.log_out:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.Support:
                startActivity(new Intent(this, NoToolbar.class));
                return true;

            case R.id.lang :
                startActivity(new Intent(this, Notify.class));
                return true;


            case R.id.feedback :
                startActivity(new Intent(this, NoToolbar.class));
                return true;

            case R.id.notify :
                startActivity(new Intent(this, Notify.class));
                return true;

            case R.id.alert :
                startActivity(new Intent(this, NoToolbar.class));
                return true;


        }

        return super.onOptionsItemSelected(item);
    }
    */


