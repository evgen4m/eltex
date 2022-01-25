package com.esoft.eltex.presentation.fragments.detailInfoFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esoft.eltex.domain.LoginRepository;
import com.esoft.eltex.domain.UserModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {

    private LoginRepository loginRepository;

    MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();

    public DetailViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public void getUserInfo() {
        loginRepository.getUserInfo()
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
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                });
    }
}
