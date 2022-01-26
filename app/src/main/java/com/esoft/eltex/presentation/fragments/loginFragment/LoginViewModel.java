package com.esoft.eltex.presentation.fragments.loginFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.esoft.eltex.data.PreferenceDataSource;
import com.esoft.eltex.domain.LoginRepository;
import com.esoft.eltex.domain.TokenModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;
    private PreferenceDataSource preferenceDataSource;

    public LoginViewModel(LoginRepository loginRepository, PreferenceDataSource preferenceDataSource) {
        this.loginRepository = loginRepository;
        this.preferenceDataSource = preferenceDataSource;
    }

    void loginIn(String grantType, String username, String password) {
        loginRepository.loginIn(grantType, username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<TokenModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<TokenModel> tokenModel) {
                        if (tokenModel.body() != null) {
                            preferenceDataSource.addPrefString("token", tokenModel.body().getToken());
                            preferenceDataSource.addPrefString("tokenType", tokenModel.body().getTokenType());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                });
    }
}
