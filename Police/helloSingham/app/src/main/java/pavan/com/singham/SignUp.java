package pavan.com.singham;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import pavan.com.singham.Interfaces.GoogleClient;
import pavan.com.singham.LoginPojo.LoginResp;
import retrofit2.Call;
import retrofit2.Callback;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    EditText _name;

    EditText _phone;
     EditText _email;
    EditText _password;
    EditText _conPass;
     EditText _addr;

    Button _signupButton;

    TextView _loginLink;

    String contact, name, email, password, confirm_password, address;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        _name = (EditText) findViewById(R.id.input_name);
        _phone = (EditText) findViewById(R.id.contact_num);
        _email = (EditText) findViewById(R.id.input_email);
        _password = (EditText) findViewById(R.id.input_password);
        _conPass = (EditText) findViewById(R.id.confirm_pass);
        _addr = (EditText) findViewById(R.id.address);

        _signupButton = (Button) findViewById(R.id.btn_signup);

        _loginLink = (TextView) findViewById(R.id.link_login);



        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

        Intent intent = getIntent();
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();

        }else{

            disableUI();

            GoogleClient service =
                    ServiceGenerator.createService(GoogleClient.class);
/*
            RequestBody NAM = createPartFromString(String.valueOf(name));
            RequestBody PHN = createPartFromString(String.valueOf(contact));
            RequestBody EML = createPartFromString(String.valueOf(email));
            RequestBody PWD = createPartFromString(String.valueOf(password));

            RequestBody LOC = createPartFromString(String.valueOf(address));

       */
            StringBuilder  sb = new StringBuilder("http://52.11.160.94/reports/inc/forms/signup.php?");
            sb.append("name=" + _name.getText());
                    sb.append("&password=" + _password.getText());
                            sb.append("&locality=" + _addr.getText());
                                       sb.append("&email=" + _email.getText());
                                                  sb.append("&phone_number=" + _phone.getText());




            Call<LoginResp> verify = service.Registration(String.valueOf(sb));

            verify.enqueue(new Callback<LoginResp>() {
                @Override
                public void onResponse(Call<LoginResp> verify, retrofit2.Response<LoginResp> response1) {

                     String pavan = response1.body().getResult();
               //     String naveen = response1.body().getUserID();

                    //  Toast.makeText(getApplicationContext(), "in auth", Toast.LENGTH_SHORT).show();

                    Toast.makeText(getApplicationContext(),pavan, Toast.LENGTH_SHORT).show();


                    if(pavan.equals("0")){

                        _email.setError("Sorry this email already exists");

                     //   Toast.makeText(getApplicationContext(),"Sorry this email already exists" , Toast.LENGTH_SHORT).show();

                    }else if(pavan.equals("1")){
                        _phone.setError("Sorry this number is already registered");
                      //  Toast.makeText(getApplicationContext(),"Sorry this number is already registered" , Toast.LENGTH_SHORT).show();

                    }else if(pavan.equals("3")){
                        _phone.setError("Sorry this email and phonenumber already exists");
                        //Toast.makeText(getApplicationContext(),"Sorry this email and phonenumber already exists" , Toast.LENGTH_SHORT).show();

                    }else if(pavan.equals("2")){

                        Toast.makeText(getApplicationContext(),"Congratulations you are a registered with Third I" , Toast.LENGTH_SHORT).show();
                       // onSignupSuccess();
                        finish();

                    }

                }

                @Override
                public void onFailure(Call<LoginResp> verify, Throwable t) {
                    Log.e("Err:", t.toString());

                    Toast.makeText(getApplicationContext(),"Sorry! Check your Internet connection and try again" , Toast.LENGTH_LONG).show();


                }
            });

        }

        enableUI();

    }







    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse("SignUpCredentials"), descriptionString);
    }


    private void disableUI(){

        _name.setEnabled(false);
        _phone.setEnabled(false);
        _email.setEnabled(false);
        _password.setEnabled(false);
        _conPass.setEnabled(false);
        _signupButton.setEnabled(false);
        _loginLink.setEnabled(false);
        _addr.setEnabled(false);

    }

    private void enableUI(){
        _addr.setEnabled(true);
        _name.setEnabled(true);
        _phone.setEnabled(true);
        _email.setEnabled(true);
        _password.setEnabled(true);
        _conPass.setEnabled(true);
        _signupButton.setEnabled(true);
        _loginLink.setEnabled(true);

    }



    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {

        boolean valid = true;

        try {

            name = _name.getText().toString();
            email = _email.getText().toString().trim();
            password = _password.getText().toString().trim();
            confirm_password = _conPass.getText().toString().trim();
            contact = _phone.getText().toString().trim();
            address = _addr.getText().toString().trim();

            if (name.isEmpty() || name.length() < 3) {
                _name.setError("at least 3 characters");
                valid = false;
            } else {
                _name.setError(null);
            }

        /*    if ( email.isEmpty() ||!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _email.setError("enter a valid email address");
                valid = false;
            } else {
                _email.setError(null);
            }*/

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                _password.setError("between 5 and 10 alphanumeric characters");
                valid = false;
            } else {
                _password.setError(null);
            }

            if (!(password.equals(confirm_password))) {
                _password.setError("Password fields don't match");
                _conPass.setError("Password fields don't match");
                valid = false;
            } else {
                _conPass.setError(null);
            }


            if (address.isEmpty() || address.length() < 3) {
                _addr.setError("Enter proper address");
                valid = false;
            } else {
                _addr.setError(null);
            }

            if (!((PhoneNumberUtils.isGlobalPhoneNumber(contact)))) {
                if (contact.length() != 10) {
                    _phone.setError("Enter proper contact number");
                    valid = false;
                } else {
                }
            } else {
                _phone.setError(null);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return valid;
    }



}
