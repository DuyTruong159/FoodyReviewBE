package com.example.foodyreviewbe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @FormUrlEncoded
    @POST("/auth/signIn")
    Call<LoginResponse> login(@Field("username") String email,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("/user/shop-owner")
    Call<Response> register(@Field("firstName") String nickname,
                            @Field("gender") String gender,
                            @Field("username") String email,
                            @Field("password") String password);

    @GET("/shop/shop-owner/{userId}")
    Call<List<ShopResponse>> getShop(@Path(value = "userId", encoded = true) String userId);

    @FormUrlEncoded
    @POST("/shop")
    Call<Response> addShop(@Field("userId") String userId,
                           @Field("shopName") String shopName,
                           @Field("description") String description,
                           @Field("logoUrl") String logoUrl);
}
