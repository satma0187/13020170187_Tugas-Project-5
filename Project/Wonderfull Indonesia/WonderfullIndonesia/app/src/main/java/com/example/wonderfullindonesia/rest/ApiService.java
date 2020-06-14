package com.example.wonderfullindonesia.rest;

import com.example.wonderfullindonesia.model.Area;
import com.example.wonderfullindonesia.model.Detail;
import com.example.wonderfullindonesia.model.Maps;
import com.example.wonderfullindonesia.model.Response;
import com.example.wonderfullindonesia.model.Status;
import com.example.wonderfullindonesia.model.Tour;
import com.example.wonderfullindonesia.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("users")
    Call<Status> register(@Field("full_name") String full_name,
                            @Field("contact") String contact,
                            @Field("address") String address,
                            @Field("username") String username,
                            @Field("password") String password);

    @FormUrlEncoded
    @PUT("users")
    Call<Status> login(@Field("username") String username, @Field("password") String password);

    @GET("choose")
    Call<List<Area>> getArea();

    @FormUrlEncoded
    @POST("detail")
    Call<List<Detail>> getDetail(@Field("idcode") int idcode,
                                 @Field("code") int code);

    @FormUrlEncoded
    @POST("tour")
    Call<List<Tour>> getTour(@Field("idcode") int idcode);

    @FormUrlEncoded
    @POST("maps")
    Call<List<Maps>> getMaps(@Field("idcode") int idcode);

    @GET("profile")
    Call<List<Response>> getProfile(@Query("id") int id);

    @FormUrlEncoded
    @POST("profile")
    Call<List<Users>> updateProfile(@Field("id") int id,
                                       @Field("fullname") String fullname,
                                       @Field("contact") String contact,
                                       @Field("address") String address,
                                       @Field("username") String username,
                                       @Field("password") String password);

    @DELETE("profile/{id}")
    Call<List<Users>> deleteProfile(@Path("id") int id);
}
