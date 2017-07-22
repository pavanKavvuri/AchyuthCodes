package com.mateoj.multiactivitydrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.mateoj.multiactivitydrawer.Interfaces.GoogleClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LetsRate extends BaseActivity implements AdapterView.OnItemSelectedListener {

    String item;
    Spinner spinner;
    RatingBar patrol,night,overall,basics;
    EditText areaSuggest;
    float p,q,r,s;
    Button submit;

   // CircularProgressView progressView;
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_rate);


        rate_Init();

        patrol.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                p =rating;

            }
        });

        night.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                 q =rating;

            }
        });

        basics.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                s =rating;

            }
        });

        overall.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                r =rating;

            }
        });


        assert submit != null;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rateYouNow(item, p, q, r,s);

                disableUI();
               // progressView.setVisibility(View.VISIBLE);
                //progressView.startAnimation();
            }

        });






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



    private void rate_Init(){

        patrol = (RatingBar) findViewById(R.id.ratingBar3);
        night = (RatingBar) findViewById(R.id.ratingBar2);
        basics = (RatingBar) findViewById(R.id.ratingBar);
        overall = (RatingBar) findViewById(R.id.ratingBar4);
        areaSuggest=(EditText) findViewById(R.id.editText);
        submit = (Button) findViewById(R.id.submit);

        //progressView =(CircularProgressView) findViewById(R.id.progress_view);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(LetsRate.this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Area_Array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


    private void disableUI(){
        submit.setEnabled(false);
        areaSuggest.setEnabled(false);
        night.setEnabled(false);
        patrol.setEnabled(false);
        overall.setEnabled(false);
        basics.setEnabled(false);
        spinner.setEnabled(false);
    }


    private void enableUI(){
        submit.setEnabled(true);
        areaSuggest.setEnabled(true);
        night.setEnabled(true);
        patrol.setEnabled(true);
        basics.setEnabled(true);
        overall.setEnabled(true);
        spinner.setEnabled(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    private void rateYouNow(String a,float p1, float q1, float r1,float s1) {
        // create upload service client
        GoogleClient service =
                ServiceGenerator.createService(GoogleClient.class);


        RequestBody ar = createPartFromString(a);

        RequestBody c1 = createPartFromString(String.valueOf(areaSuggest.getText()));

        RequestBody m1 = createPartFromString(String.valueOf(p1));

        RequestBody n1 = createPartFromString(String.valueOf(q1));

        RequestBody l1 = createPartFromString(String.valueOf(r1));

        RequestBody o1 = createPartFromString(String.valueOf(s1));




        Call<ResponseBody> call = service.CommunityRating("http://52.11.160.94/3di/inc/forms/area_feedback.php",ar,c1, m1,n1,o1,l1);
        call.enqueue(new Callback<ResponseBody>() {//http://52.11.160.94/3di/inc/forms/area_feedback.php//http://10.0.0.149:
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");

                enableUI();

                //progressView.stopAnimation();
                //progressView.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(),"Thanks for rating your area", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    Log.e("Upload error:", t.getMessage());
                    enableUI();

                  //  progressView.stopAnimation();
                   // progressView.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Sorry! Check your internet connection and try again", Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    e.printStackTrace();
                   // progressView.stopAnimation();
                    //progressView.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Sorry! Check your internet connection and try again", Toast.LENGTH_LONG).show();


                }
            }
        });
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

}
