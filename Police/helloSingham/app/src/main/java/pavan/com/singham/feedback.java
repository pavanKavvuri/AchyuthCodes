package pavan.com.singham;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pavan.com.singham.Interfaces.GoogleClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class feedback extends AppCompatActivity {

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        suggestions = (EditText) findViewById(R.id.feedback);
        send = (Button) findViewById(R.id.Send);


        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
        radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);

      /*  like0 = (RadioButton) findViewById(R.id.Like0);
        like1 = (RadioButton) findViewById(R.id.Like1);

        help0 = (RadioButton) findViewById(R.id.help3);
        help1 = (RadioButton) findViewById(R.id.help4);


        rep0 = (RadioButton) findViewById(R.id.rep5);
        rep1 = (RadioButton) findViewById(R.id.rep6);

        wit0 = (RadioButton) findViewById(R.id.wit7);
        wit1 = (RadioButton) findViewById(R.id.wit8);
        wit2 = (RadioButton) findViewById(R.id.wit9);*/


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

                Upload();


            }
        });


    }



    private void Upload() {

        GoogleClient service =
                ServiceGenerator.createService(GoogleClient.class);


        RequestBody DES = createPartFromString(String.valueOf(suggestions.getText()));
        RequestBody l1 = createPartFromString(String.valueOf(like0.getText()));
        RequestBody h1 = createPartFromString(String.valueOf(help0.getText()));
        RequestBody r1 = createPartFromString(String.valueOf(rep0.getText()));
        RequestBody v1 = createPartFromString(String.valueOf(pav));


        Call<ResponseBody> call = service.Feedback("http://52.11.160.94/reports/inc/forms/feedback.php", DES, l1, h1, r1, v1);
        call.enqueue(new Callback<ResponseBody>() {//http://3di.pe.hu/inc/forms/live_updates_form.php
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");


                Toast.makeText(getApplicationContext(), "Thanks for your feedback ", Toast.LENGTH_LONG).show();


            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                Toast.makeText(getApplicationContext(), "Sorry! Try again", Toast.LENGTH_LONG).show();


            }
        });


    }


    @NonNull
    private RequestBody createPartFromString (String descriptionString){
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }
}
