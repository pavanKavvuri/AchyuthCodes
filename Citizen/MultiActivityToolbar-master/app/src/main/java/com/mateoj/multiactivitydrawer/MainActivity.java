package com.mateoj.multiactivitydrawer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mateoj.multiactivitydrawer.Interfaces.GoogleClient;
import com.mateoj.multiactivitydrawer.POJO.Example;
import com.mateoj.multiactivitydrawer.POJO.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends BaseActivity {

    Button NearbyPlaces,QuickReport,Report,EmergencyContacts,LiveTraffic,rate,Info,Women;
    String strName; StringBuilder sb;
    TextView Resp;
    List<Result> pavan;


    double mLatitude=19.9974;
    double mLongitude=73.7876113;

    double[] lat = new double[25];
    double[] lonG = new double[25];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

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


    private void init(){


        NearbyPlaces=(Button) findViewById(R.id.nearby);
        QuickReport= (Button) findViewById(R.id.Quick);
        EmergencyContacts= (Button) findViewById(R.id.emergent);
        Report= (Button) findViewById(R.id.report);
        LiveTraffic= (Button) findViewById(R.id.traffic);
        rate= (Button) findViewById(R.id.area);
        Info= (Button) findViewById(R.id.repo);
        Women= (Button) findViewById(R.id.women);

        Resp =(TextView) findViewById(R.id.latlang);


        assert QuickReport != null;
        QuickReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });

        assert NearbyPlaces != null;
        NearbyPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              initiateDialogNow();


            }

        });



        assert EmergencyContacts != null;
        EmergencyContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });


        assert rate != null;
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });


        assert LiveTraffic != null;
        LiveTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });


        assert Info != null;
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });

        assert Women != null;
        Women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });

        assert Report != null;
        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });

    }

    private void initiateDialogNow(){

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setTitle("Pick your spots");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);

        arrayAdapter.add("Police stations");
        arrayAdapter.add("Hospitals");
        arrayAdapter.add("Atms");
        arrayAdapter.add("Petrol stations");
        arrayAdapter.add("Services");



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
                        Toast.makeText(getApplicationContext(), "You will be shortly shown your nearby"+" "+strName,Toast.LENGTH_SHORT).show();


                        dialog.dismiss();
                        GetPlacesRetrofit(strName,strName);



                    }
                });
        builderSingle.show();


    }

    public void GetPlacesRetrofit(String first, String second){

        sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&rankby=distance");
        sb.append("&type=" + first);
        sb.append("&name=" + second);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyCilC5J7kK1G2phCgzY6-0YGo8Jxmgmohc");




        GoogleClient client = ServiceGenerator.createService(GoogleClient.class);

        Call<Example> call3 = client.getTestData(String.valueOf(sb));


        call3.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {

                pavan=response.body().getResults();



                for(int i=0; i<=5; i++) {

                    Resp.setText(pavan.get(i).getGeometry().getLocation().getLat().toString() + "," + pavan.get(i).getGeometry().getLocation().getLng().toString());


                }


                Log.e("Res:", response.body().getResults().toString());

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("Err:", t.toString());
            }
        });
    }


}
