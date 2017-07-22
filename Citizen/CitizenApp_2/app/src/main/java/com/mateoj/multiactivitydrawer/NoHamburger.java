package com.mateoj.multiactivitydrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mateoj.multiactivitydrawer.Interfaces.GoogleClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoHamburger extends BaseActivity  implements AdapterView.OnItemSelectedListener{

    String item_1,item_2;
    EditText Area, Date, Complaint;
    Spinner spinner,sp2;
    Button submit;

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    Call<ResponseBody> call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_hamburger);

        Date = (EditText) findViewById(R.id.date);
        Complaint = (EditText) findViewById(R.id.complaint);
        Area = (EditText) findViewById(R.id.Loc);
        submit=(Button) findViewById(R.id.rep);


        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(NoHamburger.this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Women_Crime, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        sp2 = (Spinner) findViewById(R.id.spinner2);
        sp2.setOnItemSelectedListener(NoHamburger.this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.police_Entries, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter1);


        if (submit != null) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        SubmitReport();
                       disableUI();

                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"It stopped here", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }



    @Override
    protected boolean useDrawerToggle() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // if (item.getItemId() == R.id.action_noHamburger)
        //   return true;

        if (item.getItemId() == android.R.id.home)
            // onBackPressed();
            startActivity(new Intent(this, MainActivity.class));


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinner1)
        {
            item_1 = parent.getItemAtPosition(position).toString();
        }
        else if(spinner.getId() == R.id.spinner2)
        {
            item_2 = parent.getItemAtPosition(position).toString();
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void disableUI(){
        Date.setEnabled(false);
        sp2.setEnabled(false);
        spinner.setEnabled(false);
        Complaint.setEnabled(false);
        Area.setEnabled(false);
        submit.setEnabled(false);
    }


    private void enableUI(){
        Date.setEnabled(true);
        sp2.setEnabled(true);
        spinner.setEnabled(true);
        Complaint.setEnabled(true);
        Area.setEnabled(true);
        submit.setEnabled(true);
    }



    private void SubmitReport() {
        // create upload service client
        GoogleClient service =
                ServiceGenerator.createService(GoogleClient.class);



        // MultipartBody.Part CAM = prepareFilePart("image", cameraFile);

        //MultipartBody.Part VID = prepareFilePart("video", videoFile);
        RequestBody Ar_0 = createPartFromString(item_1);

        RequestBody Ar_1 = createPartFromString(item_2);


        RequestBody DES = createPartFromString(String.valueOf(Complaint.getText()));

        RequestBody DAT = createPartFromString(String.valueOf(Date.getText()));

        RequestBody LOC = createPartFromString(String.valueOf(Area.getText()));

       // RequestBody Emergency = createPartFromString(String.valueOf(Emergent.isChecked()));

        call = service.WomenReport("http://52.11.160.94/3di/inc/forms/women_report.php",Ar_0,LOC,DAT, DES,Ar_1);
        call.enqueue(new Callback<ResponseBody>() {//http://3di.pe.hu/inc/forms/live_updates_form.php
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");

                enableUI();


                Toast.makeText(getApplicationContext(),"Your report is successfully received! Thanks for reporting", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    Log.e("Upload error:", t.getMessage());
                   enableUI();


                    Toast.makeText(getApplicationContext(), "Sorry! We cannot receive your report!  Check your internet connection and try again", Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    e.printStackTrace();

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


}