package com.esoft.eltex.domain;

import io.reactivex.Single;
import retrofit2.Response;

public interface LoginRepository {

    Single<Response<TokenModel>> loginIn(String grantType, String username, String password);

    Single<Response<UserModel>> getUserInfo();

}
