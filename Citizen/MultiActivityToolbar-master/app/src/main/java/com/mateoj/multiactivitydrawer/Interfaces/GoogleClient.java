package com.mateoj.multiactivitydrawer.Interfaces;

import com.mateoj.multiactivitydrawer.POJO.Example;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by pavan on 4/8/16.
 */
public interface GoogleClient {

    @GET
    Call<Example> getTestData(@Url String url);
    //Call<Example> loadMaps();

    @Multipart
    @POST
    Call<ResponseBody> uploadMultipleFiles(@Url String url,
                                           @Part MultipartBody.Part GalleryFile);

}
