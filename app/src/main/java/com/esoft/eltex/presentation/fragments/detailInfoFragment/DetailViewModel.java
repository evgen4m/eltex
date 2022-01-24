package com.esoft.eltex.presentation.fragments.detailInfoFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esoft.eltex.data.PreferenceDataSource;
import com.esoft.eltex.domain.LoginRepository;
import com.esoft.eltex.domain.UserModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {

    private LoginRepository loginRepository;
    private PreferenceDataSource preferenceDataSource;

    MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> codeLiveData = new MutableLiveData<>();

    public DetailViewModel(LoginRepository loginRepository, PreferenceDataSource preferenceDataSource) {
        this.loginRepository = loginRepository;
        this.preferenceDataSource = preferenceDataSource;
    }

    public void getUserInfo() {
        String tokenType = preferenceDataSource.getPrefString("tokenType");
        String token = preferenceDataSource.getPrefString("token");
        loginRepository.getUserInfo(tokenType + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<UserModel> userModelResponse) {
                        if (userModelResponse.body() != null) {
                            userModelMutableLiveData.setValue(userModelResponse.body());
                        }
                        preferenceDataSource.addPrefInt("code", userModelResponse.code());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

     void checkCode() {
        codeLiveData.setValue(preferenceDataSource.getPrefInt("code"));
    }

}
