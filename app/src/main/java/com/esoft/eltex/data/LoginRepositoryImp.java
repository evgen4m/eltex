package com.esoft.eltex.data;

import androidx.annotation.NonNull;

import com.esoft.eltex.domain.LoginRepository;
import com.esoft.eltex.domain.TokenModel;
import com.esoft.eltex.domain.UserModel;


import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Response;

public class LoginRepositoryImp implements LoginRepository {

    private EltexApi eltexApi;

    public LoginRepositoryImp(EltexApi eltexApi) {
        this.eltexApi = eltexApi;
    }

    @Override
    public Single<Response<TokenModel>> loginIn(String grantType, String username, String password) {
        return eltexApi.loginIn(grantType, username, password).map(new Function<Response<TokenModel>, Response<TokenModel>>() {
             @Override
             public Response<TokenModel> apply(@NonNull Response<TokenModel> tokenModelResponse) throws Exception {
                return tokenModelResponse;
             }
         });
    }

    @Override
    public Single<Response<UserModel>> getUserInfo() {
        return eltexApi.getUser().map(new Function<Response<UserModel>, Response<UserModel>>() {
            @Override
            public Response<UserModel> apply(@NonNull Response<UserModel> userModelResponse) throws Exception {
                return userModelResponse;
            }
        });
    }

}
