package pavan.com.singham;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pavan.com.singham.Interfaces.GoogleClient;
import pavan.com.singham.LoginPojo.LoginResp;
import retrofit2.Call;
import retrofit2.Callback;


public class Login extends AppCompatActivity {

    StringBuilder sb;

    String result,hell;

    public static final String PREFS_NAME = "LoginPrefs";



    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailPh;
    EditText _passwordText;
    Button _loginButton;
     TextView _signupLink;
    TextView _forgotPass;

    GoogleClient service;
    boolean upload=true;
    Call<ResponseBody> call;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getString("logged", "").equals("logged")) {



            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        _emailPh = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);
        _forgotPass = (TextView) findViewById(R.id.forgot);


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });


        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        _forgotPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity

            }
        });




    }

    public void login() {
        Log.d(TAG, "Login");

    /*    if (!Upload()) {
            onLoginFailed();
           // return;
        }else{




        }*/

        authenticate();

        /*_loginButton.setEnabled(false);

      final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

   /* public boolean Upload() {

     service =
                ServiceGenerator.createService(SignUpClient.class);


        RequestBody Em_Ph = createPartFromString(String.valueOf(_emailPh.getText()));
        RequestBody PWD = createPartFromString(String.valueOf(_passwordText.getText()));

      //  call = service.Login("http://10.0.0.91:2799/Server.js",Em_Ph,PWD);

     call = service.Login("http://52.11.160.94/3di/inc/forms/login.php",Em_Ph,PWD);

        //http://52.11.160.94/3di/inc/forms/login.php

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");



                 upload= true;

                Toast.makeText(getApplicationContext(),"Upload success", Toast.LENGTH_LONG).show();

          authenticate();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e("Upload error:", t.getMessage());
                _passwordText.setText("");

                Toast.makeText(getApplicationContext(), "Sorry! Check your internet connection and try again", Toast.LENGTH_SHORT).show();

                 upload=false;
            }
        });

        return upload;
    }

*/

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse("LoginCredentials"), descriptionString);
    }


    private void authenticate(){

        service =
                ServiceGenerator.createService(GoogleClient.class);


    //    RequestBody Em_Ph = createPartFromString(String.valueOf(_emailPh.getText()));
     //   RequestBody PWD = createPartFromString(String.valueOf(_passwordText.getText()));



    /*    sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&rankby=distance");
        sb.append("&type=" + first);
        sb.append("&name=" + second);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyCilC5J7kK1G2phCgzY6-0YGo8Jxmgmohc");
*/

        sb = new StringBuilder("http://52.11.160.94/reports/inc/forms/login.php?");
        sb.append("user=" + _emailPh.getText());
        sb.append("&password=" + _passwordText.getText());

        hell= String.valueOf(_emailPh.getText());


      //  Toast.makeText(getApplicationContext(), "in auth", Toast.LENGTH_SHORT).show();

        try {

            // RequestBody Em_Ph = createPartFromString(String.valueOf(_emailPh.getText()));

            Call<LoginResp> auth = service.LoginAuth(String.valueOf(sb));


            // Call<LoginResp> auth = service.LoginAuth("http://10.0.0.91:2799/Server.js");

            auth.enqueue(new Callback<LoginResp>() {
                @Override
                public void onResponse(Call<LoginResp> auth, retrofit2.Response<LoginResp> response1) {

                    String pavan = response1.body().getResult();
                    String naveen = response1.body().getUserID();

                   // Toast.makeText(getApplicationContext(), "in auth", Toast.LENGTH_SHORT).show();

                    Toast.makeText(getApplicationContext(),pavan, Toast.LENGTH_SHORT).show();



                  if(pavan.equals("1")){

                      _emailPh.setText("");
                      _passwordText.setText("");


                      SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                      SharedPreferences.Editor editor = settings.edit();
                      editor.putString("logged", "logged");
                      editor.putString("User", hell);
                      editor.putString("Password", String.valueOf(_passwordText.getText()));
                      editor.commit();




                      startActivity(new Intent(Login.this, MainActivity.class));
                      finish();



                  }else if(pavan.equals("0")) {

                      _emailPh.setError("Wrong username or password");
                      _passwordText.setError("Wrong username or password");

                      //  Toast.makeText(getApplicationContext(),"Sorry! wrong username or password", Toast.LENGTH_SHORT).show();
                    }



                }

                @Override
                public void onFailure(Call<LoginResp> call, Throwable t) {
                    Log.e("Err:", t.toString());

                    Toast.makeText(getApplicationContext(),"Sorry! Check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }


   /* @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }


    public void backButtonHandler() {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
        alertDialog.setIcon(R.mipmap.alert);
        alertDialog.setTitle(getString(R.string.leave_application));
        alertDialog.setMessage(getString(R.string.Leave_application_msg));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.YES),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.NO), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                    }
                }
        );
        alertDialog.show();
    }

    */

}