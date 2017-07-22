package com.mateoj.multiactivitydrawer;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by pavan on 10/8/16.
 */
public class firebase extends FirebaseInstanceIdService {



    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.w("FirebaseInstanceService", "token:" + token);

        registerToken(token);
    }

    private void registerToken(String token) {


        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        Request request = new Request.Builder()
                .url("http://52.11.160.94/3di/inc/forms/fcm_citizen.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
