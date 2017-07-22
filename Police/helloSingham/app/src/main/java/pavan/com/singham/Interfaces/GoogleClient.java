 package pavan.com.singham.Interfaces;

 import okhttp3.MultipartBody;
 import okhttp3.RequestBody;
 import okhttp3.ResponseBody;
 import pavan.com.singham.LoginPojo.LoginResp;
 import pavan.com.singham.POJO.Example;
 import retrofit2.Call;
 import retrofit2.http.GET;
 import retrofit2.http.Multipart;
 import retrofit2.http.POST;
 import retrofit2.http.Part;
 import retrofit2.http.Url;

/**
 * Created by pavan on 28/7/16.
 */


public interface GoogleClient {

    @GET
    Call<Example> getTestData(@Url String url);
    //Call<Example> loadMaps();

    @Multipart
    @POST
    Call<ResponseBody> Report(@Url String url,
                                           @Part("IncidentType") RequestBody IncidentType,
                                           @Part("IncidentLoc") RequestBody IncidentLoc,
                                           @Part("IncidentDes") RequestBody IncidentDes,
                                           @Part("Emergency") RequestBody Emergency,
                                           @Part MultipartBody.Part CameraFile,
                                           @Part MultipartBody.Part VideoFile,
                                           @Part MultipartBody.Part GalleryFile);

    @Multipart
    @POST
    Call<ResponseBody> QuickReport(@Url String url,
                                   @Part("type") RequestBody type,
                                   @Part("name") RequestBody name,
                                   @Part("lat") RequestBody lat,
                                   @Part("lng") RequestBody lng,
                                   @Part("message") RequestBody description,
                                   @Part MultipartBody.Part CameraFile);


    @Multipart
    @POST
    Call<ResponseBody> MultiReport(@Url String url,

                                   @Part("name") RequestBody User,
                                   @Part("emergency") RequestBody Emergency,
                                   @Part("type") RequestBody IncidentType,
                                   @Part("message") RequestBody DES,
                                   @Part("lat") RequestBody LAT,
                                   @Part("lng") RequestBody LNG,

                                   @Part MultipartBody.Part GAL);

    @Multipart
    @POST
    Call<ResponseBody> Feedback(@Url String url,

                                @Part("Feedback") RequestBody DES,
                                @Part("Liked") RequestBody l1,
                                @Part("Helps") RequestBody h1,
                                @Part("Reports") RequestBody r1,
                                @Part("Witness") RequestBody v1);


    @Multipart
    @POST
    Call<ResponseBody> onlyText(@Url String url,
                                @Part("name") RequestBody Nam,
                                @Part("emergency") RequestBody Emergency,
                                @Part("type") RequestBody IncidentType,
                                @Part("message") RequestBody DES,
                                @Part("lat") RequestBody LAT,
                                @Part("lng") RequestBody LNG);




    @GET
    Call<LoginResp> Registration(@Url String url
                                   );



    @GET
    Call<LoginResp> LoginAuth(@Url String url


    );



}
