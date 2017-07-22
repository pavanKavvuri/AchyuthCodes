package com.mateoj.multiactivitydrawer;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.mateoj.multiactivitydrawer.Interfaces.GoogleClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {


    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    private static final String TAG = MainActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    double latitude, longitude;

    ImageButton gallery,camera,video;
    CheckBox Emergent;
    Button upload, getLoc;
    File compressedImageFile;
    Spinner spinner;
    ImageView mImageView, mCameraView;
    VideoView mVideoView;
    String imgDecodableString, item;
    double LAT = 0.0, LNG = 0.0;
    TextView latlng;
    double alat,along;

    private Uri fileUri, selectedImage, tempUri, pp;
    EditText IncidentDescription,name;

    static final int RESULT_LOAD_IMG = 1;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String IMAGE_DIRECTORY_NAME = "helloBoss";

    CircularProgressView progressView;
    static File pavan, naveen;

    Call<ResponseBody> call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Bundle extras = getIntent().getExtras();
         alat = extras.getDouble("aloc");
         along = extras.getDouble("aloc_1");
        report_init();
        latlng.setText(String.valueOf(alat) + "," + String.valueOf(along));
        //getLastKnownLocation();


        if (upload != null) {
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // uploadFile(new File(String.valueOf(imgDecodableString)));
                    // imgDecodableString = "";
             /*       try {
                        uploadFile(pavan);
                        disableUI();
                        progressView.setVisibility(View.VISIBLE);
                        progressView.startAnimation();
                        mCameraView.setVisibility(View.GONE);
                        pavan = null;
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Your cannot upload without an Image", Toast.LENGTH_LONG).show();
                   }*/
                    if (name.getText().length() != 0) {

                        if ((pavan == null) && (IncidentDescription.getText().length() == 0)) {
                            Toast.makeText(getApplicationContext(), "Report through text or a picture !", Toast.LENGTH_LONG).show();
                        }
                       else {

                        try {
                            uploadFile(pavan);
                            disableUI();
                            progressView.setVisibility(View.VISIBLE);
                            progressView.startAnimation();
                            // mCameraView.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Let us know through feed back, if it is stopping here! or Retry uploading ", Toast.LENGTH_LONG).show();
                        }

                    }
                }
                    else{
                        Toast.makeText(getApplicationContext(), "Please enter your name before reporting!", Toast.LENGTH_LONG).show();
                    }




                }
            });
        }
    }

    private void disableUI(){
        upload.setEnabled(false);
        gallery.setEnabled(false);
        camera.setEnabled(false);
        video.setEnabled(false);
        spinner.setEnabled(false);
        IncidentDescription.setEnabled(false);
        Emergent.setEnabled(false);
        name.setEnabled(false);

    }

    private void enableUI(){
        upload.setEnabled(true);
        gallery.setEnabled(true);
        camera.setEnabled(true);
        video.setEnabled(true);
        spinner.setEnabled(true);
        IncidentDescription.setEnabled(true);
        Emergent.setEnabled(true);
        name.setEnabled(true);
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
        switch (id)
        {
            case R.id.home1:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.profile:
                //startActivity(new Intent(this, OtherActivity.class));
                return true;

            case R.id.about_us :
                //startActivity(new Intent(this, NoHamburger.class));
                return true;

            case R.id.log_out:
               // startActivity(new Intent(this, MainActivity.class));
                return true;



            case R.id.lang :
                //startActivity(new Intent(this, Notify.class));
                return true;


            case R.id.feedback :
                startActivity(new Intent(this, Feedback.class));
                return true;

            case R.id.notify :
                startActivity(new Intent(this, Notify.class));
                return true;

            case R.id.alert :
              //  startActivity(new Intent(this, NoToolbar.class));
                return true;


        }

        return super.onOptionsItemSelected(item);
    }



    private void report_init() {

         gallery = (ImageButton) findViewById(R.id.button);
         camera = (ImageButton) findViewById(R.id.camera);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mCameraView = (ImageView) findViewById(R.id.CameraView);
        upload = (Button) findViewById(R.id.submit);
        // getLoc = (Button) findViewById(R.id.getLoc);
         video = (ImageButton) findViewById(R.id.VideoButton);
        latlng = (TextView) findViewById(R.id.textView2);

        progressView =(CircularProgressView) findViewById(R.id.progress_view);

        mVideoView.setVisibility(View.GONE);
        mVideoView.setVisibility(View.GONE);


         spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(OtherActivity.this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Crime_Array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Emergent = (CheckBox) findViewById(R.id.checkBox);

         name = (EditText) findViewById(R.id.name);
        IncidentDescription = (EditText) findViewById(R.id.IncidentDescription);

        assert gallery != null;
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
            }
        });


        assert camera != null;
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraIntent();
            }

        });

        assert video != null;
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Please select only camera image or Gallery image files to upload", Toast.LENGTH_SHORT)
                        .show();

                videoIntent();
            }

        });


    }

    private void galleryIntent() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");

// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    private void cameraIntent() {

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }

        try {
            Intent Cintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          //  fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
          //  Cintent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            // intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            // start the image capture Intent
            startActivityForResult(Cintent,  CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void videoIntent() {

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);


    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {

            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");

            pavan = mediaFile;

        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
            naveen = mediaFile;

        } else {
            return null;
        }

        return mediaFile;

    }





    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isStoragePermissionGranted();
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {


                // Get the Image from data

                selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);


                compressedImageFile = new Compressor.Builder(this)
                        .setMaxWidth(1200)
                        .setMaxHeight(1200)
                        .setQuality(75)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .build()
                        .compressToFile(new File(imgDecodableString));

                pavan= compressedImageFile;


                //pavan= new File(imgDecodableString);

                cursor.close();
                mImageView.setVisibility(View.VISIBLE);
                mCameraView.setVisibility(View.GONE);
                // mImageView.cancelLongPress();

                mImageView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));


            }
/*******************************************************************************************************************/
            else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {


                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                //    imageView.setImageBitmap(photo);
                //    knop.setVisibility(Button.VISIBLE);


                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    tempUri = getImageUri(getApplicationContext(), photo);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(tempUri));

                    pavan= finalFile;

                    pp= Uri.fromFile( new File(getRealPathFromURI(tempUri)));


                    previewCapturedImage();
                } else if (resultCode == RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(getApplicationContext(),
                            "Try again! Image capture has been cancelled", Toast.LENGTH_SHORT)
                            .show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                            .show();
                }

            } else if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {

                    Toast.makeText(getApplicationContext(),
                            "Video has been successfully recorded", Toast.LENGTH_SHORT)
                            .show();
                    previewVideo();
                } else if (resultCode == RESULT_CANCELED) {

                    Toast.makeText(getApplicationContext(),
                            "Try again! Video has been cancelled", Toast.LENGTH_SHORT)
                            .show();
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                            .show();
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT)
            //       .show();
        }

    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

        @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", pp);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        pp = savedInstanceState.getParcelable("file_uri");
    }


    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    private void uploadFile(File multi) {
        // create upload service client
        GoogleClient service =
                ServiceGenerator.createService(GoogleClient.class);

        File satish= multi;

        // MultipartBody.Part CAM = prepareFilePart("image", cameraFile);

        //MultipartBody.Part VID = prepareFilePart("video", videoFile);
        RequestBody IncidentType = createPartFromString(item);
        RequestBody NAM = createPartFromString(String.valueOf(name.getText()));

        RequestBody DES = createPartFromString(String.valueOf(IncidentDescription.getText()));

        RequestBody LAT = createPartFromString(String.valueOf(alat));

        RequestBody LNG = createPartFromString(String.valueOf(along));

        RequestBody Emergency = createPartFromString(String.valueOf(Emergent.isChecked()));

        if (satish != null){

            MultipartBody.Part GAL = prepareFilePart("image", satish);
            call = service.TestReport("http://52.11.160.94/3di/inc/forms/live_updates_form.php", Emergency, IncidentType, DES,NAM, LAT, LNG,GAL);
    }

        else{

            call = service.onlyText("http://52.11.160.94/3di/inc/forms/live_updates_form.php",Emergency,IncidentType, DES,NAM,LAT,LNG);

        }



        call.enqueue(new Callback<ResponseBody>() {//http://3di.pe.hu/inc/forms/live_updates_form.php
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");

                mCameraView.setVisibility(View.GONE);
                mImageView.setVisibility(View.GONE);

                enableUI();

                progressView.stopAnimation();
                progressView.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(),"Your report is successfully received! Thanks for reporting", Toast.LENGTH_SHORT).show();

                pavan=null;

                name.setText("");
                IncidentDescription.setText("");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    Log.e("Upload error:", t.getMessage());
                    enableUI();
                    progressView.stopAnimation();
                    progressView.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Sorry! We cannot receive your report!  Check your internet connection and try again", Toast.LENGTH_LONG).show();

                    Toast.makeText(getApplicationContext(), "You can still retry", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    enableUI();
                    progressView.stopAnimation();
                    progressView.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Sorry! We cannot receive your report!  Check your internet connection and try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, File file) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        //  File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    private void previewCapturedImage() {
        try {

            mCameraView.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.GONE);

            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 2;

            final Bitmap bitmap = BitmapFactory.decodeFile(getRealPathFromURI(tempUri),
                    options);

            mCameraView.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewVideo() {
        try {
            // hide image preview
            mImageView.setVisibility(View.GONE);

          //  mVideoView.setVisibility(View.VISIBLE);
          //  mVideoView.setVideoPath(fileUri.getPath());
            // start playing
           // mVideoView.stopPlayback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***********************************************************************************************************************************************/
 /*   private void getLastKnownLocation() {

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
     //   displayLocation();

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






        latlng.setText(String.valueOf(alat) + "," + String.valueOf(along));
    }




    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OtherActivity.this);
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
        AlertDialog alert = alertDialogBuilder.create();
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
    */

    /**********************************************************************************************************************************/
 /*   @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;

        //  Toast.makeText(getApplicationContext(), "Location changed!",
        //        Toast.LENGTH_SHORT).show();


        //displayLocation();
    }

    protected void startLocationUpdates() {
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
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }  */



    /******************************************************************************************************************/

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }


    }


}
