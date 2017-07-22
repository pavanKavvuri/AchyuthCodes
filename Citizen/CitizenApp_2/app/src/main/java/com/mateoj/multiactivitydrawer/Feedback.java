package com.mateoj.multiactivitydrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mateoj.multiactivitydrawer.Interfaces.GoogleClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feedback extends BaseActivity {


    RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4;
    RadioButton like0, like1, help0, help1, wit0, wit1, wit2, rep0, rep1;
    EditText suggestions;
    Button send;
    int likeId, helpId, repId, witId;
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
String pav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        suggestions = (EditText) findViewById(R.id.feedback);
        send = (Button) findViewById(R.id.Send);


        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
        radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                likeId = radioGroup1.getCheckedRadioButtonId();
                helpId = radioGroup2.getCheckedRadioButtonId();
                repId = radioGroup3.getCheckedRadioButtonId();
                witId = radioGroup4.getCheckedRadioButtonId();


                like0=(RadioButton)findViewById(likeId);
                help0=(RadioButton)findViewById(helpId);
                rep0=(RadioButton)findViewById(repId);
                wit0=(RadioButton)findViewById(witId);



                assert wit0 != null;
                if(wit0.getText().equals("I will report") ){
                    pav= "Yes";
                }

                else if(wit0.getText().equals("I don't think so") ){
                    pav= "No";
                }

                else if(wit0.getText().equals("I can't say") ){
                    pav= "may be";
                }

                else {
                    pav= (String) wit0.getText();
                }

                //   String opinion = likeId.getText;

                Upload();


            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Soon this action will be in place :)", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
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
                //  startActivity(new Intent(this, NoToolbar.class));
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



    private void Upload() {

        GoogleClient service =
                ServiceGenerator.createService(GoogleClient.class);


        RequestBody DES = createPartFromString(String.valueOf(suggestions.getText()));
        RequestBody l1 = createPartFromString(String.valueOf(like0.getText()));
        RequestBody h1 = createPartFromString(String.valueOf(help0.getText()));
        RequestBody r1 = createPartFromString(String.valueOf(rep0.getText()));
        RequestBody v1 = createPartFromString(String.valueOf(pav));


        Call<ResponseBody> call = service.Feedback("http://52.11.160.94/3di/inc/forms/feedback.php", DES, l1, h1, r1, v1);
        call.enqueue(new Callback<ResponseBody>() {//http://3di.pe.hu/inc/forms/live_updates_form.php
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");


                Toast.makeText(getApplicationContext(), "Thanks for your feedback", Toast.LENGTH_LONG).show();


            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                Toast.makeText(getApplicationContext(), "Please retry!", Toast.LENGTH_LONG).show();


            }
        });


    }


    @NonNull
    private RequestBody createPartFromString (String descriptionString){
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

}
