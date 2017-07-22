package com.mateoj.multiactivitydrawer;

/**
 * Created by pavan on 22/7/16.
 */
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://10.0.0.149:2779/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static String baseUrl = null;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());


   // return builder;



    public static <S> S createService(Class<S> serviceClass ) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }



}


