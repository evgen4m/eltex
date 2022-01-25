package com.esoft.eltex.data;

import com.esoft.eltex.domain.TokenModel;
import com.esoft.eltex.domain.UserModel;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EltexApi {

    @POST("oauth/token")
    @FormUrlEncoded
    Single<Response<TokenModel>> loginIn(@Field("grant_type") String grantType,
                                        @Field("username") String username,
                                        @Field("password") String password);


    @GET("user")
    Single<Response<UserModel>> getUser();

}
