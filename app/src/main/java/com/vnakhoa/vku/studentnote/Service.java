package com.vnakhoa.vku.studentnote;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Service {
    @FormUrlEncoded
    @POST("/api/v4/login")
    Call<ResponseBody> login(@Field("email") String email,
                              @Field("password") String password);

    @GET("/api/v4/calendar/today/{id}")
    Call<ResponseBody> getToday(@Path("id") String _id);

    @GET("/api/v4/calendar/{id}")
    Call<ResponseBody> getModule(@Path("id") String id);

    @GET("/api/v4/calendar-week/{id}")
    Call<ResponseBody> getCalendar(@Path("id") String id);

    @GET("/api/v4/point/{id}")
    Call<ResponseBody> getPoint(@Path("id") String id);

    @Multipart
    @POST("/api/v4/update/student")
    Call<ResponseBody> updateStudent(@Part("name") RequestBody name,
                                  @Part("gender") RequestBody gender,
                                  @Part("birth") RequestBody birth,
                                  @Part("phoneNumber") RequestBody phoneNumber,
                                  @Part("address") RequestBody address,
                                  @Part("_id") RequestBody id,
                                  @Part MultipartBody.Part avatar
                                  );

    @FormUrlEncoded
    @POST("/api/v4/update/password")
    Call<ResponseBody> changePassword(@Field("email") String email,
                             @Field("passOld") String passOld,
                             @Field("passNew") String passNew);

}
